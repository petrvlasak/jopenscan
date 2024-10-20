package net.petrvlasak.jopenscan.ui.helper;

import lombok.RequiredArgsConstructor;
import net.petrvlasak.jopenscan.task.ScanningTaskFactory;
import net.petrvlasak.jopenscan.ui.WicketApplication;
import net.petrvlasak.jopenscan.ui.event.WebSocketEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@RequiredArgsConstructor
public class ScanningTaskControllerImpl implements ScanningTaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScanningTaskControllerImpl.class);

    private static final String SCANNING_TASK_STATUS_READY = "Ready";
    private static final String SCANNING_TASK_STATUS_PREPARING = "Preparing";
    private static final String SCANNING_TASK_STATUS_STOPPING = "Stopping";
    private static final String SCANNING_TASK_STATUS_PHOTO_XY = "Photo {0}/{1}";
    private static final String SCANNING_TASK_STATUS_ERROR = "Error: {0}";

    private final ScanningTaskFactory scanningTaskFactory;

    private WicketApplication application;
    private Thread scanningThread;
    private boolean scanningThreadFailed;

    @Override
    public void appInit(WicketApplication application) {
        this.application = application;
        application.setCurrentJobStatus(SCANNING_TASK_STATUS_READY);
    }

    @Override
    public void start() {
        if (isInProgress()) {
            throw new IllegalStateException("Scanning is already in progress");
        }
        scanningThreadFailed = false;
        scanningThread = Thread.ofVirtual().start(scanningTaskFactory.createTask(application.getCurrentJobSettings())
                .withOnTakePhoto(this::onTakePhoto)
                .withOnError(this::onError));
        Thread.ofVirtual().start(() -> {
            try {
                scanningThread.join();
            } catch (InterruptedException ignored) {}
            if (!scanningThreadFailed) {
                application.setCurrentJobStatus(SCANNING_TASK_STATUS_READY);
            }
            application.sendPushMessage(WebSocketEventType.SCANNING_STATE_CHANGED);
        });
        setCurrentJobStatusAndPushMessage(SCANNING_TASK_STATUS_PREPARING, WebSocketEventType.SCANNING_STATE_CHANGED);
    }

    @Override
    public boolean isInProgress() {
        return scanningThread != null && scanningThread.isAlive();
    }

    @Override
    public void stop() {
        if (!isInProgress()) {
            throw new IllegalStateException("Scanning is not in progress");
        }
        setCurrentJobStatusAndPushMessage(SCANNING_TASK_STATUS_STOPPING, WebSocketEventType.SCANNING_STATE_CHANGED);
        scanningThread.interrupt();
    }

    private void onTakePhoto(short current, short count) {
        setCurrentJobStatusAndPushMessage(MessageFormat.format(SCANNING_TASK_STATUS_PHOTO_XY, current, count),
                WebSocketEventType.SCANNING_NEW_PROGRESS);
    }

    private void onError(Exception e) {
        LOGGER.error(e.getMessage(), e);
        scanningThreadFailed = true;
        setCurrentJobStatusAndPushMessage(MessageFormat.format(SCANNING_TASK_STATUS_ERROR, e.getMessage()),
                WebSocketEventType.SCANNING_NEW_PROGRESS);
    }

    private void setCurrentJobStatusAndPushMessage(String status, WebSocketEventType message) {
        application.setCurrentJobStatus(status);
        application.sendPushMessage(message);
    }

}

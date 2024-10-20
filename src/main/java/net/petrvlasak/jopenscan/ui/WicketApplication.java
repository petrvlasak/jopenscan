package net.petrvlasak.jopenscan.ui;

import com.giffing.wicket.spring.boot.starter.app.WicketBootStandardWebApplication;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.petrvlasak.jopenscan.domain.CameraType;
import net.petrvlasak.jopenscan.domain.JobSettings;
import net.petrvlasak.jopenscan.domain.MachineSettings;
import net.petrvlasak.jopenscan.service.JobSettingsService;
import net.petrvlasak.jopenscan.service.JobSettingsServiceException;
import net.petrvlasak.jopenscan.ui.helper.ScanningTaskController;
import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.ws.WebSocketSettings;
import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class WicketApplication extends WicketBootStandardWebApplication {

    private final JobSettingsService jobSettingsService;
    private final ScanningTaskController scanningTaskController;

    @Getter @Setter
    private JobSettings currentJobSettings;

    @Getter @Setter
    private volatile String currentJobStatus;

    public static WicketApplication get() {
        Application application = Application.get();

        if (!(application instanceof WicketApplication)) {
            throw new WicketRuntimeException(
                    "The application attached to the current thread is not a "
                            + WicketApplication.class.getSimpleName());
        }

        return (WicketApplication) application;
    }

    @Override
    protected void init() {
        super.init();
        resetSettings();
        scanningTaskController.appInit(this);
    }

    @Override
    public Session newSession(Request request, Response response) {
        return super.newSession(request, response).setLocale(Locale.US);
    }

    public void sendPushMessage(IWebSocketPushMessage message) {
        WebSocketSettings.Holder.get(this).getConnectionRegistry().getConnections(this)
                .forEach(c -> c.sendMessage(message));
    }

    public void changeCurrentCameraType(CameraType cameraType) {
        MachineSettings machineSettings = currentJobSettings.getMachine();
        if (machineSettings.getCameraType() != cameraType) {
            machineSettings.setCameraType(cameraType);
            jobSettingsService.applyDefaults(currentJobSettings);
        }
    }

    public void loadSettings(InputStream inputStream) throws JobSettingsServiceException {
        currentJobSettings = jobSettingsService.applyDefaults(jobSettingsService.load(inputStream));
    }

    public void resetSettings() {
        currentJobSettings = jobSettingsService.applyDefaults(new JobSettings());
    }

    public void startScanningTask() {
        scanningTaskController.start();
    }

    public boolean isScanningInProgress() {
        return scanningTaskController.isInProgress();
    }

    public void stopScanningTask() {
        scanningTaskController.stop();
    }

}

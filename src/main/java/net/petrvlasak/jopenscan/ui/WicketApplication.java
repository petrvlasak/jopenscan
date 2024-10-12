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
import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class WicketApplication extends WicketBootStandardWebApplication {

    private JobSettings currentJobSettings;
    private String currentJobStatus = "--READY--";

    private final JobSettingsService jobSettingsService;

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
    }

    @Override
    public Session newSession(Request request, Response response) {
        return super.newSession(request, response).setLocale(Locale.US);
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

}

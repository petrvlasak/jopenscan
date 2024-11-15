package net.petrvlasak.jopenscan.hal.pi4j;

import net.petrvlasak.jopenscan.domain.CameraType;
import net.petrvlasak.jopenscan.domain.JobSettings;
import net.petrvlasak.jopenscan.hal.Camera;
import net.petrvlasak.jopenscan.hal.CameraFactory;
import org.springframework.stereotype.Component;

@Component
public class Pi4jCameraFactory implements CameraFactory {

    @Override
    public Camera getCamera(JobSettings jobSettings) {
        CameraType cameraType = jobSettings.getMachine().getCameraType();
        return switch (cameraType) {
            case RPI -> new Pi4jRPiCamera();
            case EXT -> new Pi4jExternalCamera(jobSettings.getCamera());
            default -> throw new IllegalStateException("Unsupported camera type: " + cameraType);
        };
    }

}

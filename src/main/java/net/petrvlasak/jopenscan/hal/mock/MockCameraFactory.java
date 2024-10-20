package net.petrvlasak.jopenscan.hal.mock;

import net.petrvlasak.jopenscan.domain.CameraType;
import net.petrvlasak.jopenscan.domain.JobSettings;
import net.petrvlasak.jopenscan.hal.Camera;
import net.petrvlasak.jopenscan.hal.CameraFactory;
import org.springframework.stereotype.Component;

@Component
public class MockCameraFactory implements CameraFactory {

    @Override
    public Camera getCamera(JobSettings jobSettings) {
        CameraType cameraType = jobSettings.getMachine().getCameraType();
        return switch (cameraType) {
            case RPI -> new MockRPiCamera();
            case EXT -> new MockExternalCamera(jobSettings.getCamera());
            default -> throw new IllegalStateException("Unsupported camera type: " + cameraType);
        };
    }

}

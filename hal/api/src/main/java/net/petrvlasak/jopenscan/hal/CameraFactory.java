package net.petrvlasak.jopenscan.hal;

import net.petrvlasak.jopenscan.domain.CameraType;
import net.petrvlasak.jopenscan.domain.JobSettings;

public interface CameraFactory {

    default Camera getCamera(JobSettings jobSettings) {
        CameraType cameraType = jobSettings.getMachine().getCameraType();
        return (switch (cameraType) {
            case RPI -> getRPiCamera();
            case EXT -> getExternalCamera();
            default -> throw new IllegalStateException("Unsupported camera type: " + cameraType);
        }).setup(jobSettings.getCamera());
    }

    Camera getRPiCamera();

    Camera getExternalCamera();

}

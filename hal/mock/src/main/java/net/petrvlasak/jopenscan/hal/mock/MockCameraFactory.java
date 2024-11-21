package net.petrvlasak.jopenscan.hal.mock;

import net.petrvlasak.jopenscan.hal.Camera;
import net.petrvlasak.jopenscan.hal.CameraFactory;
import org.springframework.stereotype.Component;

@Component
public class MockCameraFactory implements CameraFactory {

    @Override
    public Camera getRPiCamera() {
        return new MockRPiCamera();
    }

    @Override
    public Camera getExternalCamera() {
        return new MockExternalCamera();
    }

}

package net.petrvlasak.jopenscan.hal.mock;

import net.petrvlasak.jopenscan.domain.CameraSettings;
import net.petrvlasak.jopenscan.hal.AbstractExternalCamera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockExternalCamera extends AbstractExternalCamera {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockExternalCamera.class);

    public MockExternalCamera(CameraSettings cameraSettings) {
        super(cameraSettings);
    }

    @Override
    public void switchOn() {
        LOGGER.info("External camera switch on");
    }

    @Override
    public void switchOff() {
        LOGGER.info("External camera switch off");
    }

}

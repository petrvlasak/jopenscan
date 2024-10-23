package net.petrvlasak.jopenscan.hal.mock;

import net.petrvlasak.jopenscan.hal.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class MockRPiCamera implements Camera {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MockRPiCamera.class);

    @Override
    public void takePhoto() {
        LOGGER.info("Take a photo with the Raspberry Pi Camera");
    }

}

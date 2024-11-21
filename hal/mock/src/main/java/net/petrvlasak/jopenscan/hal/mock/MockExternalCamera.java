package net.petrvlasak.jopenscan.hal.mock;

import net.petrvlasak.jopenscan.hal.AbstractExternalCamera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class MockExternalCamera extends AbstractExternalCamera {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MockExternalCamera.class);

    @Override
    protected void switchState(boolean on) {
        LOGGER.info("External camera switch {}", on ? "ON" : "OFF");
    }

}

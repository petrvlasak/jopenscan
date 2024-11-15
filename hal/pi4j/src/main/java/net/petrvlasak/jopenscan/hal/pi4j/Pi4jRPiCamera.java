package net.petrvlasak.jopenscan.hal.pi4j;

import net.petrvlasak.jopenscan.hal.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class Pi4jRPiCamera implements Camera {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Pi4jRPiCamera.class);

    @Override
    public void takePhoto() {
        LOGGER.info("Take a photo with the Raspberry Pi Camera");
    }

}

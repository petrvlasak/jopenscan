package net.petrvlasak.jopenscan.hal.pi4j;

import com.pi4j.context.Context;
import net.petrvlasak.jopenscan.domain.CameraSettings;
import net.petrvlasak.jopenscan.hal.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class Pi4jRPiCamera implements Camera {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Pi4jRPiCamera.class);

    public Pi4jRPiCamera(Context context) {
    }

    @Override
    public Camera setup(CameraSettings cameraSettings) {
        return this;
    }

    @Override
    public void takePhoto() {
        LOGGER.info("Take a photo with the Raspberry Pi Camera");
    }

}

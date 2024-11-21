package net.petrvlasak.jopenscan.hal.pi4j;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import net.petrvlasak.jopenscan.hal.AbstractExternalCamera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class Pi4jExternalCamera extends AbstractExternalCamera {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Pi4jExternalCamera.class);

    private static final int PIN_EXT_CAM = 10;

    private final DigitalOutput extCam;

    public Pi4jExternalCamera(Context context) {
        extCam = context.create(DigitalOutput.newConfigBuilder(context)
                .id("extCam")
                .name("External camera")
                .address(PIN_EXT_CAM)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW));
    }

    @Override
    protected void switchState(boolean on) {
        extCam.state(on ? DigitalState.HIGH : DigitalState.LOW);
        LOGGER.debug("{} switch {}", extCam.name(), on ? "ON" : "OFF");
    }

}

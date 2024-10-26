package net.petrvlasak.jopenscan.hal.mock;

import net.petrvlasak.jopenscan.hal.Ringlight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class MockRinglight implements Ringlight {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MockRinglight.class);

    @Override
    public void switchLed1(boolean on) {
        LOGGER.info("LED1 is {}", on ? "ON" : "OFF");
    }

    @Override
    public void switchLed2(boolean on) {
        LOGGER.info("LED2 is {}", on ? "ON" : "OFF");
    }

    @Override
    public void setIntensity(byte intensity) {
        LOGGER.info("Light intensity: {}%", intensity);
    }

}

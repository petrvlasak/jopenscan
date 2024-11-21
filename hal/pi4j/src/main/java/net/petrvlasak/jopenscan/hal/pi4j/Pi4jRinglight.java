package net.petrvlasak.jopenscan.hal.pi4j;

import com.pi4j.context.Context;
import net.petrvlasak.jopenscan.hal.Ringlight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class Pi4jRinglight implements Ringlight {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Pi4jRinglight.class);

    private static final int PIN_RL1 = 17;
    private static final int PIN_RL2 = 27;

    private final Pi4jRinglightPwm ringlight1;
    private final Pi4jRinglightPwm ringlight2;

    private boolean led1 = false;
    private boolean led2 = false;
    private byte intensity = 0;

    public Pi4jRinglight(Context context) {
        ringlight1 = new Pi4jRinglightPwm(context, 1, PIN_RL1);
        ringlight2 = new Pi4jRinglightPwm(context, 2, PIN_RL2);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Terminating");
            ringlight1.getThread().interrupt();
            ringlight2.getThread().interrupt();
            try {
                ringlight1.getThread().join();
            } catch (InterruptedException ignored) {}
            try {
                ringlight2.getThread().join();
            } catch (InterruptedException ignored) {}
            LOGGER.info("Terminated");
        }, "ringlight-shutdown"));
    }

    @Override
    public void switchLed1(boolean on) {
        led1 = on;
        ringlight1.setIntensity(led1 ? intensity : 0);
        LOGGER.debug("LED1 is {}", on ? "ON" : "OFF");
    }

    @Override
    public void switchLed2(boolean on) {
        led2 = on;
        ringlight2.setIntensity(led2 ? intensity : 0);
        LOGGER.debug("LED2 is {}", on ? "ON" : "OFF");
    }

    @Override
    public void setIntensity(byte intensity) {
        this.intensity = intensity;
        if (led1) {
            ringlight1.setIntensity(intensity);
        }
        if (led2) {
            ringlight2.setIntensity(intensity);
        }
        LOGGER.debug("Light intensity: {}%", intensity);
    }

}

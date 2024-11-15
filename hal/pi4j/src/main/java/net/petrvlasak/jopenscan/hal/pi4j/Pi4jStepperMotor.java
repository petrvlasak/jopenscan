package net.petrvlasak.jopenscan.hal.pi4j;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import net.petrvlasak.jopenscan.hal.StepperMotor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class Pi4jStepperMotor implements StepperMotor {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Pi4jStepperMotor.class);

    private static final int STEPS_PER_REV = 200 * 16;

    private final String name;
    private final DigitalOutput enabled;
    private final DigitalOutput direction;
    private final DigitalOutput step;

    public Pi4jStepperMotor(Context context, String id, String name, int pinEnabled, int pinDirection, int pinStep) {
        this.name = name;
        enabled = context.create(DigitalOutput.newConfigBuilder(context)
                .id(id + "-en")
                .name(name + " Enabled")
                .address(pinEnabled)
                .shutdown(DigitalState.HIGH)
                .initial(DigitalState.HIGH));
        direction = context.create(DigitalOutput.newConfigBuilder(context)
                .id(id + "-dir")
                .name(name + " Direction")
                .address(pinDirection)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW));
        step = context.create(DigitalOutput.newConfigBuilder(context)
                .id(id + "-step")
                .name(name + " Step")
                .address(pinStep)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW));
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled.state(enabled ? DigitalState.LOW : DigitalState.HIGH);
        LOGGER.debug("{} is {}", name, enabled ? "ON" : "OFF");
    }

    @Override
    public void rotate(float angle) throws InterruptedException {
        LOGGER.debug("{}: rotate by {} degrees", name, angle);
        direction.state(angle > 0 ? DigitalState.HIGH : DigitalState.LOW);
        int steps = Math.round(Math.abs(angle) / 360 * STEPS_PER_REV);
        for (int i = 0; i < steps; i++) {
            step.high();
            Thread.sleep(0, 500000);
            step.low();
            Thread.sleep(0, 500000);
        }
    }

}

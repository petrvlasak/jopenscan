package net.petrvlasak.jopenscan.hal.mock;

import lombok.RequiredArgsConstructor;
import net.petrvlasak.jopenscan.hal.StepperMotor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class MockStepperMotor implements StepperMotor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockStepperMotor.class);

    private final String name;

    @Override
    public void rotate(float angle) {
        LOGGER.info("{}: rotate by {} degrees", name, angle);
    }

}

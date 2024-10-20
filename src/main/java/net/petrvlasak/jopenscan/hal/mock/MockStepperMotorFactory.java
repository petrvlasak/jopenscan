package net.petrvlasak.jopenscan.hal.mock;

import net.petrvlasak.jopenscan.hal.StepperMotor;
import net.petrvlasak.jopenscan.hal.StepperMotorFactory;
import org.springframework.stereotype.Component;

@Component
public class MockStepperMotorFactory implements StepperMotorFactory {

    @Override
    public StepperMotor getRotor() {
        return new MockStepperMotor("Rotor");
    }

    @Override
    public StepperMotor getTurntable() {
        return new MockStepperMotor("Turntable");
    }

}

package net.petrvlasak.jopenscan.hal.pi4j;

import com.pi4j.context.Context;
import net.petrvlasak.jopenscan.hal.StepperMotor;
import net.petrvlasak.jopenscan.hal.StepperMotorFactory;
import org.springframework.stereotype.Component;

@Component
public class Pi4jStepperMotorFactory implements StepperMotorFactory {

    private static final int PIN_RO_EN = 3;
    private static final int PIN_RO_DIR = 5;
    private static final int PIN_RO_STEP = 6;

    private static final int PIN_TT_EN = 2;
    private static final int PIN_TT_DIR = 9;
    private static final int PIN_TT_STEP = 11;

    private final StepperMotor rotor;
    private final StepperMotor turntable;

    public Pi4jStepperMotorFactory(Context context) {
        this.rotor = new Pi4jStepperMotor(context, "ro", "Rotor", PIN_RO_EN, PIN_RO_DIR, PIN_RO_STEP);
        this.turntable = new Pi4jStepperMotor(context, "tt", "Turntable", PIN_TT_EN, PIN_TT_DIR, PIN_TT_STEP);
    }

    @Override
    public StepperMotor getRotor() {
        return rotor;
    }

    @Override
    public StepperMotor getTurntable() {
        return turntable;
    }

}

package net.petrvlasak.jopenscan.ui.component;

import net.petrvlasak.jopenscan.hal.StepperMotor;
import net.petrvlasak.jopenscan.ui.event.WebSocketEventType;
import org.danekja.java.util.function.serializable.SerializableSupplier;

import java.io.Serial;

public class StepperMotorTurnLink extends ThreadStarterLink {

    @Serial
    private static final long serialVersionUID = 1L;

    private final SerializableSupplier<StepperMotor> stepperMotor;
    private final float angle;

    public StepperMotorTurnLink(String id, WebSocketEventType startEventType, WebSocketEventType stopEventType,
                                SerializableSupplier<StepperMotor> stepperMotor, float angle) {
        super(id, startEventType, stopEventType);
        this.stepperMotor = stepperMotor;
        this.angle = angle;
    }

    @Override
    public void run() {
        try {
            stepperMotor.get().rotate(angle);
        } catch (InterruptedException ignored) {}
    }

}

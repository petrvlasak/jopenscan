package net.petrvlasak.jopenscan.hal;

import java.io.Serializable;

public interface StepperMotor extends Serializable {

    void setEnabled(boolean enabled);

    void rotate(float angle) throws InterruptedException;

}

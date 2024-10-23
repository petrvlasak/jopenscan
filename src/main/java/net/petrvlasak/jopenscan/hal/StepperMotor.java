package net.petrvlasak.jopenscan.hal;

import java.io.Serializable;

public interface StepperMotor extends Serializable {

    void rotate(float angle) throws InterruptedException;

}

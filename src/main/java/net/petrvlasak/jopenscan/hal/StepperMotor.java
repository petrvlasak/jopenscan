package net.petrvlasak.jopenscan.hal;

public interface StepperMotor {

    void rotate(float angle) throws InterruptedException;

}

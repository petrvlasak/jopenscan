package net.petrvlasak.jopenscan.hal;

import net.petrvlasak.jopenscan.domain.RinglightLedsOn;

import java.io.Serializable;

public interface Ringlight extends Serializable {

    default void switchOn(RinglightLedsOn ringlightLedsOn) {
        switchLed1(ringlightLedsOn == RinglightLedsOn.LED1 || ringlightLedsOn == RinglightLedsOn.BOTH);
        switchLed2(ringlightLedsOn == RinglightLedsOn.LED2 || ringlightLedsOn == RinglightLedsOn.BOTH);
    }

    void switchLed1(boolean on);

    void switchLed2(boolean on);

    void setIntensity(byte intensity);

}

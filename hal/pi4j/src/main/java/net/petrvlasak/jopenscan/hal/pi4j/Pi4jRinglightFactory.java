package net.petrvlasak.jopenscan.hal.pi4j;

import net.petrvlasak.jopenscan.hal.Ringlight;
import net.petrvlasak.jopenscan.hal.RinglightFactory;
import org.springframework.stereotype.Component;

@Component
public class Pi4jRinglightFactory implements RinglightFactory {

    @Override
    public Ringlight getRinglight() {
        return new Pi4jRinglight();
    }

}

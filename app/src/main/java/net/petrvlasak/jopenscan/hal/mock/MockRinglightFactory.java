package net.petrvlasak.jopenscan.hal.mock;

import net.petrvlasak.jopenscan.hal.Ringlight;
import net.petrvlasak.jopenscan.hal.RinglightFactory;
import org.springframework.stereotype.Component;

@Component
public class MockRinglightFactory implements RinglightFactory {

    @Override
    public Ringlight getRinglight() {
        return new MockRinglight();
    }

}

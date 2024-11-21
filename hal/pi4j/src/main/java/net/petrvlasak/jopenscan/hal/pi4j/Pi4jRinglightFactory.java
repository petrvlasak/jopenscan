package net.petrvlasak.jopenscan.hal.pi4j;

import com.pi4j.context.Context;
import net.petrvlasak.jopenscan.hal.Ringlight;
import net.petrvlasak.jopenscan.hal.RinglightFactory;
import org.springframework.stereotype.Component;

@Component
public class Pi4jRinglightFactory implements RinglightFactory {

    private final Ringlight ringlight;

    public Pi4jRinglightFactory(Context context) {
        ringlight = new Pi4jRinglight(context);
    }

    @Override
    public Ringlight getRinglight() {
        return ringlight;
    }

}

package net.petrvlasak.jopenscan.hal.pi4j;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import lombok.Getter;
import lombok.Setter;

public class Pi4jRinglightPwm implements Runnable {

    private static final int PERIOD_NS = 1_000_000_000 / 100; // 100Hz
    private static final int VALUE_MULTIPLIER = PERIOD_NS / 100; // mapping value range 0-100 to period length
    private static final int SLEEP_NS = PERIOD_NS / 1000;

    private final DigitalOutput pin;
    @Getter @Setter private volatile byte intensity;
    @Getter private final Thread thread;

    public Pi4jRinglightPwm(Context context, int ringlightNumber, int pinNumber) {
        pin = context.create(DigitalOutput.newConfigBuilder(context)
                .id("rl" + ringlightNumber)
                .name("Ringlight " + ringlightNumber)
                .address(pinNumber)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW));
        intensity = 0;
        thread = Thread.ofVirtual().start(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (intensity == 100 && pin.isLow()) {
                    pin.high();
                } else {
                    if (System.nanoTime() % PERIOD_NS < (long) intensity * VALUE_MULTIPLIER) {
                        if (pin.isLow()) {
                            pin.high();
                        }
                    } else if (pin.isHigh()) {
                        pin.low();
                    }
                }
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                Thread.sleep(0, SLEEP_NS);
            }
        } catch (InterruptedException ignored) {}
    }

}

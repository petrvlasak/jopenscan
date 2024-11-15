package net.petrvlasak.jopenscan.hal.pi4j;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Pi4JContextFactory {

    @Bean
    public Context context() {
        return Pi4J.newAutoContext();
    }

}

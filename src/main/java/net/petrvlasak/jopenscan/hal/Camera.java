package net.petrvlasak.jopenscan.hal;

import java.io.Serializable;

public interface Camera extends Serializable {

    void takePhoto() throws InterruptedException;

}

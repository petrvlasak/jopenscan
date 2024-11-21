package net.petrvlasak.jopenscan.hal;

import net.petrvlasak.jopenscan.domain.CameraSettings;

import java.io.Serializable;

public interface Camera extends Serializable {

    Camera setup(CameraSettings cameraSettings);

    void takePhoto() throws InterruptedException;

}

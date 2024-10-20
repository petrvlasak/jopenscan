package net.petrvlasak.jopenscan.hal;

import net.petrvlasak.jopenscan.domain.JobSettings;

public interface CameraFactory {

    Camera getCamera(JobSettings jobSettings);

}

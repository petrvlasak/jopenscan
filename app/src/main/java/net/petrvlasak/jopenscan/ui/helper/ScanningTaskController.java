package net.petrvlasak.jopenscan.ui.helper;

import net.petrvlasak.jopenscan.ui.WicketApplication;

public interface ScanningTaskController {

    void appInit(WicketApplication application);

    void start();

    boolean isInProgress();

    void stop();

}

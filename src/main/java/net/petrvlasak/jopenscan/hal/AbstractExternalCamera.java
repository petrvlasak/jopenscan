package net.petrvlasak.jopenscan.hal;

import net.petrvlasak.jopenscan.domain.CameraSettings;

public abstract class AbstractExternalCamera implements Camera {

    private final long timePerPhoto;
    private long prevPhotoTs;
    private final long releaseTime;

    public AbstractExternalCamera(CameraSettings cameraSettings) {
        timePerPhoto = Math.round(cameraSettings.getTimePerPhoto() * 1000);
        prevPhotoTs = System.currentTimeMillis();
        releaseTime = cameraSettings.getReleaseTime();
    }

    @Override
    public void takePhoto() throws InterruptedException {
        long currentTs = System.currentTimeMillis();
        long nextPhotoTs = prevPhotoTs + timePerPhoto;
        if (currentTs < nextPhotoTs) {
            Thread.sleep(nextPhotoTs - currentTs);
        }
        switchOn();
        Thread.sleep(releaseTime);
        switchOff();
        prevPhotoTs = System.currentTimeMillis();
    }

    public abstract void switchOn() throws InterruptedException;

    public abstract void switchOff() throws InterruptedException;

}

package net.petrvlasak.jopenscan.hal;

import net.petrvlasak.jopenscan.domain.CameraSettings;

import java.io.Serial;

public abstract class AbstractExternalCamera implements Camera {

    @Serial
    private static final long serialVersionUID = 1L;

    private long timePerPhoto;
    private long releaseTime;
    private long prevPhotoTs;

    public AbstractExternalCamera() {
        setup(0.5f, (short) 10);
        prevPhotoTs = System.currentTimeMillis();
    }

    @Override
    public Camera setup(CameraSettings cameraSettings) {
        setup(cameraSettings.getTimePerPhoto(), cameraSettings.getReleaseTime());
        return this;
    }

    private void setup(float timePerPhoto, short releaseTime) {
        this.timePerPhoto = Math.round(timePerPhoto * 1000);
        this.releaseTime = releaseTime;
    }

    @Override
    public void takePhoto() throws InterruptedException {
        long currentTs = System.currentTimeMillis();
        long nextPhotoTs = prevPhotoTs + timePerPhoto;
        if (currentTs < nextPhotoTs) {
            Thread.sleep(nextPhotoTs - currentTs);
        }
        switchState(true);
        Thread.sleep(releaseTime);
        switchState(false);
        prevPhotoTs = System.currentTimeMillis();
    }

    protected abstract void switchState(boolean on) throws InterruptedException;

}

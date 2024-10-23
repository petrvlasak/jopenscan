package net.petrvlasak.jopenscan.task;

import net.petrvlasak.jopenscan.domain.JobSettings;
import net.petrvlasak.jopenscan.hal.Camera;
import net.petrvlasak.jopenscan.hal.CameraFactory;
import net.petrvlasak.jopenscan.hal.StepperMotor;
import net.petrvlasak.jopenscan.hal.StepperMotorFactory;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ScanningTask implements Runnable {

    private final StepperMotor rotor;
    private final StepperMotor turntable;
    private final Camera camera;

    private final byte startDeflection;
    private final byte endDeflection;
    private final byte rotorSteps;
    private final float rotorStepAngle;

    private final byte turntableSteps;
    private final float turntableStepAngle;

    private short photosCurrent;
    private final short photosCount;

    private BiConsumer<Short, Short> onTakePhoto = null;
    private Consumer<Exception> onError = null;

    public ScanningTask(StepperMotorFactory stepperMotorFactory, CameraFactory cameraFactory, JobSettings jobSettings) {
        rotor = stepperMotorFactory.getRotor();
        turntable = stepperMotorFactory.getTurntable();
        camera = cameraFactory.getCamera(jobSettings);

        startDeflection = jobSettings.getStartDeflection();
        endDeflection = jobSettings.getEndDeflection();
        rotorSteps = (byte) (jobSettings.getVerticalPositions() - 1);
        rotorStepAngle = -1.0f * (startDeflection + jobSettings.getEndDeflection()) / rotorSteps;

        turntableSteps = jobSettings.getPhotosPerRotation();
        turntableStepAngle = 360.0f / turntableSteps;

        photosCurrent = 0;
        photosCount = (short) (jobSettings.getPhotosPerRotation() * jobSettings.getVerticalPositions());
    }

    public ScanningTask withOnTakePhoto(BiConsumer<Short, Short> onTakePhoto) {
        this.onTakePhoto = onTakePhoto;
        return this;
    }

    public ScanningTask withOnError(Consumer<Exception> onError) {
        this.onError = onError;
        return this;
    }

    @Override
    public void run() {
        try {
            rotor.rotate(startDeflection);
            performHorizontalRotation();
            for (byte t = 0; t < rotorSteps; t++) {
                rotor.rotate(rotorStepAngle);
                performHorizontalRotation();
            }
            rotor.rotate(endDeflection);
        } catch (InterruptedException ignored) {
        } catch (Exception e) {
            if (onError != null) onError.accept(e);
        }
    }

    private void performHorizontalRotation() throws InterruptedException {
        for (byte r = 0; r < turntableSteps; r++) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            camera.takePhoto();
            if (onTakePhoto != null) onTakePhoto.accept(++photosCurrent, photosCount);
            turntable.rotate(turntableStepAngle);
        }
    }

}

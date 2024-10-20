package net.petrvlasak.jopenscan.task;

import lombok.RequiredArgsConstructor;
import net.petrvlasak.jopenscan.domain.JobSettings;
import net.petrvlasak.jopenscan.hal.CameraFactory;
import net.petrvlasak.jopenscan.hal.StepperMotorFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScanningTaskFactoryImpl implements ScanningTaskFactory {

    private final StepperMotorFactory stepperMotorFactory;
    private final CameraFactory cameraFactory;

    @Override
    public ScanningTask createTask(JobSettings jobSettings) {
        return new ScanningTask(stepperMotorFactory, cameraFactory, jobSettings);
    }

}

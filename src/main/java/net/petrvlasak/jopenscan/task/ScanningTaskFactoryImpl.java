package net.petrvlasak.jopenscan.task;

import lombok.RequiredArgsConstructor;
import net.petrvlasak.jopenscan.domain.JobSettings;
import net.petrvlasak.jopenscan.hal.CameraFactory;
import net.petrvlasak.jopenscan.hal.StepperMotorFactory;
import net.petrvlasak.jopenscan.service.ProjectService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScanningTaskFactoryImpl implements ScanningTaskFactory {

    private final ProjectService projectService;
    private final StepperMotorFactory stepperMotorFactory;
    private final CameraFactory cameraFactory;

    @Override
    public ScanningTask createTask(JobSettings jobSettings) {
        return new ScanningTask(projectService, jobSettings, stepperMotorFactory.getRotor(),
                stepperMotorFactory.getTurntable(), cameraFactory.getCamera(jobSettings));
    }

}

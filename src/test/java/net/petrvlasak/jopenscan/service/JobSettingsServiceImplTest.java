package net.petrvlasak.jopenscan.service;

import net.petrvlasak.jopenscan.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JobSettingsServiceImplTest {

    private static final JobSettings EMPTY = new JobSettings();

    private static final JobSettings EXT_CAMERA_TYPE_ONLY = JobSettings.builder()
            .machine(MachineSettings.builder()
                    .cameraType(CameraType.EXT)
                    .build())
            .build();

    private static final JobSettings DEFAULT_RPI = JobSettings.builder()
            .machine(MachineSettings.builder()
                    .cameraType(CameraType.RPI)
                    .rotorEnabled(false)
                    .turntableEnabled(false)
                    .ringlightIntensity((byte) 0)
                    .build())
            .camera(CameraSettings.builder()
                    .brightness((byte) 50)
                    .contrast((byte) 50)
                    .shutterSpeed((short) 50)
                    .cropX((byte) 0)
                    .cropY((byte) 0)
                    .build())
            .photosPerRotation((byte) 12)
            .verticalPositions((byte) 10)
            .startDeflection((byte) 45)
            .endDeflection((byte) 45)
            .build();

    private static final JobSettings DEFAULT_EXT = JobSettings.builder()
            .machine(MachineSettings.builder()
                    .cameraType(CameraType.EXT)
                    .rotorEnabled(false)
                    .turntableEnabled(false)
                    .ringlightIntensity((byte) 0)
                    .build())
            .camera(CameraSettings.builder()
                    .timePerPhoto(0.5f)
                    .releaseTime((short) 10)
                    .build())
            .photosPerRotation((byte) 12)
            .verticalPositions((byte) 10)
            .startDeflection((byte) 45)
            .endDeflection((byte) 45)
            .build();

    private static final JobSettings RPI = JobSettings.builder()
            .machine(MachineSettings.builder()
                    .cameraType(CameraType.RPI)
                    .rotorEnabled(true)
                    .turntableEnabled(false)
                    .ringlightLedsOn(RinglightLedsOn.LED1)
                    .ringlightIntensity((byte) 40)
                    .build())
            .camera(CameraSettings.builder()
                    .brightness((byte) 40)
                    .contrast((byte) 45)
                    .shutterSpeed((short) 55)
                    .cropX((byte) 10)
                    .cropY((byte) 15)
                    .build())
            .photosPerRotation((byte) 20)
            .verticalPositions((byte) 15)
            .startDeflection((byte) 40)
            .endDeflection((byte) 35)
            .projectName("TEST-Project-1")
            .build();

    private static final JobSettings EXT = JobSettings.builder()
            .machine(MachineSettings.builder()
                    .cameraType(CameraType.EXT)
                    .rotorEnabled(false)
                    .turntableEnabled(true)
                    .ringlightLedsOn(RinglightLedsOn.LED2)
                    .ringlightIntensity((byte) 60)
                    .build())
            .camera(CameraSettings.builder()
                    .timePerPhoto(1.5f)
                    .releaseTime((short) 20)
                    .build())
            .photosPerRotation((byte) 20)
            .verticalPositions((byte) 15)
            .startDeflection((byte) 40)
            .endDeflection((byte) 35)
            .projectName("TEST-Project-2")
            .build();

    private final JobSettingsServiceImpl service = new JobSettingsServiceImpl();

    @Test
    public void jobEmptyJson() throws Exception {
        JobSettings jobSettings = service.load(getClass().getResourceAsStream("job-empty.json"));
        assertEquals(EMPTY, jobSettings);
        assertEquals(DEFAULT_RPI, service.applyDefaults(jobSettings));
    }

    @Test
    public void jobExtCameraTypeOnlyJson() throws Exception {
        JobSettings jobSettings = service.load(getClass().getResourceAsStream("job-ext-ctype-only.json"));
        assertEquals(EXT_CAMERA_TYPE_ONLY, jobSettings);
        assertEquals(DEFAULT_EXT, service.applyDefaults(jobSettings));
    }

    @Test
    public void jobRpiJson() throws Exception {
        JobSettings jobSettings = service.load(getClass().getResourceAsStream("job-rpi.json"));
        assertEquals(RPI.toBuilder()
                .camera(RPI.getCamera().toBuilder()
                        .timePerPhoto(1.5f)
                        .releaseTime((short) 20)
                        .build())
                .build(), jobSettings);
        assertEquals(RPI, service.applyDefaults(jobSettings));
    }

    @Test
    public void jobExtJson() throws Exception {
        JobSettings jobSettings = service.load(getClass().getResourceAsStream("job-ext.json"));
        assertEquals(EXT.toBuilder()
                .camera(EXT.getCamera().toBuilder()
                        .brightness((byte) 40)
                        .contrast((byte) 45)
                        .shutterSpeed((short) 55)
                        .cropX((byte) 10)
                        .cropY((byte) 15)
                        .build())
                .build(), jobSettings);
        assertEquals(EXT, service.applyDefaults(jobSettings));
    }

}

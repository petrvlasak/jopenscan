package net.petrvlasak.jopenscan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.petrvlasak.jopenscan.domain.CameraSettings;
import net.petrvlasak.jopenscan.domain.CameraType;
import net.petrvlasak.jopenscan.domain.JobSettings;
import net.petrvlasak.jopenscan.domain.MachineSettings;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class JobSettingsServiceImpl implements JobSettingsService {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public JobSettings load(InputStream inputStream) throws JobSettingsServiceException {
        try {
            return mapper.readValue(inputStream, JobSettings.class);
        } catch (IOException e) {
            throw new JobSettingsServiceException("Error loading job settings: " + e.getMessage(), e);
        }
    }

    @Override
    public JobSettings applyDefaults(JobSettings jobSettings) {
        applyMachineDefaults(jobSettings);
        applyCameraDefaults(jobSettings);
        if (jobSettings.getPhotosPerRotation() == null) {
            jobSettings.setPhotosPerRotation((byte) 12);
        }
        if (jobSettings.getVerticalPositions() == null) {
            jobSettings.setVerticalPositions((byte) 10);
        }
        if (jobSettings.getStartDeflection() == null) {
            jobSettings.setStartDeflection((byte) 45);
        }
        if (jobSettings.getEndDeflection() == null) {
            jobSettings.setEndDeflection((byte) 45);
        }
        return jobSettings;
    }

    private void applyMachineDefaults(JobSettings jobSettings) {
        MachineSettings machine = jobSettings.getMachine();
        if (machine == null) {
            jobSettings.setMachine(machine = new MachineSettings());
        }
        if (machine.getCameraType() == null) {
            machine.setCameraType(CameraType.RPI);
        }
        if (machine.getRotorEnabled() == null) {
            machine.setRotorEnabled(false);
        }
        if (machine.getTurntableEnabled() == null) {
            machine.setTurntableEnabled(false);
        }
        if (machine.getRinglightIntensity() == null) {
            machine.setRinglightIntensity((byte) 0);
        }
    }

    private void applyCameraDefaults(JobSettings jobSettings) {
        CameraSettings camera = jobSettings.getCamera();
        if (camera == null) {
            jobSettings.setCamera(camera = new CameraSettings());
        }
        CameraType cameraType = jobSettings.getMachine().getCameraType();
        switch (cameraType) {
            case RPI:
                if (camera.getBrightness() == null) {
                    camera.setBrightness((byte) 50);
                }
                if (camera.getContrast() == null) {
                    camera.setContrast((byte) 50);
                }
                if (camera.getShutterSpeed() == null) {
                    camera.setShutterSpeed((short) 50);
                }
                if (camera.getCropX() == null) {
                    camera.setCropX((byte) 0);
                }
                if (camera.getCropY() == null) {
                    camera.setCropY((byte) 0);
                }
                camera.setTimePerPhoto(null);
                camera.setReleaseTime(null);
                break;
            case EXT:
                camera.setBrightness(null);
                camera.setContrast(null);
                camera.setShutterSpeed(null);
                camera.setCropX(null);
                camera.setCropY(null);
                if (camera.getTimePerPhoto() == null) {
                    camera.setTimePerPhoto(0.5f);
                }
                if (camera.getReleaseTime() == null) {
                    camera.setReleaseTime((short) 10);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown camera type: " + cameraType);
        }
    }

    @Override
    public void save(JobSettings jobSettings, OutputStream outputStream) throws JobSettingsServiceException {
        try {
            mapper.writeValue(outputStream, jobSettings);
        } catch (IOException e) {
            throw new JobSettingsServiceException("Error saving job settings: " + e.getMessage(), e);
        }
    }

}

package net.petrvlasak.jopenscan.service;

import lombok.RequiredArgsConstructor;
import net.petrvlasak.jopenscan.domain.JobSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private static final String JOB_SETTINGS_FILE_NAME = "job-settings.json";

    private final JobSettingsService jobSettingsService;

    private Path projectsPath;

    @Autowired
    public void setProjectsPath(@Value("${app.projects-path}") String projectsPath) {
        this.projectsPath = Paths.get(projectsPath);
    }

    @Override
    public Path createProjectPath(String projectName) throws ProjectServiceException {
        Path projectPath = projectsPath.resolve(projectName);
        if (projectPath.toFile().mkdirs()) {
            return projectPath;
        }
        throw new ProjectServiceException.ProjectAlreadyExistsException(projectName);
    }

    @Override
    public void saveJobSettings(Path projectPath, JobSettings jobSettings) throws ProjectServiceException {
        File file = projectPath.resolve(JOB_SETTINGS_FILE_NAME).toFile();
        try (OutputStream os = new FileOutputStream(file)) {
            jobSettingsService.save(jobSettings, os);
        } catch (IOException e) {
            throw new ProjectServiceException("Error creating job settings file: " + e.getMessage(), e);
        } catch (JobSettingsServiceException e) {
            throw new ProjectServiceException(e.getMessage(), e);
        }
    }

}

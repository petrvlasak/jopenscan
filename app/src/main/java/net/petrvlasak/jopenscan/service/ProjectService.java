package net.petrvlasak.jopenscan.service;

import net.petrvlasak.jopenscan.domain.JobSettings;

import java.nio.file.Path;

public interface ProjectService {

    Path createProjectPath(String projectName) throws ProjectServiceException;

    void saveJobSettings(Path projectPath, JobSettings jobSettings) throws ProjectServiceException;

}

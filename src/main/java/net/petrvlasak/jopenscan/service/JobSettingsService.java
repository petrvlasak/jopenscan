package net.petrvlasak.jopenscan.service;

import net.petrvlasak.jopenscan.domain.JobSettings;

import java.io.InputStream;

public interface JobSettingsService {

    JobSettings load(InputStream inputStream) throws JobSettingsServiceException;

    JobSettings applyDefaults(JobSettings jobSettings);

}

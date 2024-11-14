package net.petrvlasak.jopenscan.service;

import net.petrvlasak.jopenscan.domain.JobSettings;

import java.io.InputStream;
import java.io.OutputStream;

public interface JobSettingsService {

    /**
     * Loads a job settings from the given input stream. The method do not close the input stream.
     *
     * @param inputStream the input stream from which the job settings are loaded
     * @return loaded job settings
     * @throws JobSettingsServiceException in case an error occurs during loading
     */
    JobSettings load(InputStream inputStream) throws JobSettingsServiceException;

    JobSettings applyDefaults(JobSettings jobSettings);

    /**
     * Saves the given job settings to the given output stream. The method do not close the output stream.
     *
     * @param jobSettings the job settings which are being saved
     * @param outputStream the output stream to which the job settings are saved
     * @throws JobSettingsServiceException in case an error occurs during saving
     */
    void save(JobSettings jobSettings, OutputStream outputStream) throws JobSettingsServiceException;

}

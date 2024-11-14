package net.petrvlasak.jopenscan.service;

import java.io.Serial;

public class JobSettingsServiceException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public JobSettingsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}

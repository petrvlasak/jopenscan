package net.petrvlasak.jopenscan.service;

import java.io.Serial;

public class JobSettingsServiceException extends Exception {

    @Serial
    private static final long serialVersionUID = -2674926836760570178L;

    public JobSettingsServiceException() {
        super();
    }

    public JobSettingsServiceException(String message) {
        super(message);
    }

    public JobSettingsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobSettingsServiceException(Throwable cause) {
        super(cause);
    }

    protected JobSettingsServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

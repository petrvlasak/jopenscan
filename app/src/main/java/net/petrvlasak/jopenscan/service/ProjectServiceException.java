package net.petrvlasak.jopenscan.service;

import java.io.Serial;
import java.text.MessageFormat;

public class ProjectServiceException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProjectServiceException(String message) {
        super(message);
    }

    public ProjectServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class ProjectAlreadyExistsException extends ProjectServiceException {
        @Serial
        private static final long serialVersionUID = 1L;

        public ProjectAlreadyExistsException(String projectName) {
            super(MessageFormat.format("Project ''{0}'' already exists.", projectName));
        }
    }

}

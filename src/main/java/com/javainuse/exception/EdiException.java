package com.javainuse.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EdiException extends java.lang.Exception {
    private static final Logger LOGGER = LogManager.getLogger(EdiException.class);

    public EdiException() {
    }

    public EdiException(String message) {
        super(message);
    }

    public EdiException(Throwable cause) {
        super(cause);
    }
}

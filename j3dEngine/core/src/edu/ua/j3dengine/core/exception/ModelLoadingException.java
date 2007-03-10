package edu.ua.j3dengine.core.exception;


public class ModelLoadingException extends RuntimeException {


    public ModelLoadingException() {
    }

    public ModelLoadingException(String message) {
        super(message);
    }

    public ModelLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelLoadingException(Throwable cause) {
        super(cause);
    }
}

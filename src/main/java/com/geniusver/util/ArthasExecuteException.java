package com.geniusver.util;

/**
 * ArthasExecuteException
 *
 * @author daniel.hua
 */
public class ArthasExecuteException extends RuntimeException{
    public ArthasExecuteException() {
        super();
    }

    public ArthasExecuteException(String message) {
        super(message);
    }

    public ArthasExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArthasExecuteException(Throwable cause) {
        super(cause);
    }

    protected ArthasExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

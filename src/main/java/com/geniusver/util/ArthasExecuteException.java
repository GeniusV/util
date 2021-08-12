package com.geniusver.util;

/**
 * ArthasExecuteException
 *
 * @author GeniusV
 */
public class ArthasExecuteException extends RuntimeException {
    private String arthasCommand;
    private String finalCommand;

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

    public String getArthasCommand() {
        return arthasCommand;
    }

    public void setArthasCommand(String arthasCommand) {
        this.arthasCommand = arthasCommand;
    }

    public String getFinalCommand() {
        return finalCommand;
    }

    public void setFinalCommand(String finalCommand) {
        this.finalCommand = finalCommand;
    }
}

package net.etfbl.pisio.gifservice;

public class GifProcessingException extends RuntimeException {

    public GifProcessingException(String message) {
        super(message);
    }

    public GifProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

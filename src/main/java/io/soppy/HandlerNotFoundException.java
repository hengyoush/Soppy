package io.soppy;

public class HandlerNotFoundException extends RuntimeException {
    private String uri;

    public HandlerNotFoundException(String uri) {
        super();
        this.uri = uri;
    }

    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public String toString() {
        return String.format("Handler for uri: %s not found, please check again.", uri);
    }
}

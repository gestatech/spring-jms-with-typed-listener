package demo;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PingDocument {

    private final String message;

    @JsonCreator
    public PingDocument(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PingDocument{" +
                "message='" + message + '\'' +
                '}';
    }
}

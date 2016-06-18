package demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

import java.io.Serializable;

@Value
public class PingDocument implements Serializable {

    private final String message;

    @JsonCreator
    public PingDocument(String message) {
        this.message = message;
    }

}

package com.heh.gourmet.adapter.in.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Parameters")
public class InvalidParameters extends RuntimeException {
    public InvalidParameters(String message) {
        super(message);
    }

    public InvalidParameters(String message, Throwable cause) {
        super(message, cause);
    }
}

package be.heh.gourmet.adapter.in.web.exeption;

import be.heh.gourmet.application.port.in.exception.HttpException;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class CustomException extends Exception implements HttpException {
    private final int httpStatus;

    public static HashMap<String, Object> response(String message, int httpStatus) {
        return new CustomException(message, httpStatus).toResponse();
    }

    public static HashMap<String, Object> response(String message, HttpStatus httpStatus) {
        return new CustomException(message, httpStatus).toResponse();
    }

    public CustomException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus.value();
    }

    @Override
    public int httpStatus() {
        return httpStatus;
    }
}

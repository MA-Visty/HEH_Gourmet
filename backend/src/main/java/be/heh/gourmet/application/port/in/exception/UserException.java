package be.heh.gourmet.application.port.in.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException implements HttpException {
    public enum Type {
        USER_NOT_FOUND,
        USER_ALREADY_EXIST,
        USER_NOT_CREATED,
        USER_NOT_DELETED,
        USER_NOT_UPDATED,
    }

    private final Type type;

    public UserException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public UserException(String message, Type type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    @Override
    public String getMessage() {
        return type.toString();
    }

    @Override
    public int httpStatus() {
        return switch (getType()) {
            case USER_NOT_FOUND -> 404;
            case USER_ALREADY_EXIST -> 409;
            case USER_NOT_CREATED, USER_NOT_DELETED, USER_NOT_UPDATED -> 400;
        };
    }
}

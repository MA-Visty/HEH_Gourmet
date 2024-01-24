package be.heh.gourmet.application.port.in.exception;

import lombok.Getter;

@Getter
public class FavException extends RuntimeException implements HttpException {
    public enum Type {
        FAV_NOT_CREATED,
        FAV_ALREADY_EXIST,
        FAV_NOT_DELETED,
        FAV_NOT_FOUND,
    }

    private final Type type;

    public FavException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public FavException(String message, Type type, Throwable cause) {
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
            case FAV_NOT_FOUND -> 404;
            case FAV_NOT_CREATED, FAV_NOT_DELETED -> 400;
            case FAV_ALREADY_EXIST -> 409;
        };
    }
}

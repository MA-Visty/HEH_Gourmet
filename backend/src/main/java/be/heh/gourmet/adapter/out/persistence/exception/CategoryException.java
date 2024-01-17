package be.heh.gourmet.adapter.out.persistence.exception;

import be.heh.gourmet.adapter.in.web.exeption.HttpException;
import lombok.Getter;

@Getter
public class CategoryException extends RuntimeException implements HttpException {
    public enum Type {
        CATEGORY_NOT_FOUND,
        CATEGORY_ALREADY_EXIST,
        CATEGORY_NOT_CREATED,
        CATEGORY_NOT_UPDATED,
        CATEGORY_NOT_DELETED,
        ASSOCIATED_PRODUCT_NOT_FOUND
    }

    private final Type type;

    public CategoryException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public CategoryException(String message, Type type, Throwable cause) {
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
            case CATEGORY_NOT_FOUND, ASSOCIATED_PRODUCT_NOT_FOUND -> 404;
            case CATEGORY_NOT_CREATED, CATEGORY_NOT_DELETED, CATEGORY_NOT_UPDATED, CATEGORY_ALREADY_EXIST -> 400;
        };
    }
}

package be.heh.gourmet.adapter.out.persistence.exception;

import be.heh.gourmet.adapter.in.web.exeption.HttpException;
import lombok.Getter;

@Getter
public class ProductException extends RuntimeException implements HttpException {
    public enum Type {
        PRODUCT_NOT_FOUND,
        PRODUCT_ALREADY_EXIST,
        PRODUCT_NOT_CREATED,
        PRODUCT_NOT_UPDATED,
        PRODUCT_NOT_DELETED,
        ASSOCIATED_CATEGORY_NOT_FOUND
    }

    private final Type type;

    public ProductException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public ProductException(String message, Type type, Throwable cause) {
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
            case PRODUCT_ALREADY_EXIST -> 409;
            case ASSOCIATED_CATEGORY_NOT_FOUND, PRODUCT_NOT_FOUND -> 404;
            case PRODUCT_NOT_CREATED, PRODUCT_NOT_DELETED, PRODUCT_NOT_UPDATED -> 400;
        };
    }
}

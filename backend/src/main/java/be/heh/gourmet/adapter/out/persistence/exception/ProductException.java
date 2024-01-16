package be.heh.gourmet.adapter.out.persistence.exception;

import lombok.Getter;

import java.util.Date;
import java.util.HashMap;

@Getter
public class ProductException extends RuntimeException {
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

    public HashMap<String, Object> toResponse() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date(System.currentTimeMillis()));
        response.put("error", getType().toString());
        response.put("status", httpStatus());
        return response;
    }

    public int httpStatus() {
        return switch (getType()) {
            case ASSOCIATED_CATEGORY_NOT_FOUND, PRODUCT_NOT_FOUND -> 404;
            case PRODUCT_NOT_CREATED, PRODUCT_NOT_DELETED, PRODUCT_NOT_UPDATED, PRODUCT_ALREADY_EXIST -> 400;
        };
    }
}

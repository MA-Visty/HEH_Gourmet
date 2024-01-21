package be.heh.gourmet.application.port.in.exception;

public class CartException extends Throwable implements HttpException {
    public CartException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public CartException(String message, Type type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public enum Type {
        ASSOCIATED_PRODUCT_NOT_FOUND,
        ASSOCIATED_USER_NOT_FOUND,
        INSUFFICIENT_STOCK,
        INVALID_QUANTITY,
        PRODUCT_NOT_ADDED,
        PRODUCT_ALREADY_IN_CART
    }

    private final Type type;

    @Override
    public int httpStatus() {
        return switch (type) {
            case PRODUCT_NOT_ADDED, INSUFFICIENT_STOCK -> 400;
            case ASSOCIATED_PRODUCT_NOT_FOUND, ASSOCIATED_USER_NOT_FOUND -> 404;
            case INVALID_QUANTITY -> 422;
            case PRODUCT_ALREADY_IN_CART -> 409;
        };
    }

    @Override
    public String getMessage() {
        return type.toString();
    }
}

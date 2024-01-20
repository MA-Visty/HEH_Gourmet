package be.heh.gourmet.application.port.in.exception;

public class OrderException extends RuntimeException implements HttpException {
    public enum Type {
        CART_IS_EMPTY,
        PREPARE_DATE_CANNOT_BE_NULL,
        PREPARE_DATE_CANNOT_BE_IN_THE_PAST,
        ORDER_NOT_PLACED
    }

    @Override
    public int httpStatus() {
        return switch (type) {
            case PREPARE_DATE_CANNOT_BE_NULL -> 422;
            case CART_IS_EMPTY -> 409;
            case PREPARE_DATE_CANNOT_BE_IN_THE_PAST -> 400;
            case ORDER_NOT_PLACED -> 500;
        };
    }

    private final Type type;

    public OrderException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public OrderException(String message, Type type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}

package be.heh.gourmet.application.port.in.exception;

public class OrderException extends RuntimeException implements HttpException {
    public enum Type {
        CANNOT_CANCEL_ORDER,
        CART_IS_EMPTY,
        PREPARE_DATE_CANNOT_BE_NULL,
        INVALID_TARGET_DATE_FORMAT,
        PREPARE_DATE_CANNOT_BE_IN_THE_PAST,
        ORDER_NOT_FOUND,
        ORDER_IS_ALREADY_PREPARING,
        ORDER_ALREADY_CANCELED,
        ORDER_ALREADY_PREPARED,
        ORDER_ALREADY_DELIVERED,
        INVALID_STATUS,
        ORDER_NOT_UPDATED, ORDER_NOT_PLACED
    }

    @Override
    public int httpStatus() {
        return switch (type) {
            case INVALID_STATUS, ORDER_NOT_UPDATED, PREPARE_DATE_CANNOT_BE_IN_THE_PAST, ORDER_NOT_PLACED -> 400;
            case ORDER_ALREADY_CANCELED, CANNOT_CANCEL_ORDER, CART_IS_EMPTY, ORDER_IS_ALREADY_PREPARING, ORDER_ALREADY_DELIVERED, ORDER_ALREADY_PREPARED ->
                    409;
            case ORDER_NOT_FOUND -> 404;
            case INVALID_TARGET_DATE_FORMAT, PREPARE_DATE_CANNOT_BE_NULL -> 422;
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

    @Override
    public String getMessage() {
        return type.toString();
    }
}

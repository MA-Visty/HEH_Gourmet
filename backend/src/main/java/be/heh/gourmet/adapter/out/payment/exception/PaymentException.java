package be.heh.gourmet.adapter.out.payment.exception;

import be.heh.gourmet.application.port.in.exception.HttpException;
import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException implements HttpException {
    @Override
    public int httpStatus() {
        return switch (type) {
            case InsufficientFunds -> 402;
            case InvalidCard -> 403;
            case Unknown -> 500;
        };
    }

    public enum Type {
        InsufficientFunds,
        InvalidCard,
        Unknown
    }

    private final Type type;

    public PaymentException(Type type) {
        super("Payment failed: " + type.toString());
        this.type = type;
    }

}

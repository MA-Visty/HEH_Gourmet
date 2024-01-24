package be.heh.gourmet.application.domain.model;

public enum OrderStatus {
    // associate the status to a number
    // -- 0 = canceled, 1 = cancelable, 2 = pending, 3 = ready, 4 = delivered
    CANCELED(0), CANCELABLE(1), PENDING(2), READY(3), DELIVERED(4);

    final int value;

    OrderStatus(int value) {
        if (value < 0 || value > 4) {
            throw new IllegalArgumentException("Invalid value for OrderStatus");
        }
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static OrderStatus of(int value) {
        return switch (value) {
            case 0 -> CANCELED;
            case 1 -> CANCELABLE;
            case 2 -> PENDING;
            case 3 -> READY;
            case 4 -> DELIVERED;
            default -> throw new IllegalArgumentException("Invalid value for OrderStatus");
        };
    }
}

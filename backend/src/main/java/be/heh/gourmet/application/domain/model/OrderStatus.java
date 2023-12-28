package be.heh.gourmet.application.domain.model;

public enum OrderStatus {
    // associate the status to a number
    // -- 0 = canceled, 1 = cancelable, 2 = pending, 3 = ready, 4 = delivered
    CANCELED(0), CANCELABLE(1), PENDING(2), READY(3), DELIVERED(4);

    OrderStatus(int value) {
        if (value < 0 || value > 4) {
            throw new IllegalArgumentException("Invalid value for OrderStatus");
        }
    }
}

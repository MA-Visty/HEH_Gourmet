package be.heh.gourmet.application.domain.model;

import java.util.Date;

public record OrderRow(int ID, Date orderDate, Date prepareDate, Float totalPrice, OrderStatus status) {
}

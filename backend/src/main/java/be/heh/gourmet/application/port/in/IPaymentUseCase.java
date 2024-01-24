package be.heh.gourmet.application.port.in;

import be.heh.gourmet.application.domain.model.CartRow;

import java.util.List;
import java.util.Map;

public interface IPaymentUseCase {
    void charge(int UserId, List<CartRow> cart, Map<String, Object> params);

    void refund(int UserID, String orderID, Map<String, Object> params);
}

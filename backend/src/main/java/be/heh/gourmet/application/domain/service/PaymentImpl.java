package be.heh.gourmet.application.domain.service;

import be.heh.gourmet.application.domain.model.CartRow;
import be.heh.gourmet.application.domain.model.User;
import be.heh.gourmet.application.port.in.IPaymentUseCase;
import be.heh.gourmet.application.port.out.IUserRepository;
import be.heh.gourmet.application.port.out.IPaymentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentImpl implements IPaymentUseCase {
    @Autowired
    @Qualifier("getPaymentClient")
    private IPaymentClient paymentClient;

    @Autowired
    @Qualifier("getUserRepository")
    private IUserRepository userManagement;

    @Override
    public void charge(int userId, List<CartRow> cart, Map<String, Object> params) {
        float total = 0f;
        for (CartRow cartRow : cart) {
            total += cartRow.totalPrice();
        }
        if (total == 0f) {
            throw new IllegalArgumentException("Cart is empty");
        }
        Optional<User> user = userManagement.get(userId);
        if (user.isEmpty()) {
            // TODO : use cart exception throw new IllegalArgumentException("User does not exist");
            throw new IllegalArgumentException("User does not exist");
        }

        paymentClient.charge(user.get(), total, params);
    }

    @Override
    public void refund(int UserID, String orderID, Map<String, Object> params) {
        throw new UnsupportedOperationException();
    }
}

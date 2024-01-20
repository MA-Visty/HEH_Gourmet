package be.heh.gourmet.application.port.out;

import be.heh.gourmet.application.domain.model.User;

import java.util.Map;

public interface PaymentClient {
    void charge(User user, Float amount, Map<String, Object> params);

    void charge(Float amount, Map<String, Object> params);
}

package be.heh.gourmet.adapter.out.payment;

import be.heh.gourmet.adapter.out.payment.exception.PaymentException;
import be.heh.gourmet.application.domain.model.User;
import be.heh.gourmet.application.port.out.IPaymentClient;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class StripeIPaymentAdapter implements IPaymentClient {
    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Customer add_customer(User user, Map<String, Object> params) throws StripeException {
        String token = (String) params.get("token");
        CustomerCreateParams.Builder customerParams = new CustomerCreateParams.Builder();
        customerParams.setEmail(user.email());
        customerParams.setSource(token);
        customerParams.setName(user.firstname() + " " + user.lastname());
        return Customer.create(customerParams.build());
    }

    @Override
    public void charge(User user, Float amount, Map<String, Object> params) {
        CustomerListParams listParams = CustomerListParams.builder()
                .setEmail(user.email())
                .build();
        try {
            List<Customer> customers = Customer.list(listParams).getData();
            ChargeCreateParams.Builder chargeParams = new ChargeCreateParams.Builder();
            chargeParams.setAmount((long) (amount * 100));
            if (params.get("currency") != null) {
                chargeParams.setCurrency((String) params.get("currency"));
            } else {
                chargeParams.setCurrency("eur");
            }
            Customer customer;
            if (customers.isEmpty()) {
                customer = add_customer(user, params);
            } else {
                customer = customers.get(0);
            }
            chargeParams.setCustomer(customer.getId());
            if (params.get("token") != null) {
                chargeParams.setSource((String) params.get("token"));
            } else {
                throw new IllegalArgumentException("Token is required");
            }
            com.stripe.model.Charge.create(chargeParams.build());
        } catch (StripeException e) {
            if (Objects.equals(e.getCode(), "insufficient_funds")) {
                throw new PaymentException(PaymentException.Type.InsufficientFunds);
            } else if (Objects.equals(e.getCode(), "invalid_number") || Objects.equals(e.getCode(), "invalid_expiry_month") || Objects.equals(e.getCode(), "invalid_expiry_year") || Objects.equals(e.getCode(), "invalid_cvc")) {
                throw new PaymentException(PaymentException.Type.InvalidCard);
            } else {
                throw new RuntimeException("Stripe payment failed", e);
            }
        }
    }

    @Override
    public void charge(Float amount, Map<String, Object> params) {
        try {
            ChargeCreateParams.Builder chargeParams = new ChargeCreateParams.Builder();
            chargeParams.setAmount((long) (amount * 100));
            if (params.get("currency") != null) {
                chargeParams.setCurrency((String) params.get("currency"));
            } else {
                chargeParams.setCurrency("eur");
            }
            if (params.get("token") != null) {
                chargeParams.setSource((String) params.get("token"));
            } else {
                throw new IllegalArgumentException("Token is required");
            }
            com.stripe.model.Charge.create(chargeParams.build());
        } catch (StripeException e) {
            if (Objects.equals(e.getCode(), "insufficient_funds")) {
                throw new PaymentException(PaymentException.Type.InsufficientFunds);
            } else if (Objects.equals(e.getCode(), "invalid_number") || Objects.equals(e.getCode(), "invalid_expiry_month") || Objects.equals(e.getCode(), "invalid_expiry_year") || Objects.equals(e.getCode(), "invalid_cvc")) {
                throw new PaymentException(PaymentException.Type.InvalidCard);
            } else {
                throw new RuntimeException("Stripe payment failed", e);
            }
        }
    }
}

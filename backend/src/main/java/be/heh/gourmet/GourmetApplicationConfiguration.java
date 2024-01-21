package be.heh.gourmet;

import be.heh.gourmet.adapter.out.payment.StripePaymentAdapter;
import be.heh.gourmet.adapter.out.persistence.CartAdapter;
import be.heh.gourmet.adapter.out.persistence.CategoryAdapter;
import be.heh.gourmet.adapter.out.persistence.ProductAdapter;
import be.heh.gourmet.adapter.out.persistence.UserAdapter;
import be.heh.gourmet.application.domain.service.ManageCartImpl;
import be.heh.gourmet.application.domain.service.ManagerOrderImpl;
import be.heh.gourmet.application.domain.service.PaymentImpl;
import be.heh.gourmet.application.port.in.*;
import be.heh.gourmet.application.port.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;


@Configuration
@EnableJdbcRepositories
public class GourmetApplicationConfiguration {
    // Repositories
    @Bean
    public ICartRepository getCartRepository() {
        return new CartAdapter();
    }

    @Bean
    public IUserRepository getUserRepository() {
        return new UserAdapter();
    }

    // Client
    @Bean
    public PaymentClient getPaymentClient() {
        return new StripePaymentAdapter();
    }

    // Crud only use cases
    @Bean
    public IManageProductUseCase getManageProductUseCase() {
        return new ProductAdapter();
    }

    @Bean
    IManageCategoryUseCase getManageCategoryUseCase() {
        return new CategoryAdapter();
    }

    // Use cases with business logic
    @Bean
    public IManageCartUseCase getManageCartUseCase() {
        return new ManageCartImpl();
    }

    @Bean
    IManageOrderUseCase getManageOrderUseCase() {
        return new ManagerOrderImpl();
    }

    @Bean
    IPaymentUseCase getPaymentUseCase() {
        return new PaymentImpl();
    }
}

package be.heh.gourmet;

import be.heh.gourmet.adapter.out.image.CloudinaryClient;
import be.heh.gourmet.adapter.out.payment.StripePaymentAdapter;
import be.heh.gourmet.adapter.out.persistence.CartAdapter;
import be.heh.gourmet.adapter.out.persistence.CategoryAdapter;
import be.heh.gourmet.adapter.out.persistence.ProductAdapter;
import be.heh.gourmet.adapter.out.persistence.UserAdapter;
import be.heh.gourmet.application.domain.service.ManageCartImpl;
import be.heh.gourmet.application.domain.service.ManagerOrderImpl;
import be.heh.gourmet.application.domain.service.PaymentImpl;
import be.heh.gourmet.application.port.in.*;
import be.heh.gourmet.application.port.out.ICartRepository;
import be.heh.gourmet.application.port.out.IPaymentClient;
import be.heh.gourmet.application.port.in.IManageUserUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;


@Configuration
@EnableJdbcRepositories
public class GourmetApplicationConfiguration {
    // Cross origin config
    @Bean
    @Profile("dev")
    public WebMvcConfigurer corsConfigurerDev() {
        // only in dev profile
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    @Profile("production")
    public WebMvcConfigurer corsConfigurerProd() {
        // only in dev profile
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }

    // Data sources
    @Value("${DB_URL}")
    private String DB_URL;
    @Value("${DB_USERNAME}")
    private String DB_USERNAME;
    @Value("${DB_PASSWORD}")
    private String DB_PASSWORD;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource config = new DriverManagerDataSource();
        config.setUrl(DB_URL);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);
        return config;
    }

    // Repositories
    @Bean
    public ICartRepository getCartRepository() {
        return new CartAdapter();
    }

    @Bean
    public IManageUserUseCase getUserRepository() {
        return new UserAdapter();
    }

    // Client
    @Bean
    public IPaymentClient getPaymentClient() {
        return new StripePaymentAdapter();
    }

    @Bean
    public IImageClient getImageClient() {
        return new CloudinaryClient();
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

    @Bean
    IManageUserUseCase getManageUserUseCase() {
        return new UserAdapter();
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

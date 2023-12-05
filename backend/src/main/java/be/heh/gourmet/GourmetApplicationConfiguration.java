package be.heh.gourmet;

import be.heh.gourmet.adapter.out.persistence.ProductAdapter;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;


@Configuration
@EnableJdbcRepositories
public class GourmetApplicationConfiguration {
    @Bean
    public IManageProductUseCase getManageProductUseCase() {
        return new ProductAdapter();
    }
}

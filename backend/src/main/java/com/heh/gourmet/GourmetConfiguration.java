package com.heh.gourmet;

import com.heh.gourmet.adapter.ou.persistence.CartAdapter;
import com.heh.gourmet.adapter.ou.persistence.CategoryAdapter;
import com.heh.gourmet.adapter.ou.persistence.ProductAdapter;
import com.heh.gourmet.application.domain.service.ManageCartImpl;
import com.heh.gourmet.application.domain.service.ManageCategoryImpl;
import com.heh.gourmet.application.domain.service.ManageProductImpl;
import com.heh.gourmet.application.port.in.IFetchUserData;
import com.heh.gourmet.application.port.in.IManageCartUseCase;
import com.heh.gourmet.application.port.in.IManageCategoryUseCase;
import com.heh.gourmet.application.port.in.IManageProductUseCase;
import com.heh.gourmet.application.port.out.ICartRepository;
import com.heh.gourmet.application.port.out.ICategoryRepository;
import com.heh.gourmet.application.port.out.IProductRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@EnableJdbcRepositories
@EnableConfigurationProperties(GourmetConfigurationProperties.class)
public class GourmetConfiguration {
    // Use Case Bean

    @Bean
    public IManageCartUseCase getIManageCartUseCase() {
        return new ManageCartImpl(getCartRepository(), getProductRepository());
    }

    @Bean
    public IManageCategoryUseCase getManageCategoryUseCase() {
        return new ManageCategoryImpl(getCategoryRepository());
    }

    @Bean
    IManageProductUseCase getManageProductUseCase() {
        return new ManageProductImpl(getProductRepository());
    }

    @Bean
    public IFetchUserData getFetchUserData() {
        return null;
    }

    // Repository Bean

    @Bean
    public ICategoryRepository getCategoryRepository() {
        return new CategoryAdapter();
    }

    @Bean
    public ICartRepository getCartRepository() {
        return new CartAdapter();
    }

    @Bean
    public IProductRepository getProductRepository() {
        return new ProductAdapter();
    }
}

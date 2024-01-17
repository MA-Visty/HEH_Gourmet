package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.application.domain.model.Category;
import be.heh.gourmet.application.domain.model.Product;
import be.heh.gourmet.application.port.in.IManageCategoryUseCase;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import be.heh.gourmet.application.port.in.InputProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("getManageProductUseCase")
    private IManageProductUseCase productManager;

    @MockBean
    @Qualifier("getManageCategoryUseCase")
    private IManageCategoryUseCase categoryManager;

    private final Product product1 = new Product(1, "Product 1", "Description 1", 10.0f, 1, new URL("https://localhost"), 1);
    private final Product product2 = new Product(2, "Product 2", "Description 2", 20.0f, 2, new URL("http://localhost"), 2);
    private final Category category1 = new Category(1, "Category 1", "Description 1");

    ProductControllerTest() throws MalformedURLException {
    }

    @Test
    void getProducts() throws Exception {
        List<Product> products = Arrays.asList(product1, product2);
        when(productManager.list()).thenReturn(products);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ID").value(product1.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(product1.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ID").value(product2.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(product2.name()));
    }

    @Test
    void getProductsByCategory() throws Exception {
        when(productManager.listByCategory(1)).thenReturn(List.of(product1));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ID").value(product1.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(product1.name()))
                // expect only one product
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").doesNotExist());

    }

    @Test
    void getProductsByCategoryWithInvalidCategory() throws Exception {
        when(productManager.listByCategory(1)).thenReturn(null);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addProduct() throws Exception {
        when(productManager.add(new InputProduct("Product 1", "Description 1", 10.0f, 1, new URL("https://localhost"), 1))).thenReturn(product1);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType("application/json")
                        .content("{\"name\":\"Product 1\",\"description\":\"Description 1\",\"price\":10.0,\"stock\":1,\"image\":\"https://localhost\",\"categoryID\":1}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ID").value(product1.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product1.name()));
    }

    @Test
    void addProductWithMissingCategory() throws Exception {
        when(productManager.add(new InputProduct("Product 1", "Description 1", 10.0f, 1, new URL("https://localhost"), 1))).thenReturn(product1);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType("application/json")
                        .content("{\"name\":\"Product 1\",\"description\":\"Description 1\",\"price\":10.0,\"stock\":1,\"image\":\"https://localhost\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void batchAddProducts() throws Exception {
        when(categoryManager.get(1)).thenReturn(Optional.of(category1));
        when(categoryManager.get(2)).thenReturn(Optional.of(category1));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType("application/json")
                        .content("[{\"name\":\"Product 1\",\"description\":\"Description 1\",\"price\":10.0,\"stock\":1,\"image\":\"https://localhost\",\"categoryID\":1}," +
                                "{\"name\":\"Product 2\",\"description\":\"Description 2\",\"price\":20.0,\"stock\":2,\"image\":\"http://localhost\",\"categoryID\":2}]"))
                .andExpect(status().isCreated());

    }

    @Test
    void batchAddProductWithMissingCategory() throws Exception {
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType("application/json")
                        .content("[{\"name\":\"Product 1\",\"description\":\"Description 1\",\"price\":10.0,\"stock\":1,\"image\":\"https://localhost\"}," +
                                "{\"name\":\"Product 2\",\"description\":\"Description 2\",\"price\":20.0,\"stock\":2,\"image\":\"http://localhost\"}]"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProduct() throws Exception {
        when(productManager.get(1)).thenReturn(Optional.of(product1));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ID").value(product1.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product1.name()));
    }

    @Test
    void getProductCategory() throws Exception {
        when(categoryManager.getByProduct(1)).thenReturn(category1);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/1/category"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ID").value(category1.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(category1.name()));
    }

    @Test
    void updateProduct() throws Exception {
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/1")
                        .contentType("application/json")
                        .content("{\"name\":\"Product 1\",\"description\":\"Description 1\",\"price\":10.0,\"stock\":1,\"image\":\"https://localhost\",\"categoryID\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    void removeProduct() throws Exception {
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void batchRemoveProducts() throws Exception {
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products")
                        .contentType("application/json")
                        .content("[1,2]"))
                .andExpect(status().isNoContent());
    }
}
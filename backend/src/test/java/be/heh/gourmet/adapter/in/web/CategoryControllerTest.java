package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.application.domain.model.Category;
import be.heh.gourmet.application.port.in.IManageCategoryUseCase;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import be.heh.gourmet.application.port.in.InputCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("getManageCategoryUseCase")
    private IManageCategoryUseCase categoryManager;

    private final Category category1 = new Category(1, "Category 1", "Description 1");
    private final Category category2 = new Category(2, "Category 2", "Description 2");

    @Test
    void getCategories() throws Exception {
        when(categoryManager.list()).thenReturn(List.of(category1, category2));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ID").value(category1.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(category1.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ID").value(category2.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(category2.name()));
    }

    @Test
    void batchAddCategory() throws Exception {
        // Act and Assert
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/categories")
                                .contentType("application/json")
                                .content("[{\"name\":\"Category 1\",\"description\":\"Description 1\"}," +
                                        "{\"name\":\"Category 2\",\"description\":\"Description 2\"}]"))
                .andExpect(status().isCreated());
    }

    @Test
    void removeCategories() throws Exception {
        // Act and Assert
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/categories")
                                .contentType("application/json")
                                .content("[1, 2]"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addCategory() throws Exception {
        when(categoryManager.add(new InputCategory("Category 1", "Description 1"))).thenReturn(category1);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/category")
                        .contentType("application/json")
                        .content("{\"name\":\"Category 1\",\"description\":\"Description 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ID").value(category1.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(category1.name()));
    }

    @Test
    void getCategory() throws Exception {
        when(categoryManager.get(1)).thenReturn(category1);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/category/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ID").value(category1.ID()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(category1.name()));
    }

    @Test
    void updateCategory() throws Exception {
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/category/1")
                        .contentType("application/json")
                        .content("{\"name\":\"Category 1\",\"description\":\"Description 1\"}"))
                .andExpect(status().isNoContent());

    }

    @Test
    void removeCategory() throws Exception {
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/category/1"))
                .andExpect(status().isNoContent());

    }
}
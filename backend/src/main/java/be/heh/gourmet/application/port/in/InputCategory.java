package be.heh.gourmet.application.port.in;

import be.heh.gourmet.application.domain.model.Category;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

@NonNull
public record InputCategory(
        @Size(min = 1, max = 255, message = "name should be between 1 and 255 characters")
        String name,
        @Size(min = 1, max = 2048, message = "description should be between 1 and 2048 characters")
        String description) {
    public Category asCategory(int ID) {
        InputCategory category = this;
        return new Category(ID, category.name(), category.description());
    }

    public InputCategory fromCategory(Category category) {
        return new InputCategory(category.name(), category.description());
    }
}

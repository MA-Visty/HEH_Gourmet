package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.application.domain.model.Category;
import be.heh.gourmet.application.port.in.IManageCategoryUseCase;
import be.heh.gourmet.application.port.in.InputCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    @Qualifier("getManageCategoryUseCase")
    IManageCategoryUseCase categoryManager;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories(@RequestBody Optional<List<Integer>> ids) {
        if (ids.isPresent()) {
            List<Category> categories = categoryManager.batchGet(ids.get());
            if (categories == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(categories);
        }

        List<Category> categories = categoryManager.list();
        if (categories == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public void batchAddCategory(@RequestBody List<InputCategory> categories) {
        categoryManager.batchAdd(categories);
    }

    @DeleteMapping("/categories")
    public ResponseEntity<List<Category>> removeCategories(@RequestBody List<Integer> ids) {
        categoryManager.batchRemove(ids);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/category")
    public ResponseEntity<Category> addCategory(@RequestBody InputCategory category) {
        Category response = categoryManager.add(category);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable int id) {
        Category category = categoryManager.get(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping("/category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody InputCategory category) {
        categoryManager.update(id, category);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Category> removeCategory(@PathVariable int id) {
        categoryManager.remove(id);
        return ResponseEntity.noContent().build();
    }
}

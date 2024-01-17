package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.adapter.in.web.exeption.InternalServerError;
import be.heh.gourmet.adapter.out.persistence.exception.CategoryException;
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
    public ResponseEntity<Object> getCategories(@RequestBody Optional<List<Integer>> ids) {
        try {
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
        } catch (CategoryException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public void batchAddCategory(@RequestBody List<InputCategory> categories) {
        categoryManager.batchAdd(categories);
    }

    @DeleteMapping("/categories")
    public ResponseEntity<Object> removeCategories(@RequestBody List<Integer> ids) {
        try {
            categoryManager.batchRemove(ids);
            return ResponseEntity.noContent().build();
        } catch (CategoryException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/category")
    public ResponseEntity<Object> addCategory(@RequestBody InputCategory category) {
        try {
            Category response = categoryManager.add(category);
            if (response == null) {
                throw new CategoryException("Category not created", CategoryException.Type.CATEGORY_NOT_CREATED);
            }
            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<>(response, responseHeaders, HttpStatus.CREATED);
        } catch (CategoryException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Object> getCategory(@PathVariable int id) {
        try {
            Optional<Category> category = categoryManager.get(id);
            if (category.isEmpty()) {
                throw new CategoryException("Category not found", CategoryException.Type.CATEGORY_NOT_FOUND);
            }
            return ResponseEntity.ok(category.get());
        } catch (CategoryException e) {
            return ResponseEntity.status(e.httpStatus()).body(e.toResponse());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/category/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable int id, @RequestBody InputCategory category) {
        try {
            categoryManager.update(id, category);
            return ResponseEntity.noContent().build();
        } catch (CategoryException e) {
            return ResponseEntity.status(e.httpStatus()).body(e.toResponse());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Object> removeCategory(@PathVariable int id) {
        try {
            categoryManager.remove(id);
            return ResponseEntity.noContent().build();
        } catch (CategoryException e) {
            return ResponseEntity.status(e.httpStatus()).body(e.toResponse());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }
}

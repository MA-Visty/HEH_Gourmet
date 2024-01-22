package be.heh.gourmet.backend.adapter;

import be.heh.gourmet.backend.application.domain.model.Ingredient;
import be.heh.gourmet.backend.application.domain.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private static final Logger logger = LoggerFactory.getLogger(IngredientController.class);

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Ingredient ingredient) {
        logger.debug("Ingredient create controller called");
        ingredientService.create(ingredient);
        return new ResponseEntity<>("Ingredient create successfully ! ", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> findAll() {
        logger.debug("Ingredient findAll controller called");
        List<Ingredient> ingredients = ingredientService.findAll();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> findOne(@PathVariable Long id) {
        logger.debug("Ingredient findOne controller called with id: {}", id);
        Ingredient ingredient = ingredientService.findOne(id);
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> update(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        logger.debug("Ingredient update controller called with id: {}", id);
        Ingredient ingredientRes = ingredientService.update(id, ingredient);
        return new ResponseEntity<>(ingredientRes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.debug("Ingredient delete controller called with id: {}", id);
        ingredientService.delete(id);
        return new ResponseEntity<>("Ingredient deleted successfully !", HttpStatus.OK);
    }
}

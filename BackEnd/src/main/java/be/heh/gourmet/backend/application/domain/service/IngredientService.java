package be.heh.gourmet.backend.application.domain.service;

import be.heh.gourmet.backend.common.exception.NotFoundException;
import be.heh.gourmet.backend.application.domain.model.Ingredient;
import be.heh.gourmet.backend.application.port.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private static final Logger logger = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient create(Ingredient ingredient) {
        logger.debug("Ingredient create action called");
        return ingredientRepository.save(ingredient);
    }

    public Ingredient findOne(Long ingredientId) {
        logger.debug("Ingredient findById action called");
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new NotFoundException("Ingredient of id " +  ingredientId + " not found."));
    }

    public List<Ingredient> findAll() {
        logger.debug("Ingredient findAll action called");
        return ingredientRepository.findAll();
    }

    public Ingredient update(Long ingredientId, Ingredient ingredient) {
        logger.debug("Ingredient update action called");
        Ingredient foundIngredient = findOne(ingredientId);

        logger.debug("Ingredient found");

        foundIngredient.setName(ingredient.getName());
        foundIngredient.setPrice(ingredient.getPrice());
        foundIngredient.setDescription(ingredient.getDescription());
        ingredientRepository.save(foundIngredient);

        logger.debug("Ingredient updated successfully");
        return foundIngredient;
    }

    public void delete(Long ingredientId) {
        logger.debug("Ingredient delete action called");
        ingredientRepository.deleteById(ingredientId);
    }
}

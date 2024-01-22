package be.heh.gourmet.backend.application.domain.service;

import be.heh.gourmet.backend.common.exception.NotFoundException;
import be.heh.gourmet.backend.application.domain.model.TypeProduct;
import be.heh.gourmet.backend.application.port.TypeProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeProductService {

    private static final Logger logger = LoggerFactory.getLogger(TypeProductService.class);

    private final TypeProductRepository typeProductRepository;

    @Autowired
    public TypeProductService(TypeProductRepository typeProductRepository) {
        this.typeProductRepository = typeProductRepository;
    }

    public TypeProduct create(TypeProduct typeProduct) {
        logger.debug("TypeProduct create action called");
        return typeProductRepository.save(typeProduct);
    }

    public TypeProduct findOne(Long typeProductId) {
        logger.debug("TypeProduct findById action called");
        return typeProductRepository.findById(typeProductId)
                .orElseThrow(() -> new NotFoundException("TypeProduct of id " +  typeProductId + " not found."));
    }

    public List<TypeProduct> findAll() {
        logger.debug("TypeProduct findAll action called");
        return typeProductRepository.findAll();
    }

    public TypeProduct update(Long typeProductId, TypeProduct typeProduct) {
        logger.debug("TypeProduct update action called");
        TypeProduct foundTypeProduct = findOne(typeProductId);

        logger.debug("TypeProduct found");
        foundTypeProduct.setTypeName(typeProduct.getTypeName());
        typeProductRepository.save(foundTypeProduct);

        logger.debug("TypeProduct updated successfully");
        return foundTypeProduct;
    }

    public void delete(Long typeProductId) {
        logger.debug("TypeProduct delete action called");
        typeProductRepository.deleteById(typeProductId);
    }
}

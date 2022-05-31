package ru.tkachenko.buyerassistant.product.type.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.product.type.repository.ProductTypeRepository;

import java.util.List;

@Service
public class ProductTypeService {
    ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public List<ProductTypeEntity> findProductTypeEntitiesByProductGroup(ProductGroupEntity productGroupEntity) {
        return productTypeRepository.findAllByGroup(productGroupEntity);
    }
}

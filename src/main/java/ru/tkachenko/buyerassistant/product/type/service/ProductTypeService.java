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

    public List<ProductTypeEntity> findAll() {
        return productTypeRepository.findAll();
    }

    public void save(ProductTypeEntity productTypeEntity) {
        productTypeRepository.save(productTypeEntity);
    }

    public String findGroupByProductTypeName(String productTypeName) {
        ProductTypeEntity productType = productTypeRepository.findByName(productTypeName);
        System.out.println(productType);

        ProductGroupEntity productGroup = productType.getProductGroup();
        System.out.println(productGroup);

        String name = productGroup.getName();

        return name;
    }

}

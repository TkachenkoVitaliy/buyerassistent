package ru.tkachenko.buyerassistant.total.product.group.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.total.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.total.product.group.repository.ProductGroupRepository;
import ru.tkachenko.buyerassistant.total.product.type.entity.ProductTypeEntity;

import java.util.List;

@Service
public class ProductGroupService {
    ProductGroupRepository productGroupRepository;

    @Autowired
    public ProductGroupService(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }

    public void save(ProductGroupEntity productGroupEntity){
        productGroupRepository.save(productGroupEntity);
    }

    public List<ProductGroupEntity> findAll(){
        return productGroupRepository.findAll();
    }

    public List<ProductTypeEntity> productTypesByProductGroupName(String name) {
        return findFirstByProductGroup(name).getProductTypes();
    }

    public ProductGroupEntity findFirstByProductGroup(String name){
        return productGroupRepository.findFirstByName(name);
    }

}

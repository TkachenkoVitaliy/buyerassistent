package ru.tkachenko.buyerassistant.product.group.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.product.group.repository.ProductGroupRepository;

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
    public void deleteAll() {
        productGroupRepository.deleteAll();
    }

    public ProductGroupEntity findFirstByProductGroup(String productGroup){
        return productGroupRepository.findFirstByProductGroup(productGroup);
    }

}

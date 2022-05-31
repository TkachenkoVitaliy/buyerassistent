package ru.tkachenko.buyerassistant.product.group.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.product.group.repository.ProductGroupRepository;

@Service
public class ProductGroupService {
    ProductGroupRepository productGroupRepository;

    @Autowired
    public ProductGroupService(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }
}

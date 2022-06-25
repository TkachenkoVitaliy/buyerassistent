package ru.tkachenko.buyerassistant.total.product.type.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.total.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.total.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.total.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.total.product.type.repository.ProductTypeRepository;

import java.util.List;

@Service
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;
    private final ProductGroupService productGroupService;


    @Autowired
    public ProductTypeService(ProductTypeRepository productTypeRepository, ProductGroupService productGroupService) {
        this.productTypeRepository = productTypeRepository;
        this.productGroupService = productGroupService;
    }

    public List<ProductTypeEntity> findAll() {
        return productTypeRepository.findAll();
    }

    public ProductTypeEntity findByName(String name){
        return productTypeRepository.findByName(name);
    }

    public void save(ProductTypeEntity productTypeEntity) {
        productTypeRepository.save(productTypeEntity);
    }

    public String findGroupByProductTypeName(String productTypeName) {
        ProductTypeEntity productType = productTypeRepository.findByName(productTypeName);
        ProductGroupEntity productGroup = productType.getProductGroup();
        String name = productGroup.getName();
        return name;
    }

    public List<ProductTypeEntity> findUndefinedProductTypes() {
        String undefined = "Не определена";
        ProductGroupEntity undefinedProductGroup = productGroupService.findFirstByProductGroup(undefined);
        return productTypeRepository.findAllByProductGroupOrderById(undefinedProductGroup);
    }

    public void updateUndefinedProductTypes(List<String> updatedGroupNames) {
        List<ProductTypeEntity> undefinedProductTypes = findUndefinedProductTypes();
        if (!undefinedProductTypes.isEmpty()) {
            for(int i = 0; i < undefinedProductTypes.size(); i++) {
                ProductTypeEntity productTypeEntity = undefinedProductTypes.get(i);
                String updatedGroupName = updatedGroupNames.get(i);
                ProductGroupEntity productGroupEntity = productGroupService.findFirstByProductGroup(updatedGroupName);
                productTypeEntity.setProductGroup(productGroupEntity);
                save(productTypeEntity);
            }
        }
    }
}

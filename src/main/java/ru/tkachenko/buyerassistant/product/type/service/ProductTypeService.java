package ru.tkachenko.buyerassistant.product.type.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.mmk_accept.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistant.mmk_accept.exception.AcceptParseException;
import ru.tkachenko.buyerassistant.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.product.type.repository.ProductTypeRepository;
import ru.tkachenko.buyerassistant.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class ProductTypeService {
    ProductTypeRepository productTypeRepository;



    @Autowired
    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public List<ProductTypeEntity> findProductTypeEntitiesByProductGroupId(ProductGroupEntity productGroupEntity) {
        return productTypeRepository.findAllByGroupId(productGroupEntity.getId());
    }


}

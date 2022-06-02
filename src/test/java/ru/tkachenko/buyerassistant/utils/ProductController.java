package ru.tkachenko.buyerassistant.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.tkachenko.buyerassistant.product.ProductService;
import ru.tkachenko.buyerassistant.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.product.type.repository.ProductTypeRepository;

import java.nio.file.Path;
import java.util.List;

@RestController
public class ProductController {
    ProductService productService;
    ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductController(ProductService productService, ProductTypeRepository productTypeRepository) {
        this.productService = productService;
        this.productTypeRepository = productTypeRepository;
    }

    @Test
    void checkParser() {
        Path path = Path.of("C:\\Users\\Administrator\\Downloads\\Telegram Desktop\\type-group.xlsx");
        productService.parseFileToDatabase(path);
        List<ProductTypeEntity> entities = productTypeRepository.findAllByProductGroupEntity(new ProductGroupEntity("Фасон"));
        entities.forEach(System.out::println);
    }
}

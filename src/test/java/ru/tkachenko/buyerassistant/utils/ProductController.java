package ru.tkachenko.buyerassistant.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tkachenko.buyerassistant.product.ProductParser;
import ru.tkachenko.buyerassistant.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.product.type.service.ProductTypeService;

import java.nio.file.Path;
import java.util.List;

@RestController
public class ProductController {
    ProductParser productParser;
    ProductTypeService productTypeService;
    ProductGroupService productGroupService;

    @Autowired
    public ProductController(ProductParser productParser, ProductTypeService productTypeService, ProductGroupService productGroupService) {
        this.productParser = productParser;
        this.productTypeService = productTypeService;
        this.productGroupService = productGroupService;
    }



    @GetMapping("/test")
    public String checkParser() {
        Path path = Path.of("C:\\Users\\Admin\\Downloads\\Telegram Desktop\\type-group.xlsx");
        productParser.parseFileToDatabase(path);
        ProductGroupEntity en = productGroupService.findFirstByProductGroup("Арматура");
        List<ProductTypeEntity> entities = productTypeService.findAllByGroupId(en.getId());
        System.out.println(productTypeService.findAll().size());
        System.out.println(productGroupService.findAll().size());
        System.out.println(entities.size());
        entities.forEach(System.out::println);
        return "HI";
    }
}

package ru.tkachenko.buyerassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tkachenko.buyerassistant.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.product.type.service.ProductTypeService;

//Test class
//TODO Delete after testing

@RestController
public class ProductController {
    ProductTypeService productTypeService;
    ProductGroupService productGroupService;

    @Autowired
    public ProductController(ProductTypeService productTypeService, ProductGroupService productGroupService) {
        this.productTypeService = productTypeService;
        this.productGroupService = productGroupService;
    }


    @GetMapping("/test")
    public String checkParser() {
        System.out.println(productTypeService.findAllByGroupId(productGroupService.findFirstByProductGroup("Прокат г/к").getId()));
        System.out.println(productTypeService.findAllByGroupId(productGroupService.findFirstByProductGroup("Полимеры").getId()));
        System.out.println(productTypeService.findAllByGroupId(productGroupService.findFirstByProductGroup("Арматура").getId()));
        return "HI";
    }
}

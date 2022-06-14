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
        System.out.println("квадрат");
        System.out.println(productTypeService.findGroupByProductTypeName("квадрат"));
//        System.out.println("лист г/к чеч");
//        System.out.println(productTypeService.findGroupByProductTypeName("лист г/к чеч"));
        System.out.println("Рулон ГЦ_полимер");
        System.out.println(productTypeService.findGroupByProductTypeName("Рулон ГЦ_полимер"));
//        System.out.println("Профиль арматурный_моток");
//        System.out.println(productTypeService.findGroupByProductTypeName("Профиль арматурный_моток"));
//        System.out.println("УГОЛ");
//        System.out.println(productTypeService.findGroupByProductTypeName("УГОЛ"));
        return "HI";
    }
}

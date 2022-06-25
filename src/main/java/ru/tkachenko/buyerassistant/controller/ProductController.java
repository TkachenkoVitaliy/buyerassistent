package ru.tkachenko.buyerassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tkachenko.buyerassistant.summary.service.SummaryDBService;
import ru.tkachenko.buyerassistant.total.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.total.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.total.product.type.service.ProductTypeService;
import ru.tkachenko.buyerassistant.total.service.TotalService;

import java.util.List;

//Test class
//TODO Delete after testing

@RestController
public class ProductController {
    private final ProductTypeService productTypeService;
    private final ProductGroupService productGroupService;
    private final TotalService totalService;
    private final SummaryDBService summaryDBService;

    @Autowired
    public ProductController(ProductTypeService productTypeService, ProductGroupService productGroupService,
                             TotalService totalService, SummaryDBService summaryDBService) {
        this.productTypeService = productTypeService;
        this.productGroupService = productGroupService;
        this.totalService = totalService;
        this.summaryDBService = summaryDBService;
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

    @GetMapping("/test2")
    public String testTotal() {
        List<String> allProductTypeNames = summaryDBService.findAllProductTypeNames();
        for(String productType : allProductTypeNames) {
            ProductTypeEntity productTypeEntity = productTypeService.findByName(productType);
            if(productTypeEntity == null) {
                productTypeEntity = new ProductTypeEntity();
                productTypeEntity.setName(productType);
                productTypeEntity.setProductGroup(productGroupService.findFirstByProductGroup("Не определена"));
                productTypeService.save(productTypeEntity);
            }
        }

         List<ProductTypeEntity> allProductTypes = productTypeService.findAll();
        StringBuilder builder = new StringBuilder();

        for(ProductTypeEntity productType : allProductTypes) {
            builder.append(productType.getName()).append(" - ");
            builder.append(productType.getProductGroup()).append("\n");
        }

        return builder.toString();

    }
}

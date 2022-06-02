package ru.tkachenko.buyerassistant.product;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.product.group.repository.ProductGroupRepository;
import ru.tkachenko.buyerassistant.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.product.type.repository.ProductTypeRepository;
import ru.tkachenko.buyerassistant.product.type.service.ProductTypeService;
import ru.tkachenko.buyerassistant.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Service
public class ProductParser {
    private final ProductTypeService productTypeService;
    private final ProductGroupService productGroupService;

    private final String PRODUCT_TYPE = "product_type";
    private final String PRODUCT_GROUP = "product_group";
    private final String[] columnsNames = {PRODUCT_TYPE, PRODUCT_GROUP};

    @Autowired
    public ProductParser(ProductTypeService productTypeService, ProductGroupService productGroupService) {
        this.productTypeService = productTypeService;
        this.productGroupService = productGroupService;
    }



    public void parseFileToDatabase(Path filePath) {
        productTypeService.deleteAll();
        productGroupService.deleteAll();

        try (FileInputStream fileInputStream = new FileInputStream(filePath.toString());
             XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int headerRowIndex = ExcelUtils.findFirstNotBlankRow(sheet);
            int firstRowIndex = headerRowIndex + 1;
            int lastRowIndex = sheet.getLastRowNum();
            int[] colIndexes = ExcelUtils.getEntityColumnsIndexes(sheet, headerRowIndex, columnsNames);
            Set<ProductGroupEntity> productGroupEntitySet = new HashSet<>();

            //TODO How to improve it (Ihor)
            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                Row currentRow = sheet.getRow(i);
                ProductGroupEntity entity = parseProductGroupEntityFromRow(colIndexes, currentRow);
                productGroupEntitySet.add(entity);
            }
            productGroupEntitySet.forEach(productGroupService::save);//parallelStream?

            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                Row currentRow = sheet.getRow(i);
                ProductTypeEntity entity = parseProductTypeEntityFromRow(colIndexes, currentRow);
                productTypeService.save(entity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ProductTypeEntity parseProductTypeEntityFromRow(int[] colIndexes, Row row) {
        String type = ExcelUtils.getStringValue(colIndexes[0], row);
        String group = ExcelUtils.getStringValue(colIndexes[1], row);
        Long id = productGroupService.findFirstByProductGroup(group).getId();
        return new ProductTypeEntity(type, id);
    }

    private ProductGroupEntity parseProductGroupEntityFromRow(int[] colIndexes, Row row) {
        String group = ExcelUtils.getStringValue(colIndexes[1], row);
        return new ProductGroupEntity(group);
    }
}

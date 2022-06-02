package ru.tkachenko.buyerassistant.product;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.mmk_accept.exception.AcceptParseException;
import ru.tkachenko.buyerassistant.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.product.group.repository.ProductGroupRepository;
import ru.tkachenko.buyerassistant.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.product.type.repository.ProductTypeRepository;
import ru.tkachenko.buyerassistant.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Service
public class ProductService {
    private final ProductTypeRepository productTypeRepository;
    private final ProductGroupRepository productGroupRepository;
    private final String PRODUCT_TYPE = "product_type";
    private final String PRODUCT_GROUP = "product_group";
    private final String[] columnsNames = {PRODUCT_TYPE, PRODUCT_GROUP};

    @Autowired
    public ProductService(ProductTypeRepository productTypeRepository,
                          ProductGroupRepository productGroupRepository) {
        this.productTypeRepository = productTypeRepository;
        this.productGroupRepository = productGroupRepository;
    }

    public void parseFileToDatabase(Path filePath) {

        try (FileInputStream fileInputStream = new FileInputStream(filePath.toString());
             HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int headerRowIndex = ExcelUtils.findFirstNotBlankRow(sheet);
            int firstRowIndex = headerRowIndex + 1;
            int lastRowIndex = sheet.getLastRowNum();
            int[] colIndexes = ExcelUtils.getEntityColumnsIndexes(sheet, headerRowIndex, columnsNames);
            Set<ProductGroupEntity> productGroupEntitySet = new HashSet<>();

            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                Row currentRow = sheet.getRow(i);
                ProductGroupEntity entity = parseProductGroupEntityFromRow(colIndexes, currentRow);
                productGroupEntitySet.add(entity);
            }
            productGroupEntitySet.forEach(System.out::println);
            productGroupEntitySet.forEach(productGroupRepository::save);//parallelStream?

            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                Row currentRow = sheet.getRow(i);
                ProductTypeEntity entity = parseProductTypeEntityFromRow(colIndexes, currentRow);
                productTypeRepository.save(entity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ProductTypeEntity parseProductTypeEntityFromRow(int[] colIndexes, Row row) {
        String type = ExcelUtils.getStringValue(colIndexes[0], row);
        String group = ExcelUtils.getStringValue(colIndexes[1], row);
        Long id = productGroupRepository.findFirstByProductGroup(group).getId();
        return new ProductTypeEntity(type, id);
    }

    private ProductGroupEntity parseProductGroupEntityFromRow(int[] colIndexes, Row row) {
        String group = ExcelUtils.getStringValue(colIndexes[1], row);
        return new ProductGroupEntity(group);
    }
}

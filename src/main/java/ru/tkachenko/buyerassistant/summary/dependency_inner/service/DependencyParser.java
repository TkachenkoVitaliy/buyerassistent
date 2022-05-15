package ru.tkachenko.buyerassistant.summary.dependency_inner.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.summary.dependency_inner.entity.DependencyEntity;
import ru.tkachenko.buyerassistant.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class DependencyParser {

    private final DependencyDBService dependencyDBService;
    String[] columnNames = {"Грузополучатель", "Станция", "Номер СПЕЦ", "Номер позиции", "База", "Вид поставки",
            "Транзитн. Клиент"};
    String defaultSheetName = "Склады";
    String transitsSheetName = "Прямые транзиты";
    String containersSheetName = "Контейнеры";
    String exceptionalSheetName = "Исключения";

    @Autowired
    public DependencyParser(DependencyDBService dependencyDBService) {
        this.dependencyDBService = dependencyDBService;
    }

    public void parse(Path dependenciesMmkPath) {
        try(FileInputStream fis = new FileInputStream(dependenciesMmkPath.toString());
            XSSFWorkbook wb = new XSSFWorkbook(fis)) {
            dependencyDBService.truncateTable();
            parseSheetToDatabase(wb.getSheet(defaultSheetName), 1);
            parseSheetToDatabase(wb.getSheet(transitsSheetName), 2);
            parseSheetToDatabase(wb.getSheet(containersSheetName), 3);
            parseSheetToDatabase(wb.getSheet(exceptionalSheetName), 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseSheetToDatabase(Sheet sheet, int priority) {
        int headerRowIndex = ExcelUtils.findFirstNotBlankRow(sheet);
        int firstRowIndex = headerRowIndex + 1;
        int lastRowIndex = sheet.getLastRowNum();
        int[] colIndexes = ExcelUtils.getEntityColumnsIndexes(sheet, headerRowIndex, columnNames);
        for (int i = firstRowIndex; i <= lastRowIndex; i++) {
            Row currentRow = sheet.getRow(i);
            DependencyEntity dependencyEntity = parseRowToEntity(colIndexes, currentRow, priority);
            dependencyDBService.save(dependencyEntity);
        }
    }


    private DependencyEntity parseRowToEntity(int[] colIndexes, Row row, int priority) {

        String consignee = (colIndexes[0] != -1) ? ExcelUtils.getStringValue(colIndexes[0], row) : null;
        String station = (colIndexes[1] != -1) ? ExcelUtils.getStringValue(colIndexes[1], row) : null;
        String spec = (colIndexes[2] != -1) ? ExcelUtils.getStringValue(colIndexes[2], row) : null;
        int position = (colIndexes[3] != -1) ? ExcelUtils.getIntValue(colIndexes[3], row) : -1;
        String branch = ExcelUtils.getStringValue(colIndexes[4], row);
        String sellType = ExcelUtils.getStringValue(colIndexes[5], row);
        String client = ExcelUtils.getStringValue(colIndexes[6], row);
        return new DependencyEntity(consignee, station, spec, position, branch, sellType, client, priority);

    }
}

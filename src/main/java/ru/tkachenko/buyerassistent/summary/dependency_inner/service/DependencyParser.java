package ru.tkachenko.buyerassistent.summary.dependency_inner.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.summary.dependency_inner.entity.DependencyEntity;
import ru.tkachenko.buyerassistent.utils.ExcelUtils;

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
            parseSheet(wb.getSheet(defaultSheetName), 1);
            parseSheet(wb.getSheet(transitsSheetName), 2);
            parseSheet(wb.getSheet(containersSheetName), 3);
            parseSheet(wb.getSheet(exceptionalSheetName), 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseSheet(Sheet sheet, int priority) {
        int headerRowIndex = ExcelUtils.findFirstNotBlankRow(sheet);
        int firstRowIndex = headerRowIndex + 1;
        int lastRowIndex = sheet.getLastRowNum();
        int[] colIndexes = ExcelUtils.findColIndexesByValues(columnNames, sheet.getRow(headerRowIndex));
        for (int i = firstRowIndex; i <= lastRowIndex; i++) {
            Row currentRow = sheet.getRow(i);
            DependencyEntity dependencyEntity = parseRowToEntity(colIndexes, currentRow, priority);
            dependencyDBService.save(dependencyEntity);
        }
    }


    private DependencyEntity parseRowToEntity(int[] colIndexes, Row row, int priority) {
        String consignee = null;
        if(colIndexes[0] != -1) consignee = ExcelUtils.getStringValue(colIndexes[0], row);

        String station = null;
        if(colIndexes[1] != -1) station = ExcelUtils.getStringValue(colIndexes[1], row);

        String spec = null;
        if(colIndexes[2] != -1) spec = ExcelUtils.getStringValue(colIndexes[2], row);

        int position = -1;
        if(colIndexes[3] != -1) position = ExcelUtils.getIntValue(colIndexes[3], row);

        String branch = ExcelUtils.getStringValue(colIndexes[4], row);

        String sellType = ExcelUtils.getStringValue(colIndexes[5], row);

        String client = ExcelUtils.getStringValue(colIndexes[6], row);

        return new DependencyEntity(consignee, station, spec, position, branch, sellType, client, priority);

    }
}

package ru.tkachenko.buyerassistant.summary.other_factory_inner.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.summary.service.SummaryDBService;
import ru.tkachenko.buyerassistant.summary.service.SummaryInfoUtil;
import ru.tkachenko.buyerassistant.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class OtherFactoriesParser {

    private final List<String> monthSheetNames = new ArrayList<>();
    private final SummaryDBService summaryDBService;

    @Autowired
    public OtherFactoriesParser(SummaryDBService summaryDBService) {
        this.summaryDBService = summaryDBService;
        String[] months = SummaryInfoUtil.getMonthSheetNames();
        for(int i = 2021; i <= 2026; i++) {
            for (String month : months) {
                monthSheetNames.add(month + "_" + i);
            }
        }
    }

    public void parse(Path filePath) {
        try(FileInputStream fis = new FileInputStream(filePath.toString());
            XSSFWorkbook wb = new XSSFWorkbook(fis)) {
            monthSheetNames.parallelStream()
                    .map(wb::getSheet)
                    .filter(Objects::nonNull)
                    .forEach(this::parseSheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseSheet(Sheet sheet) {
        String[] columnsNames = SummaryInfoUtil.getFileColumnsNamesForEntity();
        int headerRowIndex = ExcelUtils.findFirstNotBlankRow(sheet);
        int firstRowIndex = headerRowIndex + 1;
        int lastRowIndex = sheet.getLastRowNum();
        int[] colIndexes = ExcelUtils.getEntityColumnsIndexes(sheet, headerRowIndex, columnsNames);
        for (int i = firstRowIndex; i <= lastRowIndex; i++) {
            Row currentRow = sheet.getRow(i);
            SummaryRowEntity entity = parseSummaryEntityFromRow(colIndexes, currentRow);
            summaryDBService.save(entity);
        }
    }

    private SummaryRowEntity parseSummaryEntityFromRow(int[] colIndexes, Row row) {
        String supplier = ExcelUtils.getStringValue(colIndexes[0],row);
        int mill = ExcelUtils.getIntValue(colIndexes[1], row);
        String branch = ExcelUtils.getStringValue(colIndexes[2], row);
        String sellType = ExcelUtils.getStringValue(colIndexes[3], row);
        String client = ExcelUtils.getStringValue(colIndexes[4], row);
        String consignee = ExcelUtils.getStringValue(colIndexes[5], row);
        String productType = ExcelUtils.getStringValue(colIndexes[6], row);
        String profile = ExcelUtils.getStringValue(colIndexes[7], row);
        String grade = ExcelUtils.getStringValue(colIndexes[8], row);
        String ral = ExcelUtils.getStringValue(colIndexes[9], row);
        double issued = ExcelUtils.getDoubleValue(colIndexes[10], row);
        String contract = ExcelUtils.getStringValue(colIndexes[11], row);
        String spec = ExcelUtils.getStringValue(colIndexes[12], row);
        int position = ExcelUtils.getIntValue(colIndexes[13], row);
        int acceptMonth = ExcelUtils.getIntValue(colIndexes[14], row);
        int year = ExcelUtils.getIntValue(colIndexes[15], row);
        double accepted = ExcelUtils.getDoubleValue(colIndexes[16], row);
        double price = ExcelUtils.getDoubleValue(colIndexes[17], row);
        double acceptedCost = ExcelUtils.getDoubleValue(colIndexes[18], row);
        double shipped = ExcelUtils.getDoubleValue(colIndexes[19], row);
        double shippedCost = ExcelUtils.getDoubleValue(colIndexes[20], row);

        SimpleDateFormat shippedDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date shippedDate = ExcelUtils.getDateValue(colIndexes[21], row, shippedDateFormat);

        String vehicleNumber = ExcelUtils.getStringValue(colIndexes[22], row);
        int invoiceNumber = ExcelUtils.getIntValue(colIndexes[23], row);

        SimpleDateFormat invoiceDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date invoiceDate = ExcelUtils.getDateValue(colIndexes[24], row, invoiceDateFormat);

        double finalPrice = ExcelUtils.getDoubleValue(colIndexes[25], row);
        double finalCost = ExcelUtils.getDoubleValue(colIndexes[26], row);

        return new SummaryRowEntity(supplier, mill, branch, sellType, client, consignee, productType, profile, grade,
                ral, issued, contract, spec, position, acceptMonth, year, accepted, price, acceptedCost, shipped,
                shippedCost, shippedDate, vehicleNumber, invoiceNumber, invoiceDate, finalPrice, finalCost);
    }

}

package ru.tkachenko.buyerassistent.service.summary;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.tkachenko.buyerassistent.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistent.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistent.utils.ExcelUtils;

import javax.persistence.Column;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Date;

public class OtherFactoriesParser {
    private final Path filePath;
    private final String[] monthSheetNames = {"Январь_2022", "Февраль_2022", "Март_2022", "Апрель_2022", "Май_2022",
            "Июнь_2022", "Июль_2022", "Август_2022", "Сентябрь_2022", "Октябрь_2022", "Ноябрь_2022", "Декабрь_2022"};


    public OtherFactoriesParser(Path filePath) {
        this.filePath = filePath;
    }

    public void parse() {
        try {
            FileInputStream fis = new FileInputStream(filePath.toString());
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            for(String monthSheetName : monthSheetNames) {
                XSSFSheet sheet = wb.getSheet(monthSheetName);
                if(sheet != null) parseSheet(sheet);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseSheet(Sheet sheet) {
        String[] columnsNames = SummaryInfoUtil.getFileColumnsNamesForEntity();
        int headerRowIndex = ExcelUtils.findFirstNotBlankRow(sheet);
        int firstRowIndex = headerRowIndex + 1;
        int lastRowIndex = sheet.getLastRowNum();
        int[] colIndexes = ExcelUtils.getEntityColumns(sheet, headerRowIndex, columnsNames);
        for (int i = firstRowIndex; i <= lastRowIndex; i++) {
            Row currentRow = sheet.getRow(i);
            SummaryRowEntity entity = parseSummaryEntityFromRow(colIndexes, currentRow);
            //mmkAcceptDBService.addUniqueEntity(entity);
        }
    }

    public SummaryRowEntity parseSummaryEntityFromRow(int[] colIndexes, Row row) {
        String supplier = ExcelUtils.getStringValue(colIndexes[0],row);
        int mill = ExcelUtils.getIntValue(colIndexes[1], row);
        String branch = ExcelUtils.getStringValue(colIndexes[2], row);
        String sell_type = ExcelUtils.getStringValue(colIndexes[3], row);
        String client = ExcelUtils.getStringValue(colIndexes[4], row);
        String consignee = ExcelUtils.getStringValue(colIndexes[5], row);
        String product_type = ExcelUtils.getStringValue(colIndexes[6], row);
        String profile = ExcelUtils.getStringValue(colIndexes[7], row);
        String grade = ExcelUtils.getStringValue(colIndexes[8], row);
        String ral = ExcelUtils.getStringValue(colIndexes[9], row);
        double issued = ExcelUtils.getDoubleValue(colIndexes[10], row);
        String contract = ExcelUtils.getStringValue(colIndexes[11], row);
        String spec = ExcelUtils.getStringValue(colIndexes[12], row);
        int position = ExcelUtils.getIntValue(colIndexes[13], row);
        int accept_month = ExcelUtils.getIntValue(colIndexes[14], row);
        int year = ExcelUtils.getIntValue(colIndexes[15], row);
        double accepted = ExcelUtils.getDoubleValue(colIndexes[16], row);
        double price = ExcelUtils.getDoubleValue(colIndexes[17], row);
        double accepted_cost = ExcelUtils.getDoubleValue(colIndexes[18], row);
        double shipped = ExcelUtils.getDoubleValue(colIndexes[19], row);
        double shipped_cost = ExcelUtils.getDoubleValue(colIndexes[20], row);
        Date shipped_date = ExcelUtils.getDateValue(colIndexes[21], row);
        String vehicle_number = ExcelUtils.getStringValue(colIndexes[22], row);
        int invoice_number = ExcelUtils.getIntValue(colIndexes[23], row);
        Date invoice_date = ExcelUtils.getDateValue(colIndexes[24], row);
        double final_price = ExcelUtils.getDoubleValue(colIndexes[25], row);
        double final_cost = ExcelUtils.getDoubleValue(colIndexes[26], row);

        return new SummaryRowEntity(supplier, mill, branch, sell_type, client, consignee, product_type, profile, grade,
                ral, issued, contract, spec, position, accept_month, year, accepted, price, accepted_cost, shipped,
                shipped_cost, shipped_date, vehicle_number, invoice_number, invoice_date, final_price, final_cost);
    }

}

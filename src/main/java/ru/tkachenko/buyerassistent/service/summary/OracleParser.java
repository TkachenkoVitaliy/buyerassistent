package ru.tkachenko.buyerassistent.service.summary;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.dto.OracleDTO;
import ru.tkachenko.buyerassistent.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class OracleParser {

    public void parse(Path oracleMmkPath) {
        try(FileInputStream fis = new FileInputStream(oracleMmkPath.toString());
            XSSFWorkbook wb = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = wb.getSheetAt(0);
            String[] oracleColumnsNames = OracleInfoUtil.getOracleColumnsNamesForDTO();
            int headerRowIndex = ExcelUtils.findFirstNotBlankRow(sheet);
            int firstRowIndex = headerRowIndex + 1;
            int lastRowIndex = sheet.getLastRowNum();
            int[] oracleColIndexes = ExcelUtils.getEntityColumns(sheet, headerRowIndex, oracleColumnsNames);

            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                Row currentRow = sheet.getRow(i);
                OracleDTO oracleDTO = parseToOracleDTO(oracleColIndexes, currentRow);
//                SummaryRowEntity entity = parseSummaryEntityFromRow(colIndexes, currentRow);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OracleDTO parseToOracleDTO(int[] colIndexes, Row row) {
        OracleDTO oracleDTO = new OracleDTO();
        oracleDTO.setMill(ExcelUtils.getStringValue(colIndexes[0], row));
        oracleDTO.setConsignee(ExcelUtils.getStringValue(colIndexes[1], row));
        oracleDTO.setProductType(ExcelUtils.getStringValue(colIndexes[2], row));
        oracleDTO.setProfile(ExcelUtils.getStringValue(colIndexes[3], row));
        oracleDTO.setGrade(ExcelUtils.getStringValue(colIndexes[4], row));
        oracleDTO.setRal(ExcelUtils.getStringValue(colIndexes[5], row));
        oracleDTO.setContract(ExcelUtils.getStringValue(colIndexes[6], row));
        oracleDTO.setSpec(ExcelUtils.getStringValue(colIndexes[7], row));
        oracleDTO.setPosition(ExcelUtils.getIntValue(colIndexes[8], row));
        oracleDTO.setAcceptMonth(ExcelUtils.getIntValue(colIndexes[9], row));
        oracleDTO.setAccepted(ExcelUtils.getDoubleValue(colIndexes[10], row));
        oracleDTO.setShipped(ExcelUtils.getDoubleValue(colIndexes[11], row));
        oracleDTO.setShippedDate(ExcelUtils.getDateValue(colIndexes[12], row));
        oracleDTO.setVehicleNumber(ExcelUtils.getStringValue(colIndexes[13], row));
        oracleDTO.setInvoiceNumber(ExcelUtils.getIntValue(colIndexes[14], row));
        oracleDTO.setInvoiceDate(ExcelUtils.getDateValue(colIndexes[15], row));

        oracleDTO.setPriceWithoutNDS(ExcelUtils.getDoubleValue(colIndexes[16], row));
        oracleDTO.setPrt(ExcelUtils.getStringValue(colIndexes[17], row));
        oracleDTO.setStation(ExcelUtils.getStringValue(colIndexes[18], row));
        oracleDTO.setThickness(ExcelUtils.getDoubleValue(colIndexes[19], row));
        oracleDTO.setWidth(ExcelUtils.getDoubleValue(colIndexes[20], row));
        oracleDTO.setLength(ExcelUtils.getDoubleValue(colIndexes[21], row));
        oracleDTO.setAdditionalRequirements(ExcelUtils.getStringValue(colIndexes[22], row));

        return oracleDTO;
    }

}

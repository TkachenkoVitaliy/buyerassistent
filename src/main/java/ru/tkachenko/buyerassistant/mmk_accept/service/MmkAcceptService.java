package ru.tkachenko.buyerassistant.mmk_accept.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.mmk_accept.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistant.mmk_accept.exception.AcceptParseException;
import ru.tkachenko.buyerassistant.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class MmkAcceptService {
    private final MmkAcceptDBService mmkAcceptDBService;

    private final String SPEC_COL_NAME = "Номер заказа";
    private final String POSITION_COL_NAME = "Номер строки";
    private final String NOMENCLATURE_COL_NAME = "Наименование позиции";
    private final String GRADE_COL_NAME = "Марка стали";
    private final String THICKNESS_COL_NAME = "Толщина от";
    private final String WIDTH_COL_NAME = "Ширина от";
    private final String LENGTH_COL_NAME = "Длина от";
    private final String ALTER_PROFILE_COL_NAME = "Альт. Профиль";
    private final String ACCEPTED_COL_NAME = "Заказанное количество";
    private final String ACCEPTED_MONTH_COL_NAME = "Отгрузить до (Месяц)";
    private final String ADDITIONAL_REQUIREMENTS_COL_NAME = "Доп.тех.требования";
    private final String[] columnsNames = {SPEC_COL_NAME, POSITION_COL_NAME, NOMENCLATURE_COL_NAME, GRADE_COL_NAME,
            THICKNESS_COL_NAME, WIDTH_COL_NAME, LENGTH_COL_NAME, ALTER_PROFILE_COL_NAME, ACCEPTED_COL_NAME,
            ACCEPTED_MONTH_COL_NAME, ADDITIONAL_REQUIREMENTS_COL_NAME};

    @Autowired
    public MmkAcceptService(MmkAcceptDBService mmkAcceptDBService) {
        this.mmkAcceptDBService = mmkAcceptDBService;
    }

    public void parseFileToDatabase(Path filePath) throws AcceptParseException {

        try (FileInputStream fileInputStream = new FileInputStream(filePath.toString());
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream))
        {
            Sheet sheet = workbook.getSheetAt(0);
            int headerRowIndex = ExcelUtils.findFirstNotBlankRow(sheet);
            int firstRowIndex = headerRowIndex + 1;
            int lastRowIndex = sheet.getLastRowNum();
            int[] colIndexes = ExcelUtils.getEntityColumnsIndexes(sheet, headerRowIndex, columnsNames);

            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                Row currentRow = sheet.getRow(i);
                MmkAcceptRowEntity entity = parseMmkAcceptEntityFromRow(colIndexes, currentRow);
                mmkAcceptDBService.addUniqueEntity(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MmkAcceptRowEntity parseMmkAcceptEntityFromRow(int[] colIndexes, Row row) {
        String spec = ExcelUtils.getStringValue(colIndexes[0], row);
        int position = ExcelUtils.getIntValue(colIndexes[1], row);
        String nomenclature = ExcelUtils.getStringValue(colIndexes[2], row);
        String grade = ExcelUtils.getStringValue(colIndexes[3], row);
        double thickness = ExcelUtils.getDoubleValue(colIndexes[4], row);
        double width = ExcelUtils.getDoubleValue(colIndexes[5], row);
        double length = ExcelUtils.getDoubleValue(colIndexes[6], row);
        String alterProfile = ExcelUtils.getStringValue(colIndexes[7], row);
        double accepted = ExcelUtils.getDoubleValue(colIndexes[8], row);
        int acceptMonth = ExcelUtils.getIntValue(colIndexes[9], row);
        String additionalRequirements = ExcelUtils.getStringValue(colIndexes[10], row);

        return new MmkAcceptRowEntity(spec, position, nomenclature, grade, thickness, width, length, alterProfile,
                accepted, acceptMonth, additionalRequirements);
    }
}

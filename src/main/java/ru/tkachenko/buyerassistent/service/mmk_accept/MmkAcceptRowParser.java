package ru.tkachenko.buyerassistent.service.mmk_accept;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistent.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class MmkAcceptRowParser {
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

    public MmkAcceptRowEntity parseRowEntity (Path filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath.toString());
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int headerRowIndex = ExcelUtils.findFirstNotBlankRow(sheet);
            XSSFRow header = sheet.getRow(headerRowIndex);
            int[] colIndexes = ExcelUtils.findColIndexesByValues(columnsNames, header);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        

        //TODO dont forget to return MmkAcceptRowEntity
        return null;
    }
}

package ru.tkachenko.buyerassistent.service.mmk_accept;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.entity.MmkAcceptRowEntity;
import ru.tkachenko.buyerassistent.repository.MmkAcceptRepository;
import ru.tkachenko.buyerassistent.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class MmkAcceptParser {
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
    public MmkAcceptParser(MmkAcceptDBService mmkAcceptDBService) {
        this.mmkAcceptDBService = mmkAcceptDBService;
    }

    public void parseFile (Path filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath.toString());
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int headerRowIndex = ExcelUtils.findFirstNotBlankRow(sheet);
            int[] colIndexes = getEntityColumns(sheet, headerRowIndex);

            int firstRowIndex = headerRowIndex + 1;
            int lastRowIndex = sheet.getLastRowNum();

            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                Row currentRow = sheet.getRow(i);
                MmkAcceptRowEntity entity = parseMmkAcceptEntityFromRow(colIndexes, currentRow);
                mmkAcceptDBService.addUniqueEntity(entity);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private int[] getEntityColumns(Sheet sheet, int headerRowIndex) {
        Row header = sheet.getRow(headerRowIndex);
        int[] colIndexes = ExcelUtils.findColIndexesByValues(columnsNames, header);
        return colIndexes;
    }

    private MmkAcceptRowEntity parseMmkAcceptEntityFromRow(int[] colIndexes, Row row) {
        Cell cell = null;

        //spec
        cell = row.getCell(colIndexes[0]);
        if(ExcelUtils.cellIsNullOrBlank(cell)) {
            return null;
        }
        String spec = ExcelUtils.getAnyValueAsString(cell);

        //position
        cell = row.getCell(colIndexes[1]);
        if(ExcelUtils.cellIsNullOrBlank(cell)) {
            return null;
        }
        int position = (int) cell.getNumericCellValue();
        //TODO ExcelUtils methods


        /*
        public int getPosition() {
            return position;
        }

        public String getNomenclature() {
            return nomenclature;
        }

        public String getGrade() {
            return grade;
        }

        public double getThickness() {
            return thickness;
        }

        public double getWidth() {
            return width;
        }

        public double getLength() {
            return length;
        }

        public String getAlterProfile() {
            return alterProfile;
        }

        public double getAccepted() {
            return accepted;
        }

        public int getAcceptMonth() {
            return acceptMonth;
        }

        public String getAdditionalRequirements() {
            return additionalRequirements;
        }
        */

        //TODO return MmkAcceptRowEntity
        return null;
    }
}

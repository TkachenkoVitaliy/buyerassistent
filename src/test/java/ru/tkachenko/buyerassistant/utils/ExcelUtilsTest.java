package ru.tkachenko.buyerassistant.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class ExcelUtilsTest {

    private static Workbook workbook = new XSSFWorkbook();

    private static Sheet sheet = workbook.createSheet();

    private static Row row0;
    private static Row row1;
    private static Row row2;
    private static Row row3;

    private static Row row4;
    private static Cell cell10;
    private static Cell cell11;
    private static Cell cell20;
    private static Cell cell21;
    private static Cell cell22;
    private static Cell cell23;

    private static Cell cell40;

    private static Cell cell41;

    private static Cell cell42;

    private static Cell cell44;
    private static CreationHelper creationHelper = workbook.getCreationHelper();
    private static CellStyle dateStyle = workbook.createCellStyle();


    @BeforeAll
    static void init() {
        row0 = sheet.createRow(0);
        row1 = sheet.createRow(1);

        cell10 = row1.createCell(0);
        cell11 = row1.createCell(1, CellType.BLANK);

        row2 = sheet.createRow(2);

        cell20 = row2.createCell(0);
        cell20.setCellValue("База");
        cell21 = row2.createCell(1);
        cell21.setCellValue("Дата отгрузки");
        cell22 = row2.createCell(2);
        cell22.setCellValue("Поставщик");
        cell23 = row2.createCell(3);
        cell23.setCellValue("Вид поставки");

        row3 = sheet.createRow(3);

        row4 = sheet.createRow(4);
        cell40 = row4.createCell(0);
        cell40.setCellValue("Текстовое");
        cell41 = row4.createCell(1);
        cell41.setCellValue(1.453);
        cell42 = row4.createCell(2);
        cell44 = row4.createCell(4);
        cell44.setCellValue("Текст с названием ООО \"CТАЛЬЭКС\"");

        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd.MM.yyyy"));
    }

    @Test
    void getEntityColumnsIndexes() {
        String[] inputArrayValues = {"Поставщик", "База", "Вид поставки"};

        int[] expectedArray = {2, 0, 3};

        int[] actualArray = ExcelUtils.getEntityColumnsIndexes(sheet, 2, inputArrayValues);

        Assertions.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    void testGetEntityColumnsIndexes() {
        String[] inputArrayValues = {"Поставщик", "База", "Вид поставки"};

        int[] expectedArray = {2, 0, 3};

        int[] actualArray = ExcelUtils.getEntityColumnsIndexes(inputArrayValues, row2);

        Assertions.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    void findColIndexByValue() {
        String inputValue = "Поставщик";
        Row inputRow = sheet.createRow(3);
        inputRow.createCell(0).setCellValue("База");
        inputRow.createCell(1).setCellValue("Дата отгрузки");
        inputRow.createCell(2).setCellValue("Поставщик");
        inputRow.createCell(3).setCellValue("Вид поставки");

        int expectedIndex = 2;

        int actualIndex = ExcelUtils.findColIndexByValue(inputValue, inputRow);

        Assertions.assertEquals(expectedIndex, actualIndex);
    }

    @Test
    void findFirstNotBlankRow() {
        int actualResult = ExcelUtils.findFirstNotBlankRow(sheet);
        int expectedResult = 2;
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void cellIsNullOrBlank() {
        boolean actualResult1 = ExcelUtils.cellIsNullOrBlank(cell20);
        boolean actualResult2 = ExcelUtils.cellIsNullOrBlank(cell10);
        boolean actualResult3 = ExcelUtils.cellIsNullOrBlank(cell11);

        Assertions.assertFalse(actualResult1);
        Assertions.assertTrue(actualResult2);
        Assertions.assertTrue(actualResult3);
    }

    @Test
    void getStringValueWithoutQuote() {
        String actualResult = ExcelUtils.getStringValueWithoutQuote(4, row4);
        String actualResult1 = ExcelUtils.getStringValue(0, row4);

        String expectedResult = "Текст с названием ООО CТАЛЬЭКС";
        String expectedResult1 = "Текстовое";
        
        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedResult1, actualResult1);
        Assertions.assertNull(ExcelUtils.getStringValue(5, row4));

    }

    @Test
    void getStringValue() {
        String actualTest1 = ExcelUtils.getStringValue(0, row4);
        String actualTest2 = ExcelUtils.getStringValue(1, row4);
        String actualTest3 = ExcelUtils.getStringValue(2, row4);

        String expectedResult1 = "Текстовое";
        String expectedResult2 = "1.453";
        String expectedResult3 = "";

        Assertions.assertEquals(expectedResult1, actualTest1);
        Assertions.assertEquals(expectedResult2, actualTest2);
        Assertions.assertEquals(expectedResult3, actualTest3);
        Assertions.assertNull(ExcelUtils.getStringValue(5, row4));
        Assertions.assertNull(ExcelUtils.getStringValue(5, sheet.getRow(5)));
    }

    @Test
    void getAnyValueAsString() {
        String actualTest1 = ExcelUtils.getAnyValueAsString(cell40);
        String actualTest2 = ExcelUtils.getAnyValueAsString(cell41);
        String actualTest3 = ExcelUtils.getAnyValueAsString(cell42);

        String expectedResult1 = "Текстовое";
        String expectedResult2 = "1.453";
        String expectedResult3 = "";

        Assertions.assertEquals(expectedResult1, actualTest1);
        Assertions.assertEquals(expectedResult2, actualTest2);
        Assertions.assertEquals(expectedResult3, actualTest3);
        Assertions.assertNull(ExcelUtils.getAnyValueAsString(row4.getCell(5)));
    }

    @Test
    void getDoubleValue() {
    }

    @Test
    void getIntValue() {
    }

    @Test
    void getDateValue() {
    }

    @Test
    void testGetDateValue() {
    }

    @Test
    void testWriteCellNotNullValueString() {
    }

    @Test
    void testWriteCellNotNullValueInt() {
        //test case 1 (positive)
        int inputColIndex = 5;
        int inputValue = 10;

        ExcelUtils.writeCellNotNullValue(row3, inputColIndex, inputValue);

        int expectedResult = 10;

        Cell actualCell = row3.getCell(5);
        double actualResult = actualCell.getNumericCellValue();

        //TODO как можно проверить что записалось именно int значение, возможно взять значение как строку и проверить его
        Assertions.assertEquals(expectedResult, actualResult);

        //test case 2 (negative)
        int inputColIndexNull = 4;
        int inputValueNull = 0;
        ExcelUtils.writeCellNotNullValue(row3, inputColIndexNull, inputValueNull);
        Assertions.assertNull(row3.getCell(4));
    }

    @Test
    void testWriteCellNotNullValueDouble() {
        //test case 1 (positive)
        int inputColIndex = 3;
        double inputValue = 145.584;

        ExcelUtils.writeCellNotNullValue(row3, inputColIndex, inputValue);

        double expectedResult = 145.584;

        Cell actualCell = row3.getCell(3);
        double actualResult = actualCell.getNumericCellValue();

        Assertions.assertEquals(expectedResult, actualResult);

        //test case 2 (negative)
        int inputColIndexNull = 2;
        double inputValueNull = 0.0;
        ExcelUtils.writeCellNotNullValue(row3, inputColIndexNull, inputValueNull);
        Assertions.assertNull(row3.getCell(2));
    }

    @Test
    void writeCellNotNullDateValue() {
        //test case 1 (positive)
        int inputColIndex = 1;
        java.sql.Date inputDate = new java.sql.Date(2022, 4, 29);
        ExcelUtils.writeCellNotNullDateValue(row3, inputColIndex, inputDate, dateStyle);

        java.util.Date exceptedDate = new java.util.Date(2022, 4, 29);

        Cell actualCell = row3.getCell(inputColIndex);
        java.util.Date actualResult = actualCell.getDateCellValue();

        Assertions.assertEquals(exceptedDate, actualResult);

        //test case 2 (negative)
        int inputColIndexNull = 0;

        ExcelUtils.writeCellNotNullDateValue(row3, inputColIndexNull, null, dateStyle);

        Assertions.assertNull(row3.getCell(inputColIndexNull));
    }

    @Test
    void setColumnWidthBranchFile() {

    }
}
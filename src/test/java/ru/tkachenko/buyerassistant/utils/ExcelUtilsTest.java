package ru.tkachenko.buyerassistant.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;


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
    private static Cell cell49;
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
        cell49 = row4.createCell(9);
        cell49.setCellValue(49);
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd.MM.yyyy"));
    }

    @Test
    @Disabled
    void getEntityColumnsIndexes() {
        String[] inputArrayValues = {"Поставщик", "База", "Вид поставки"};

        int[] expectedArray = {2, 0, 3};

        int[] actualArray = ExcelUtils.getEntityColumnsIndexes(sheet, 2, inputArrayValues);

        Assertions.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    @Disabled
    void testGetEntityColumnsIndexes() {
        String[] inputArrayValues = {"Поставщик", "База", "Вид поставки"};

        int[] expectedArray = {2, 0, 3};

        int[] actualArray = ExcelUtils.getEntityColumnsIndexes(inputArrayValues, row2);

        Assertions.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    @Disabled
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
    @Disabled
    void findFirstNotBlankRow() {
        int actualResult = ExcelUtils.findFirstNotBlankRow(sheet);
        int expectedResult = 2;
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @Disabled
    void cellIsNullOrBlank() {
        boolean actualResult1 = ExcelUtils.cellIsNullOrBlank(cell20);
        boolean actualResult2 = ExcelUtils.cellIsNullOrBlank(cell10);
        boolean actualResult3 = ExcelUtils.cellIsNullOrBlank(cell11);

        Assertions.assertFalse(actualResult1);
        Assertions.assertTrue(actualResult2);
        Assertions.assertTrue(actualResult3);
    }

    @Test
    @Disabled
    void getStringValueWithoutQuote() {
        String expectedResult = "Текст с названием ООО CТАЛЬЭКС";
        String expectedResult1 = "Текстовое";

        String actualResult = ExcelUtils.getStringValueWithoutQuote(4, row4);
        String actualResult1 = ExcelUtils.getStringValue(0, row4);

        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedResult1, actualResult1);
        Assertions.assertNull(ExcelUtils.getStringValue(5, row4));

    }

    @Test
    @Disabled
    void getStringValue() {
        int inputColIndex1 = 0;
        int inputColIndex2 = 1;
        int inputColIndex3 = 2;
        int inputColIndexNull = 5;

        String expectedResult1 = "Текстовое";
        String expectedResult2 = "1,453";
        String expectedResult3 = "";

        String actualTest1 = ExcelUtils.getStringValue(inputColIndex1, row4);
        String actualTest2 = ExcelUtils.getStringValue(inputColIndex2, row4);
        String actualTest3 = ExcelUtils.getStringValue(inputColIndex3, row4);

        Assertions.assertEquals(expectedResult1, actualTest1);
        Assertions.assertEquals(expectedResult2, actualTest2);
        Assertions.assertEquals(expectedResult3, actualTest3);
        Assertions.assertNull(ExcelUtils.getStringValue(inputColIndexNull, row4));
        Assertions.assertNull(ExcelUtils.getStringValue(inputColIndexNull, sheet.getRow(5)));
    }

    @Test
    @Disabled
    void getAnyValueAsString() {
        String expectedResult1 = "Текстовое";
        String expectedResult2 = "1,453";
        String expectedResult3 = "";

        String actualTest1 = ExcelUtils.getAnyValueAsString(cell40);
        String actualTest2 = ExcelUtils.getAnyValueAsString(cell41);
        String actualTest3 = ExcelUtils.getAnyValueAsString(cell42);

        Assertions.assertEquals(expectedResult1, actualTest1);
        Assertions.assertEquals(expectedResult2, actualTest2);
        Assertions.assertEquals(expectedResult3, actualTest3);
        Assertions.assertNull(ExcelUtils.getAnyValueAsString(row4.getCell(5)));
    }

    @Test
    @Disabled
    void getDoubleValue() {
        int inputColIndex1 = 1;
        int inputColIndex2 = 2;
        int inputColIndex3 = 10;

        double expectedResult1 = 1.453;
        double expectedResultZero = 0.0;

        double actualResult1 = ExcelUtils.getDoubleValue(inputColIndex1, row4);
        double actualResult2 = ExcelUtils.getDoubleValue(inputColIndex2, row4);
        double actualResult3 = ExcelUtils.getDoubleValue(inputColIndex3, row4);

        Assertions.assertEquals(expectedResult1, actualResult1);
        Assertions.assertEquals(expectedResultZero, actualResult2);
        Assertions.assertEquals(expectedResultZero, actualResult3);
    }

    @Test
    @Disabled
    void getIntValue() {
        int inputColIndex1 = 9;
        int inputColIndex2 = 2;
        int inputColIndex3 = 10;

        int expectedResult1 = 49;
        int expectedResultZero = 0;

        int actualResult1 = ExcelUtils.getIntValue(inputColIndex1, row4);
        int actualResult2 = ExcelUtils.getIntValue(inputColIndex2, row4);
        int actualResult3 = ExcelUtils.getIntValue(inputColIndex3, row4);

        Assertions.assertEquals(expectedResult1, actualResult1);
        Assertions.assertEquals(expectedResultZero, actualResult2);
        Assertions.assertEquals(expectedResultZero, actualResult3);
    }

    @Test
    @Disabled
    void getDateValue() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String inputDate = "01.04.1992";
        Cell cellWithDate = row4.createCell(8);
        cellWithDate.setCellValue(inputDate);

        java.sql.Date expectedDate = java.sql.Date.valueOf("1992-04-01");

        java.sql.Date actualDate = ExcelUtils.getDateValue(8, row4, dateFormat);

        Assertions.assertEquals(expectedDate, actualDate);
    }

    @Test
    @Disabled
    void writeCellNotNullValueString() {
        //TODO возможно стоить переделать метод чтобы не записывал пустые строки - "", а может и нет; нужно обдумать
        /*int inputColIndex = 7;
        String inputValue = "ММК";

        ExcelUtils.writeCellNotNullValue(row3, 7, inputValue);

        String expectedResult = "ММК";

        Cell actualCell = row3.getCell(7);
        String actualResult = actualCell.getStringCellValue();

        Assertions.assertEquals(expectedResult, actualResult);

        int inputColIndexNull = 6;
        String inputValueNull = null;
        ExcelUtils.writeCellNotNullValue(row3, inputColIndexNull, inputValueNull);
        Assertions.assertNull(row3.getCell(6));*/

    }

    @Test
    @Disabled
    void writeCellNotNullValueInt() {
        /*int inputColIndex = 5;
        int inputValue = 10;

        ExcelUtils.writeCellNotNullValue(row3, inputColIndex, inputValue);

        int expectedResult = 10;

        Cell actualCell = row3.getCell(5);
        double actualResult = actualCell.getNumericCellValue();

        //TODO как можно проверить что записалось именно int значение, возможно взять значение как строку и проверить его
        Assertions.assertEquals(expectedResult, actualResult);

        int inputColIndexNull = 4;
        int inputValueNull = 0;
        ExcelUtils.writeCellNotNullValue(row3, inputColIndexNull, inputValueNull);
        Assertions.assertNull(row3.getCell(4));*/
    }

    @Test
    @Disabled
    void writeCellNotNullValueDouble() {
        /*int inputColIndex = 3;
        double inputValue = 145.584;

        ExcelUtils.writeCellNotNullValue(row3, inputColIndex, inputValue);

        double expectedResult = 145.584;

        Cell actualCell = row3.getCell(3);
        double actualResult = actualCell.getNumericCellValue();

        Assertions.assertEquals(expectedResult, actualResult);

        int inputColIndexNull = 2;
        double inputValueNull = 0.0;
        ExcelUtils.writeCellNotNullValue(row3, inputColIndexNull, inputValueNull);
        Assertions.assertNull(row3.getCell(2));*/
    }

    @Test
    @Disabled
    void writeCellNotNullDateValue() {
        /*int inputColIndex = 1;
        java.sql.Date inputDate = new java.sql.Date(2022, 4, 29);
        ExcelUtils.writeCellNotNullDateValue(row3, inputColIndex, inputDate, dateStyle);

        java.util.Date exceptedDate = new java.util.Date(2022, 4, 29);

        Cell actualCell = row3.getCell(inputColIndex);
        java.util.Date actualResult = actualCell.getDateCellValue();

        Assertions.assertEquals(exceptedDate, actualResult);

        int inputColIndexNull = 0;

        ExcelUtils.writeCellNotNullDateValue(row3, inputColIndexNull, null, dateStyle);

        Assertions.assertNull(row3.getCell(inputColIndexNull));*/
    }

    @Test
    @Disabled
    void setColumnWidthBranchFile() {
        int[] expectedResults = {24*256, 7*256, 13*256, 26*256, 29*256, 20*256, 13*256, 10*256, 13*256, 9*256, 9*256,
                11*256, 15*256, 14*256, 18*256};

        ExcelUtils.setColumnWidthBranchFile(sheet);
        int[] actualResults = new int[15];
        for (int i = 0; i <= 14; i++) {
            int width = sheet.getColumnWidth(i);
            actualResults[i] = width;
        }

        Assertions.assertArrayEquals(expectedResults, actualResults);
    }
}
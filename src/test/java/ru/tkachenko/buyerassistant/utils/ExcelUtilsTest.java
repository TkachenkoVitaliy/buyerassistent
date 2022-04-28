package ru.tkachenko.buyerassistant.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcelUtilsTest {

    private static Workbook workbook = new XSSFWorkbook();

    private static Sheet sheet = workbook.createSheet();

    private static Row row0;
    private static Row row1;
    private static Row row2;
    private static Cell cell10;
    private static Cell cell11;
    private static Cell cell20;
    private static Cell cell21;
    private static Cell cell22;
    private static Cell cell23;

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
    }

    @Test
    void cellIsNullOrBlank() {
    }

    @Test
    void getStringValueWithoutQuote() {
    }

    @Test
    void getStringValue() {
    }

    @Test
    void getAnyValueAsString() {
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
    void writeCellNotNullValue() {
    }

    @Test
    void testWriteCellNotNullValue() {
    }

    @Test
    void testWriteCellNotNullValue1() {
    }

    @Test
    void writeCellNotNullDateValue() {
    }
}
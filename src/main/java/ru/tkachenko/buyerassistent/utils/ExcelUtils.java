package ru.tkachenko.buyerassistent.utils;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {

    private final static DataFormatter dataFormatter = new DataFormatter();

    public static int findColIndexByValue (String value, Row row) {
        int result = -1;
        for (Cell cell: row) {
            if (cell.getCellType() == CellType.STRING) {
                if (value.equals(cell.getStringCellValue())) {
                    result = cell.getColumnIndex();
                    return result;
                }
            }
        }

        return result;
    }

    public static int[] findColIndexesByValues(String[] arrayValues, Row row) {
        int[] resultArray = new int[arrayValues.length];
        for (int i = 0; i < arrayValues.length; i++) {
            String value = arrayValues[i];
            resultArray[i] = findColIndexByValue(value, row);
        }
        return resultArray;
    }

    public static int findFirstNotBlankRow (Sheet sheet) {
        int result = -1;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if(cell.getCellType() != CellType.BLANK || cell.getCellType() != CellType._NONE) {
                    result = cell.getRowIndex();
                    return result;
                }
            }
        }
        return result;
    }

    public static String getAnyValueAsString(Cell cell) {
        if(cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else {
            String stringValue = dataFormatter.formatCellValue(cell);
            return stringValue;
        }
    }

    public static boolean cellIsNullOrBlank(Cell cell) {
        if(cell == null && cell.getCellType() == CellType.BLANK && cell.getCellType() == CellType._NONE) {
            return true;
        }
        return false;
    }

    public static String getStringValue(int colIndex, Row row) {
        Cell cell = row.getCell(colIndex);
        return getAnyValueAsString(cell);
    }

    public static double getDoubleValue(int colIndex, Row row) {
        Cell cell = row.getCell(colIndex);
        return cell.getNumericCellValue();
    }

    public static int getIntValue(int colIndex, Row row) {
        Cell cell = row.getCell(colIndex);
        return (int) cell.getNumericCellValue();
    }
}

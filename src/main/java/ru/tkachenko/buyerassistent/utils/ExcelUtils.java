package ru.tkachenko.buyerassistent.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelUtils {

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
}

package ru.tkachenko.buyerassistent.utils;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {

    private final static DataFormatter dataFormatter = new DataFormatter();

    public static int findColIndexByValue(String value, Row row) {
        int result = -1;
        for (Cell cell : row) {
            if (cell.getCellType() == CellType.STRING) {
                if (value.equals(cell.getStringCellValue())) {
                    result = cell.getColumnIndex();
                    return result;
                }
            }
        }

        return result;
    }

    public static int[] getEntityColumnsIndexes(String[] arrayValues, Row row) {

        int[] resultArray = new int[arrayValues.length];
        for (int i = 0; i < arrayValues.length; i++) {
            String value = arrayValues[i];
            resultArray[i] = findColIndexByValue(value, row);
        }
        return resultArray;
    }

    public static int[] getEntityColumnsIndexes(Sheet sheet, int headerRowIndex, String[] entityColumnNames) {
        Row header = sheet.getRow(headerRowIndex);
        int[] colIndexes = ExcelUtils.getEntityColumnsIndexes(entityColumnNames, header);
        return colIndexes;
    }

    public static int findFirstNotBlankRow(Sheet sheet) {
        int result = -1;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (!cellIsNullOrBlank(cell)) {
                    result = cell.getRowIndex();
                    return result;
                }
            }
        }
        return result;
    }

    public static boolean cellIsNullOrBlank(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK || cell.getCellType() == CellType._NONE) {
            return true;
        }
        return false;
    }

    public static String getAnyValueAsString(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else {
            String stringValue = dataFormatter.formatCellValue(cell);
            return stringValue;
        }
    }

    public static String getStringValue(int colIndex, Row row) {
        Cell cell = row.getCell(colIndex);
        return getAnyValueAsString(cell);
    }

    public static String getStringValueWithoutQuote(int colIndex, Row row) {
        Cell cell = row.getCell(colIndex);
        return getAnyValueAsString(cell).replace("\"", "");
    }

    public static double getDoubleValue(int colIndex, Row row) {
        Cell cell = row.getCell(colIndex);
        if(cell == null || cell.getCellType() == CellType.BLANK) {
            return 0.0;
        }
        return cell.getNumericCellValue();
    }

    public static int getIntValue(int colIndex, Row row) {
        Cell cell = row.getCell(colIndex);
        if(cell == null || cell.getCellType() == CellType.BLANK) {
            return 0;
        }
        return (int) cell.getNumericCellValue();
    }

    public static java.sql.Date getDateValue(int colIndex, Row row) {
        Cell cell = row.getCell(colIndex);
        java.sql.Date sqlDate = null;
        if(cell != null && cell.getCellType()!= CellType.BLANK) {
            if(cell.getCellType() == CellType.STRING) {
                String dateString = cell.getStringCellValue();
                sqlDate = java.sql.Date.valueOf(dateString);
            } else {
                java.util.Date javaDate = cell.getDateCellValue();
                sqlDate = new java.sql.Date(javaDate.getTime());
            }
        }
        return sqlDate;
    }

}

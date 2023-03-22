package ru.tkachenko.buyerassistant.utils;

import org.apache.poi.ss.usermodel.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExcelUtils {

    private final static DataFormatter dataFormatter = new DataFormatter();

    public static int[] getEntityColumnsIndexes(Sheet sheet, int headerRowIndex, String[] entityColumnNames) {
        Row header = sheet.getRow(headerRowIndex);
        return ExcelUtils.getEntityColumnsIndexes(entityColumnNames, header);
    }

    public static int[] getEntityColumnsIndexes(String[] arrayValues, Row row) {
        int[] resultArray = new int[arrayValues.length];
        for (int i = 0; i < arrayValues.length; i++) {
            String value = arrayValues[i];
            resultArray[i] = findColIndexByValue(value, row);
        }
        return resultArray;
    }

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

    public static String getStringValueWithoutQuote(int colIndex, Row row) {
        return getStringValue(colIndex, row).replace("\"", "");
    }

    public static String getStringValue(int colIndex, Row row) {
        if(row == null) return null;
        Cell cell = row.getCell(colIndex);
        if(cell == null) return null;
        return getAnyValueAsString(cell).trim();
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

    public static java.sql.Date getDateValue(int colIndex, Row row, SimpleDateFormat dateFormat) {
        Cell cell = row.getCell(colIndex);
        java.sql.Date sqlDate = null;
        if(cell != null && cell.getCellType()!= CellType.BLANK) {
            if(cell.getCellType() == CellType.STRING) {
                String dateString = cell.getStringCellValue();
                try {
                    sqlDate = new Date(dateFormat.parse(dateString).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                java.util.Date javaDate = cell.getDateCellValue();
                sqlDate = new java.sql.Date(javaDate.getTime());
            }
        }
        return sqlDate;
    }

    public static void writeCellNotNullValue(Row row, int columnIndex, String value, CellStyleContainer cellStyles) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyles.getStringAndIntCellStyle());
        if(value != null) {
            cell.setCellValue(value);
        }
    }

    public static void writeCellNotNullValue(Row row, int columnIndex, int value, CellStyleContainer cellStyles) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyles.getStringAndIntCellStyle());
        if(value != 0) {
            cell.setCellValue(value);
        }
    }

    public static void writeCellNotNullValue(Row row, int columnIndex, double value, CellStyleContainer cellStyles) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyles.getDoubleCellStyle());
        if(value != 0.0) {
            cell.setCellValue(value);
        }
    }

    public static void writeCellNotNullDateValue(Row row, int columnIndex, Date dateValue, CellStyleContainer cellStyles) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyles.getDateCellStyle());
        if(dateValue != null) {
            cell.setCellValue(dateValue);
        }
    }

    public static void setColumnWidthBranchFile(Sheet sheet) {
        sheet.setColumnWidth(0, 24*256);//поставщик
        sheet.setColumnWidth(1, 7*256);//стан
        sheet.setColumnWidth(2, 9*256);//вид поставки
        sheet.setColumnWidth(3, 26*256);//грузополучатель
        sheet.setColumnWidth(4, 19*256);//вид продукции
        sheet.setColumnWidth(5, 19*256);//размеры/профиль
        sheet.setColumnWidth(6, 11*256);//марка/класс
        sheet.setColumnWidth(7, 10*256);//класс покрытия/ral
        sheet.setColumnWidth(8, 17*256);//доп. требования
        sheet.setColumnWidth(9, 13*256);//номер спец
        sheet.setColumnWidth(10, 9*256);//номер позиции
        sheet.setColumnWidth(11, 9*256);//месяц акцепта
        sheet.setColumnWidth(12, 11*256);//акцепт, тн
        sheet.setColumnWidth(13, 15*256);//отгруженно, тн
        sheet.setColumnWidth(14, 14*256);//дата отгрузки
        sheet.setColumnWidth(15, 15*256);//номер тс/вагона
    }
}

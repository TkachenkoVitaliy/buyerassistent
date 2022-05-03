package ru.tkachenko.buyerassistant.utils;

import org.apache.poi.ss.usermodel.*;

public class CellStyleContainer {

    private final CellStyle headerCellStyle;
    private final CellStyle stringAndIntCellStyle;
    private final CellStyle doubleCellStyle;
    private final CellStyle dateCellStyle;
    private final CellStyle simpleDateCellStyle;

    public CellStyleContainer(Workbook wb) {
        Font headerFont = wb.createFont();
        headerFont.setFontName("Calibri");
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        headerCellStyle = wb.createCellStyle();
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerCellStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font defaultFont = wb.createFont();
        defaultFont.setFontName("Calibri");
        headerFont.setFontHeightInPoints((short) 11);

        stringAndIntCellStyle = wb.createCellStyle();
        stringAndIntCellStyle.setBorderLeft(BorderStyle.THIN);
        stringAndIntCellStyle.setBorderTop(BorderStyle.THIN);
        stringAndIntCellStyle.setBorderRight(BorderStyle.THIN);
        stringAndIntCellStyle.setBorderBottom(BorderStyle.THIN);
        stringAndIntCellStyle.setFont(defaultFont);

        doubleCellStyle = wb.createCellStyle();
        DataFormat dataFormat = wb.createDataFormat();
        doubleCellStyle.setDataFormat(dataFormat.getFormat("#,##0.00"));
        doubleCellStyle.setBorderLeft(BorderStyle.THIN);
        doubleCellStyle.setBorderTop(BorderStyle.THIN);
        doubleCellStyle.setBorderRight(BorderStyle.THIN);
        doubleCellStyle.setBorderBottom(BorderStyle.THIN);
        doubleCellStyle.setFont(defaultFont);

        CreationHelper createHelper = wb.getCreationHelper();
        short dateFormat = createHelper.createDataFormat().getFormat("dd.MM.yyyy");

        dateCellStyle = wb.createCellStyle();
        dateCellStyle.setBorderLeft(BorderStyle.THIN);
        dateCellStyle.setBorderTop(BorderStyle.THIN);
        dateCellStyle.setBorderRight(BorderStyle.THIN);
        dateCellStyle.setBorderBottom(BorderStyle.THIN);
        dateCellStyle.setFont(defaultFont);
        dateCellStyle.setDataFormat(dateFormat);

        simpleDateCellStyle = wb.createCellStyle();
        simpleDateCellStyle.setDataFormat(dateFormat);
    }

    public CellStyle getHeaderCellStyle() {
        return headerCellStyle;
    }

    public CellStyle getStringAndIntCellStyle() {
        return stringAndIntCellStyle;
    }

    public CellStyle getDoubleCellStyle() {
        return doubleCellStyle;
    }

    public CellStyle getDateCellStyle() {
        return dateCellStyle;
    }

    public CellStyle getSimpleDateCellStyle() {
        return simpleDateCellStyle;
    }

}

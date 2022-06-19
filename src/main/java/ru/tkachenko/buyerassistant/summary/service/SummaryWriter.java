package ru.tkachenko.buyerassistant.summary.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.utils.CellStyleContainer;
import ru.tkachenko.buyerassistant.utils.ExcelUtils;

public class SummaryWriter {

    public static void writeHeader(Row headerRow, boolean isFullCopy, CellStyleContainer cellStyles) {
        if (isFullCopy) {
            writeFullHeader(headerRow);
        } else {
            writeClippedHeader(headerRow, cellStyles);
        }
    }

    private static void writeFullHeader(Row headerRow) {
        String[] headerColumnNames = SummaryInfoUtil.getFileColumnsNamesForEntity();
        for(int i = 0; i < headerColumnNames.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerColumnNames[i]);
        }
    }

    private static void writeClippedHeader(Row headerRow, CellStyleContainer cellStyles) {
        CellStyle headerCellStyle = cellStyles.getHeaderCellStyle();
        String[] headerColumnNames = SummaryInfoUtil.getClippedFileColumnsNames();
        for (int i = 0; i < headerColumnNames.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerColumnNames[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    public static void writeEntityToRow(CellStyleContainer cellStyles, SummaryRowEntity summaryRowEntity, Row row,
                                        boolean isFullCopy) {
        if (isFullCopy) {
            writeFullEntityToRow(cellStyles, summaryRowEntity, row);
        } else {
            writeClippedEntityToRow(cellStyles, summaryRowEntity, row);
        }
    }


    private static void writeFullEntityToRow(CellStyleContainer cellStyles, SummaryRowEntity summaryRowEntity,
                                             Row row) {
        CellStyle dateStyle = cellStyles.getSimpleDateCellStyle();

        row.createCell(0).setCellValue(summaryRowEntity.getSupplier());
        row.createCell(1).setCellValue(summaryRowEntity.getMill());
        row.createCell(2).setCellValue(summaryRowEntity.getBranch());
        row.createCell(3).setCellValue(summaryRowEntity.getSellType());
        row.createCell(4).setCellValue(summaryRowEntity.getClient());
        row.createCell(5).setCellValue(summaryRowEntity.getConsignee());
        row.createCell(6).setCellValue(summaryRowEntity.getProductType());
        row.createCell(7).setCellValue(summaryRowEntity.getProfile());
        row.createCell(8).setCellValue(summaryRowEntity.getGrade());
        row.createCell(9).setCellValue(summaryRowEntity.getRal());
        row.createCell(10).setCellValue(summaryRowEntity.getIssued());
        row.createCell(11).setCellValue(summaryRowEntity.getContract());
        row.createCell(12).setCellValue(summaryRowEntity.getSpec());
        row.createCell(13).setCellValue(summaryRowEntity.getPosition());
        row.createCell(14).setCellValue(summaryRowEntity.getAcceptMonth());
        row.createCell(15).setCellValue(summaryRowEntity.getYear());
        row.createCell(16).setCellValue(summaryRowEntity.getAccepted());
        row.createCell(17).setCellValue(summaryRowEntity.getPrice());
        row.createCell(18).setCellValue(summaryRowEntity.getAcceptedCost());
        row.createCell(19).setCellValue(summaryRowEntity.getShipped());
        row.createCell(20).setCellValue(summaryRowEntity.getShippedCost());
        Cell shippedDateCell = row.createCell(21);
        shippedDateCell.setCellValue(summaryRowEntity.getShippedDate());
        shippedDateCell.setCellStyle(dateStyle);
        row.createCell(22).setCellValue(summaryRowEntity.getVehicleNumber());
        row.createCell(23).setCellValue(summaryRowEntity.getInvoiceNumber());
        Cell invoiceDateCell = row.createCell(24);
        invoiceDateCell.setCellValue(summaryRowEntity.getInvoiceDate());
        invoiceDateCell.setCellStyle(dateStyle);
        row.createCell(25).setCellValue(summaryRowEntity.getFinalPrice());
        row.createCell(26).setCellValue(summaryRowEntity.getFinalCost());
        row.createCell(27).setCellValue(summaryRowEntity.getAdditionalReq());
    }

    private static void writeClippedEntityToRow(CellStyleContainer cellStyles, SummaryRowEntity summaryRowEntity,
                                                Row row) {

        ExcelUtils.writeCellNotNullValue(row, 0, summaryRowEntity.getSupplier(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 1, summaryRowEntity.getMill(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 2,summaryRowEntity.getSellType(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 3,summaryRowEntity.getConsignee(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 4,summaryRowEntity.getProductType(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 5,summaryRowEntity.getProfile(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 6,summaryRowEntity.getGrade(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 7,summaryRowEntity.getRal(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 8, summaryRowEntity.getAdditionalReq(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 9,summaryRowEntity.getSpec(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 10,summaryRowEntity.getPosition(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 11,summaryRowEntity.getAcceptMonth(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 12,summaryRowEntity.getAccepted(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 13,summaryRowEntity.getShipped(), cellStyles);
        ExcelUtils.writeCellNotNullDateValue(row, 14,summaryRowEntity.getShippedDate(), cellStyles);
        ExcelUtils.writeCellNotNullValue(row, 15,summaryRowEntity.getVehicleNumber(), cellStyles);
    }
}

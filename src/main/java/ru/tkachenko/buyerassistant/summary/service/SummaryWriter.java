package ru.tkachenko.buyerassistant.summary.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.utils.ExcelUtils;

public class SummaryWriter {

    public static void writeHeader(Row headerRow, boolean isFullCopy) {
        if (isFullCopy) {
            writeFullHeader(headerRow);
        } else {
            writeClippedHeader(headerRow);
        }
    }

    private static void writeFullHeader(Row headerRow) {
        String[] headerColumnNames = SummaryInfoUtil.getFileColumnsNamesForEntity();
        for(int i = 0; i < headerColumnNames.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerColumnNames[i]);
        }
    }

    private static void writeClippedHeader(Row headerRow) {
        String[] headerColumnNames = SummaryInfoUtil.getClippedFileColumnsNames();
        for (int i = 0; i < headerColumnNames.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerColumnNames[i]);
        }
    }

    public static void writeEntityToRow(XSSFCellStyle dateStyle, SummaryRowEntity summaryRowEntity, Row row, boolean isFullCopy) {
        if (isFullCopy) {
            writeFullEntityToRow(dateStyle, summaryRowEntity, row);
        } else {
            writeClippedEntityToRow(dateStyle, summaryRowEntity, row);
        }
    }


    private static void writeFullEntityToRow(XSSFCellStyle dateStyle, SummaryRowEntity summaryRowEntity, Row row) {
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
    }

    private static void writeClippedEntityToRow(XSSFCellStyle dateStyle, SummaryRowEntity summaryRowEntity, Row row) {
        ExcelUtils.writeCellNotNullValue(row, 0, summaryRowEntity.getSupplier());
        ExcelUtils.writeCellNotNullValue(row, 1, summaryRowEntity.getMill());
        ExcelUtils.writeCellNotNullValue(row, 2,summaryRowEntity.getSellType());
        ExcelUtils.writeCellNotNullValue(row, 3,summaryRowEntity.getClient());
        ExcelUtils.writeCellNotNullValue(row, 4,summaryRowEntity.getConsignee());
        ExcelUtils.writeCellNotNullValue(row, 5,summaryRowEntity.getProductType());
        ExcelUtils.writeCellNotNullValue(row, 6,summaryRowEntity.getProfile());
        ExcelUtils.writeCellNotNullValue(row, 7,summaryRowEntity.getGrade());
        ExcelUtils.writeCellNotNullValue(row, 8,summaryRowEntity.getRal());
        ExcelUtils.writeCellNotNullValue(row, 9,summaryRowEntity.getSpec());
        ExcelUtils.writeCellNotNullValue(row, 10,summaryRowEntity.getPosition());
        ExcelUtils.writeCellNotNullValue(row, 11,summaryRowEntity.getAcceptMonth());
        ExcelUtils.writeCellNotNullValue(row, 12,summaryRowEntity.getYear());
        ExcelUtils.writeCellNotNullValue(row, 13,summaryRowEntity.getAccepted());
        ExcelUtils.writeCellNotNullValue(row, 14,summaryRowEntity.getShipped());
        ExcelUtils.writeCellNotNullDateValue(row, 15,summaryRowEntity.getShippedDate(), dateStyle);
        ExcelUtils.writeCellNotNullValue(row, 16,summaryRowEntity.getVehicleNumber());
    }
}

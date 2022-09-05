package ru.tkachenko.buyerassistant.letter_of_authorization.service;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.letter_of_authorization.entity.*;
import ru.tkachenko.buyerassistant.property.FileStorageProperties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;

@Service
public class LetterOfAuthorizationCreator {

    private final Path LOA_DIRECTORY;
    private final String templateName = "template.xls";
    private final String createdLoa = "Доверенность.xls";
    private final String createdLoadPdf = "Доверенность.pdf";

    public LetterOfAuthorizationCreator(FileStorageProperties fileStorageProperties) {
        this.LOA_DIRECTORY = Paths.get(fileStorageProperties.getLoaDir()).toAbsolutePath().normalize();
    }

    public Path createNewLoa(LetterOfAuthorization letterOfAuthorization) throws IOException {
        Path loaPath = createNewBlankLoaFile();
        FileInputStream fis = new FileInputStream(loaPath.toString());
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);

        Principal principal = letterOfAuthorization.getPrincipal();
        Driver driver = letterOfAuthorization.getDriver();
        Supplier supplier = letterOfAuthorization.getSupplier();
        List<LetterRow> letterRows = letterOfAuthorization.getLetterRows();

        String principalInfo = principal.getName() + ", ИНН " + principal.getInn() + ", КПП " + principal.getKpp()
                + ", " + principal.getAddress();


        sheet.getRow(4).getCell(4).setCellValue(principalInfo);
        sheet.getRow(4).getCell(20).setCellValue(principal.getOkpo());

        sheet.getRow(7).getCell(9).setCellValue(letterOfAuthorization.getNumber());
        sheet.getRow(9).getCell(5).setCellValue(letterOfAuthorization.getIssuedDate());
        sheet.getRow(10).getCell(5).setCellValue(letterOfAuthorization.getValidUntil());
        sheet.getRow(12).getCell(1).setCellValue(principalInfo);
        sheet.getRow(15).getCell(1).setCellValue(principalInfo);

        sheet.getRow(18).getCell(4).setCellValue(principal.getBankAccount());

        sheet.getRow(21).getCell(13).setCellValue(driver.getName());
        sheet.getRow(23).getCell(4).setCellValue(driver.getPassportSeries());
        sheet.getRow(23).getCell(9).setCellValue(driver.getPassportNumber());
        sheet.getRow(24).getCell(4).setCellValue(driver.getIssuedBy());
        sheet.getRow(25).getCell(4).setCellValue(driver.getDateOfIssue());

        sheet.getRow(27).getCell(4).setCellValue(supplier.getSupplierName());

        sheet.getRow(51).getCell(7).setCellValue(principal.getDirectorName());
        sheet.getRow(55).getCell(7).setCellValue(principal.getDirectorName());



        letterRows.stream()
                .sorted(Comparator.comparingInt(LetterRow::getNumber))
                .forEach(letterRow -> writeLetterRow(sheet, letterRow));

        fis.close();
        FileOutputStream fos = new FileOutputStream(loaPath.toString());
        wb.write(fos);
        wb.close();
        fos.flush();
        fos.close();

        return loaPath;
    }

    private void writeLetterRow(HSSFSheet sheet,LetterRow letterRow) {
        int rowIndex = 36 + letterRow.getNumber();
        HSSFRow row = sheet.getRow(rowIndex);
        HSSFCellStyle cellStyleNumber = sheet.getRow(37).getCell(1).getCellStyle();
        HSSFCellStyle cellStyleNomenclature = sheet.getRow(37).getCell(2).getCellStyle();
        HSSFCellStyle cellStyleT = sheet.getRow(37).getCell(14).getCellStyle();
        HSSFCellStyle cellStyleTonnage = sheet.getRow(37).getCell(16).getCellStyle();

        if(row == null) row = sheet.createRow(36 + letterRow.getNumber());
        if(rowIndex > 37) {
            HSSFCell numberCell = getCurrentCell(row, 1);
            numberCell.setCellStyle(cellStyleNumber);
            numberCell.setCellValue(letterRow.getNumber());

            CellRangeAddress nomenclatureAddresses = new CellRangeAddress(rowIndex, rowIndex, 2, 13);
            sheet.addMergedRegion(nomenclatureAddresses);
            RegionUtil.setBorderBottom(BorderStyle.THIN, nomenclatureAddresses, sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, nomenclatureAddresses, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, nomenclatureAddresses, sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN, nomenclatureAddresses, sheet);
            HSSFCell nomenclatureCell = getCurrentCell(row, 2);
            nomenclatureCell.setCellStyle(cellStyleNomenclature);
            nomenclatureCell.setCellValue(letterRow.getNomenclature().getName());

            CellRangeAddress tAddresses = new CellRangeAddress(rowIndex, rowIndex, 14, 15);
            sheet.addMergedRegion(tAddresses);
            RegionUtil.setBorderBottom(BorderStyle.THIN, tAddresses, sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, tAddresses, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, tAddresses, sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN, tAddresses, sheet);
            HSSFCell tCell = getCurrentCell(row, 14);
            tCell.setCellStyle(cellStyleT);
            tCell.setCellValue("т");

            CellRangeAddress tonnageAddresses = new CellRangeAddress(rowIndex, rowIndex, 16, 21);
            sheet.addMergedRegion(tonnageAddresses);
            RegionUtil.setBorderBottom(BorderStyle.THIN, tonnageAddresses, sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, tonnageAddresses, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, tonnageAddresses, sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN, tonnageAddresses, sheet);
            HSSFCell tonnageCell = getCurrentCell(row, 16);
            tonnageCell.setCellStyle(cellStyleTonnage);
            tonnageCell.setCellValue(letterRow.getTonnage().doubleValue());
        } else {
            getCurrentCell(row, 1).setCellValue(letterRow.getNumber());
            getCurrentCell(row, 2).setCellValue(letterRow.getNomenclature().getName());
            getCurrentCell(row, 14).setCellValue("т");
            getCurrentCell(row, 16).setCellValue(letterRow.getTonnage().doubleValue());
        }
    }

    private HSSFCell getCurrentCell(HSSFRow row, int column) {
        return row.getCell(column) == null ? row.createCell(column) : row.getCell(column);
    }

    private Path createNewBlankLoaFile() throws IOException {
        Path templatePath = LOA_DIRECTORY.resolve(templateName);
        Path createdLoaPath = LOA_DIRECTORY.resolve(createdLoa);
        Path createdLoaPdfPath = LOA_DIRECTORY.resolve(createdLoadPdf);

        Files.createDirectories(LOA_DIRECTORY);
        Files.copy(templatePath, createdLoaPath, StandardCopyOption.REPLACE_EXISTING);
        return createdLoaPath;
    }
}

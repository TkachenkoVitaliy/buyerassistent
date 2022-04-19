package ru.tkachenko.buyerassistent.summary.service;

import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.file_storage.entity.SavedFileEntity;
import ru.tkachenko.buyerassistent.file_storage.service.FileStorageDBService;
import ru.tkachenko.buyerassistent.property.FileStorageProperties;
import ru.tkachenko.buyerassistent.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistent.summary.oracle_inner.service.OracleParser;
import ru.tkachenko.buyerassistent.summary.other_factory_inner.service.OtherFactoriesParser;
import ru.tkachenko.buyerassistent.summary.dependency_inner.service.DependencyParser;
import ru.tkachenko.buyerassistent.utils.CurrentDate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

@Service
public class SummaryService {

    private final SummaryDBService summaryDBService;
    private final DependencyParser dependencyParser;
    private final OtherFactoriesParser otherFactoriesParser;
    private final OracleParser oracleParser;
    private final Path FILE_STORAGE_LOCATION;
    private final FileStorageDBService fileStorageDBService;

    @Autowired
    public SummaryService(SummaryDBService summaryDBService, DependencyParser dependencyParser,
                          OtherFactoriesParser otherFactoriesParser, OracleParser oracleParser,
                          FileStorageProperties fileStorageProperties, FileStorageDBService fileStorageDBService) {
        this.summaryDBService = summaryDBService;
        this.dependencyParser = dependencyParser;
        this.otherFactoriesParser = otherFactoriesParser;
        this.oracleParser = oracleParser;
        this.FILE_STORAGE_LOCATION = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.fileStorageDBService = fileStorageDBService;
    }

    //TODO parseFilesToSummary need to return Path summaryFile and after need save this fileEntity in DB
    public void parseFilesToSummary(List<Path> filesPaths) {
        Path otherFactoriesPath = filesPaths.get(0);
        Path oracleMmkPath = filesPaths.get(1);
        Path dependenciesMmkPath = filesPaths.get(2);

        summaryDBService.truncateTable();
        dependencyParser.parse(dependenciesMmkPath);
        otherFactoriesParser.parse(otherFactoriesPath);
        oracleParser.parse(oracleMmkPath);
        try {
            Files.delete(otherFactoriesPath);
            Files.delete(oracleMmkPath);
            Files.delete(dependenciesMmkPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SavedFileEntity savedFileEntity = copySummaryTableToFile();
        fileStorageDBService.save(savedFileEntity);
        //copy summary_table to Excel file without styles and write this file to save_files_table

    }

    private SavedFileEntity copySummaryTableToFile() {
        CurrentDate currentDate = new CurrentDate();
        Timestamp savedTimestamp = currentDate.getTimestamp();
        String year = currentDate.getYear();
        String month = currentDate.getMonth();
        String day = currentDate.getDay();
        String time = currentDate.getTime();
        Path destination_directory = FILE_STORAGE_LOCATION.resolve(year)
                .resolve(month)
                .resolve(day);
        try {
            Files.createDirectories(destination_directory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String storageFileName = time + ".xlsx";
        Path targetFilePath = destination_directory.resolve(storageFileName);

        String[] headerColumnsNames = SummaryInfoUtil.getFileColumnsNamesForEntity();
        List<SummaryRowEntity> entities = summaryDBService.findAll();

        try(FileOutputStream fos = new FileOutputStream(targetFilePath.toString());
            XSSFWorkbook wb = new XSSFWorkbook();) {
            XSSFSheet sheet = wb.createSheet();
            XSSFCreationHelper creationHelper = wb.getCreationHelper();
            XSSFCellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd.MM.yyyy"));

            XSSFRow headerRow = sheet.createRow(0);
            for(int i = 0; i < headerColumnsNames.length; i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(headerColumnsNames[i]);
            }

            for (int rowNum = 1; rowNum <= entities.size(); rowNum++) {
                XSSFRow row = sheet.createRow(rowNum);
                SummaryRowEntity rowEntity = entities.get(rowNum-1);
                SummaryEntityWriter.writeToRow(dateStyle, rowEntity, row, true);
            }

            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SavedFileEntity(targetFilePath.toString(), storageFileName,
                savedTimestamp, year, month, day, time, true);
    }
}

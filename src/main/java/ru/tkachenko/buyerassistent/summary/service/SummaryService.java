package ru.tkachenko.buyerassistent.summary.service;

import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.file_storage.entity.SavedFileEntity;
import ru.tkachenko.buyerassistent.file_storage.service.FileDBService;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummaryService {

    private static final String EXTENSION = ".xlsx";
    private final SummaryDBService summaryDBService;
    private final FileDBService fileDBService;
    private final DependencyParser dependencyParser;
    private final OtherFactoriesParser otherFactoriesParser;
    private final OracleParser oracleParser;
    private final Path FILE_STORAGE_LOCATION;
    private final Path ZIP_DIRECTORY;

    @Autowired
    public SummaryService(SummaryDBService summaryDBService, DependencyParser dependencyParser,
                          OtherFactoriesParser otherFactoriesParser, OracleParser oracleParser,
                          FileStorageProperties fileStorageProperties, FileDBService fileDBService) {
        this.summaryDBService = summaryDBService;
        this.fileDBService = fileDBService;
        this.dependencyParser = dependencyParser;
        this.otherFactoriesParser = otherFactoriesParser;
        this.oracleParser = oracleParser;
        this.FILE_STORAGE_LOCATION = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.ZIP_DIRECTORY = FILE_STORAGE_LOCATION.resolve("forZip");
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
        fileDBService.save(savedFileEntity);
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

        String storageFileName = time + EXTENSION;
        Path targetFilePath = destination_directory.resolve(storageFileName);

        String[] headerColumnsNames = SummaryInfoUtil.getFileColumnsNamesForEntity();
        List<SummaryRowEntity> entities = summaryDBService.findAll();

        try(FileOutputStream fos = new FileOutputStream(targetFilePath.toString());
            XSSFWorkbook wb = new XSSFWorkbook()) {
            XSSFCreationHelper creationHelper = wb.getCreationHelper();
            XSSFCellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd.MM.yyyy"));
            XSSFSheet sheet = wb.createSheet();

            XSSFRow headerRow = sheet.createRow(0);
            SummaryWriter.writeHeader(headerRow, true);

            for (int rowNum = 1; rowNum <= entities.size(); rowNum++) {
                XSSFRow row = sheet.createRow(rowNum);
                SummaryRowEntity rowEntity = entities.get(rowNum-1);
                SummaryWriter.writeEntityToRow(dateStyle, rowEntity, row, true);
            }

            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SavedFileEntity(targetFilePath.toString(), storageFileName,
                savedTimestamp, year, month, day, time, true);
    }

    public List<Path> createAllBranchesFiles() {
        String[] allBranchesNames = SummaryInfoUtil.getAllBranchesNames();
        try {
            Files.createDirectories(ZIP_DIRECTORY);
            List<Path> branchFilesPaths = Arrays.stream(allBranchesNames).parallel()
                    .map(this::createBranchFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Path createBranchFile(String branchName) {
        String[] monthSheetsNames = SummaryInfoUtil.getMonthSheetNames();

        Path filePath = ZIP_DIRECTORY.resolve(branchName+EXTENSION);
        try(FileOutputStream fos = new FileOutputStream(filePath.toString());
        XSSFWorkbook wb = new XSSFWorkbook()) {
            XSSFCreationHelper creationHelper = wb.getCreationHelper();
            XSSFCellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd.MM.yyyy"));
            for(int i = 1; i <=12; i++) {
                List<SummaryRowEntity> entities = summaryDBService.findByBranchAndAcceptMonth(branchName, i);
                if(!entities.isEmpty()) {
                    XSSFSheet sheet = wb.createSheet(monthSheetsNames[i - 1]);
                    XSSFRow headerRow = sheet.createRow(0);
                    SummaryWriter.writeHeader(headerRow, false);
                    for (int rowNum = 1; rowNum <= entities.size(); rowNum++) {
                        XSSFRow row = sheet.createRow(rowNum);
                        SummaryRowEntity rowEntity = entities.get(rowNum-1);
                        SummaryWriter.writeEntityToRow(dateStyle, rowEntity, row, false);
                    }
                }
            }
            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }
}

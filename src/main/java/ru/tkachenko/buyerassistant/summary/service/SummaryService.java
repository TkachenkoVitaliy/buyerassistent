package ru.tkachenko.buyerassistant.summary.service;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.file_storage.entity.SavedFileEntity;
import ru.tkachenko.buyerassistant.file_storage.service.FileDBService;
import ru.tkachenko.buyerassistant.property.FileStorageProperties;
import ru.tkachenko.buyerassistant.settings.service.BranchStartMonthService;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.summary.oracle_inner.service.OracleParser;
import ru.tkachenko.buyerassistant.summary.other_factory_inner.service.OtherFactoriesParser;
import ru.tkachenko.buyerassistant.summary.dependency_inner.service.DependencyParser;
import ru.tkachenko.buyerassistant.utils.CellStyleContainer;
import ru.tkachenko.buyerassistant.utils.CurrentDate;
import ru.tkachenko.buyerassistant.utils.ExcelUtils;
import ru.tkachenko.buyerassistant.utils.FileUtils;

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

    private final BranchStartMonthService branchStartMonthService;
    private final Path FILE_STORAGE_LOCATION;
    private final Path ZIP_DIRECTORY;

    @Autowired
    public SummaryService(SummaryDBService summaryDBService, DependencyParser dependencyParser,
                          OtherFactoriesParser otherFactoriesParser, OracleParser oracleParser,
                          BranchStartMonthService branchStartMonthService, FileStorageProperties fileStorageProperties,
                          FileDBService fileDBService) {
        this.summaryDBService = summaryDBService;
        this.fileDBService = fileDBService;
        this.dependencyParser = dependencyParser;
        this.otherFactoriesParser = otherFactoriesParser;
        this.oracleParser = oracleParser;
        this.branchStartMonthService = branchStartMonthService;
        this.FILE_STORAGE_LOCATION = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.ZIP_DIRECTORY = FILE_STORAGE_LOCATION.resolve("forZip");
    }

    public void parseFilesToSummary(List<Path> filesPaths) {
        Path otherFactoriesPath = filesPaths.get(0);
        Path oracleMmkPath = filesPaths.get(1);
        Path dependenciesMmkPath = filesPaths.get(2);

        summaryDBService.truncateTable();
        dependencyParser.parse(dependenciesMmkPath);
        otherFactoriesParser.parse(otherFactoriesPath);
        oracleParser.parse(oracleMmkPath);
        fixZeroInAcceptMonth();
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

    private void fixZeroInAcceptMonth() {
        List<SummaryRowEntity> entitiesWithZeroAcceptMonth = summaryDBService.findZeroAcceptMonth();
        for (SummaryRowEntity entity : entitiesWithZeroAcceptMonth) {
            SummaryRowEntity entityWithAcceptMonth = summaryDBService.findSameSpecWithNotZeroAcceptMonth(entity);
            if (entityWithAcceptMonth != null) {
                entity.setAcceptMonth(entityWithAcceptMonth.getAcceptMonth());
                entity.setYear(entityWithAcceptMonth.getYear());
                summaryDBService.save(entity);
            }
        }
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
            CellStyleContainer cellStyles = new CellStyleContainer(wb);
            XSSFCreationHelper creationHelper = wb.getCreationHelper();
            CellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd.MM.yyyy"));
            XSSFSheet sheet = wb.createSheet();

            XSSFRow headerRow = sheet.createRow(0);
            SummaryWriter.writeHeader(headerRow, true, cellStyles);

            for (int rowNum = 1; rowNum <= entities.size(); rowNum++) {
                XSSFRow row = sheet.createRow(rowNum);
                SummaryRowEntity rowEntity = entities.get(rowNum-1);
                SummaryWriter.writeEntityToRow(cellStyles, rowEntity, row, true);
            }

            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SavedFileEntity(targetFilePath.toString(), storageFileName,
                savedTimestamp, year, month, day, time, true);
    }

    public List<Path> createAllBranchesFiles() {
        FileUtils.cleanDirectory(ZIP_DIRECTORY);

        String[] allBranchesNames = SummaryInfoUtil.getAllBranchesNames();
        List<Path> branchFilesPaths = null;
        try {
            Files.createDirectories(ZIP_DIRECTORY);
            branchFilesPaths = Arrays.stream(allBranchesNames)
                    .parallel()
                    .map(this::createBranchFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return branchFilesPaths;
    }


    private Path createBranchFile(String branchName) {
        String[] monthSheetsNames = SummaryInfoUtil.getMonthSheetNames();
        int startMonth = branchStartMonthService.getBranchStartMonth(branchName);
        int startYear = branchStartMonthService.getBranchStartYear(branchName);

        Path filePath = ZIP_DIRECTORY.resolve(branchName+EXTENSION);
        try(FileOutputStream fos = new FileOutputStream(filePath.toString());
        XSSFWorkbook wb = new XSSFWorkbook()) {
            CellStyleContainer cellStyles = new CellStyleContainer(wb);
            XSSFCreationHelper creationHelper = wb.getCreationHelper();
            CellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd.MM.yyyy"));

            for(int j = startYear; j <= 2026; j++) {
                for(int i = startMonth; i <=12; i++) {
//  TODO think about best way              List<SummaryRowEntity> entities = summaryDBService.findByBranchAndAcceptMonth(branchName, i);
                    List<SummaryRowEntity> entities = summaryDBService.findByBranchAndAcceptMonthSorted (branchName, i,
                            j);
                    if(!entities.isEmpty()) {
                        XSSFSheet sheet = wb.createSheet(monthSheetsNames[i - 1] + "_" + j);
                        ExcelUtils.setColumnWidthBranchFile(sheet);
                        XSSFRow headerRow = sheet.createRow(0);
                        SummaryWriter.writeHeader(headerRow, false, cellStyles);
                        for (int rowNum = 1; rowNum <= entities.size(); rowNum++) {
                            XSSFRow row = sheet.createRow(rowNum);
                            SummaryRowEntity rowEntity = entities.get(rowNum-1);
                            SummaryWriter.writeEntityToRow(cellStyles, rowEntity, row, false);
                        }
                    }
                }
            }

            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    public void updateProductTypeTable() {
        summaryDBService.updateProductTypeTable();
    }

    public List<SummaryRowEntity> findAllUndefinedBranchRows() {
        return summaryDBService.getAllUndefinedBranchRows();
    }
}

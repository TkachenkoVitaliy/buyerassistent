package ru.tkachenko.buyerassistent.summary.oracle_inner.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.summary.oracle_inner.dto.OracleDTO;
import ru.tkachenko.buyerassistent.summary.dependency_inner.entity.DependencyEntity;
import ru.tkachenko.buyerassistent.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistent.summary.dependency_inner.service.DependencyWorker;
import ru.tkachenko.buyerassistent.summary.service.SummaryDBService;
import ru.tkachenko.buyerassistent.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Date;

@Service
public class OracleParser {

    private final DependencyWorker dependencyWorker;
    private final SummaryDBService summaryDBService;

    @Autowired
    public OracleParser(DependencyWorker dependencyWorker, SummaryDBService summaryDBService) {
        this.dependencyWorker = dependencyWorker;
        this.summaryDBService = summaryDBService;
    }

    public void parse(Path oracleMmkPath) {
        try(FileInputStream fis = new FileInputStream(oracleMmkPath.toString());
            XSSFWorkbook wb = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = wb.getSheetAt(0);
            String[] oracleDTOColumnsNames = OracleInfoUtil.getOracleColumnsNamesForDTO();
            int headerRowIndex = ExcelUtils.findFirstNotBlankRow(sheet);
            int firstRowIndex = headerRowIndex + 1;
            int lastRowIndex = sheet.getLastRowNum();
            int[] oracleDTOColIndexes = ExcelUtils.getEntityColumnsIndexes(sheet, headerRowIndex, oracleDTOColumnsNames);

            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                Row currentRow = sheet.getRow(i);
                OracleDTO oracleDTO = parseToOracleDTO(oracleDTOColIndexes, currentRow);
                SummaryRowEntity summaryRowEntity = parseSummaryEntityFromOracleDTOAndDependencies(oracleDTO);
                summaryDBService.save(summaryRowEntity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OracleDTO parseToOracleDTO(int[] colIndexes, Row row) {
        OracleDTO oracleDTO = new OracleDTO();
        oracleDTO.setMill(ExcelUtils.getIntValue(colIndexes[0], row));
        oracleDTO.setConsignee(ExcelUtils.getStringValue(colIndexes[1], row));
        oracleDTO.setProductType(ExcelUtils.getStringValue(colIndexes[2], row));
        oracleDTO.setProfile(ExcelUtils.getStringValue(colIndexes[3], row));
        oracleDTO.setGrade(ExcelUtils.getStringValue(colIndexes[4], row));
        oracleDTO.setRal(ExcelUtils.getStringValue(colIndexes[5], row));
        oracleDTO.setContract(ExcelUtils.getStringValue(colIndexes[6], row));
        oracleDTO.setSpec(ExcelUtils.getStringValue(colIndexes[7], row));
        oracleDTO.setPosition(ExcelUtils.getIntValue(colIndexes[8], row));
        oracleDTO.setAcceptMonth(ExcelUtils.getIntValue(colIndexes[9], row));
        oracleDTO.setAccepted(ExcelUtils.getDoubleValue(colIndexes[10], row));
        oracleDTO.setShipped(ExcelUtils.getDoubleValue(colIndexes[11], row));
        oracleDTO.setShippedDate(ExcelUtils.getDateValue(colIndexes[12], row));
        oracleDTO.setVehicleNumber(ExcelUtils.getStringValue(colIndexes[13], row));
        oracleDTO.setInvoiceNumber(ExcelUtils.getIntValue(colIndexes[14], row));
        oracleDTO.setInvoiceDate(ExcelUtils.getDateValue(colIndexes[15], row));

        oracleDTO.setPriceWithoutNDS(ExcelUtils.getDoubleValue(colIndexes[16], row));
        oracleDTO.setPrt(ExcelUtils.getStringValue(colIndexes[17], row));
        oracleDTO.setStation(ExcelUtils.getStringValue(colIndexes[18], row));
        oracleDTO.setThickness(ExcelUtils.getDoubleValue(colIndexes[19], row));
        oracleDTO.setWidth(ExcelUtils.getDoubleValue(colIndexes[20], row));
        oracleDTO.setLength(ExcelUtils.getDoubleValue(colIndexes[21], row));
        oracleDTO.setAdditionalRequirements(ExcelUtils.getStringValue(colIndexes[22], row));

        return oracleDTO;
    }

    private SummaryRowEntity parseSummaryEntityFromOracleDTOAndDependencies(OracleDTO oracleDTO) {
        String supplier = "ММК";
        int mill = oracleDTO.getMill();

        String branch = null;
        String sellType = null;
        String client = null;
        DependencyEntity dependencyEntity = dependencyWorker.findDependencyEntity(oracleDTO);
        if(dependencyEntity != null) {
            branch = dependencyEntity.getBranch();
            sellType = dependencyEntity.getSellType();
            client = dependencyEntity.getClient();
        }
        String consignee = oracleDTO.getConsignee();
        String productType = oracleDTO.getProductType();

        String profile = null;
        if(oracleDTO.getProfile() != null) {
            profile = oracleDTO.getProfile();
        } else {
            //TODO method for parse profile
        }

        String grade = oracleDTO.getGrade();
        String ral = oracleDTO.getRal();
        double issued = oracleDTO.getAccepted();
        String contract = oracleDTO.getContract();
        String spec = oracleDTO.getSpec();
        int position = oracleDTO.getPosition();
        int acceptMonth = oracleDTO.getAcceptMonth();//TODO check != 0
        int year = 2022;
        double accepted = oracleDTO.getAccepted();
        double price = oracleDTO.getPriceWithoutNDS() * 1.2;
        double acceptedCost = accepted * price;
        double shipped = oracleDTO.getShipped();
        double shippedCost = shipped * price;
        Date shippedDate = oracleDTO.getShippedDate();
        String vehicleNumber = oracleDTO.getVehicleNumber();
        int invoiceNumber = oracleDTO.getInvoiceNumber();
        Date invoiceDate = oracleDTO.getInvoiceDate();
        double finalPrice = 0.0;
        double finalCost = shipped * price;

        return new SummaryRowEntity(supplier, mill, branch, sellType, client, consignee, productType, profile, grade,
                ral, issued, contract, spec, position, acceptMonth, year, accepted, price, acceptedCost, shipped,
                shippedCost, shippedDate, vehicleNumber, invoiceNumber, invoiceDate, finalPrice, finalCost);
    }

}

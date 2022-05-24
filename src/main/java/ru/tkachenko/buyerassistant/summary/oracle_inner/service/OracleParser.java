package ru.tkachenko.buyerassistant.summary.oracle_inner.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.summary.oracle_inner.dto.OracleDTO;
import ru.tkachenko.buyerassistant.summary.dependency_inner.entity.DependencyEntity;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.summary.dependency_inner.service.DependencyWorker;
import ru.tkachenko.buyerassistant.summary.service.ProfileParser;
import ru.tkachenko.buyerassistant.summary.service.SummaryDBService;
import ru.tkachenko.buyerassistant.utils.CurrentDate;
import ru.tkachenko.buyerassistant.utils.ExcelUtils;
import ru.tkachenko.buyerassistant.utils.RegexUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class OracleParser {

    private final DependencyWorker dependencyWorker;
    private final SummaryDBService summaryDBService;
    private final ProfileParser profileParser;

    @Autowired
    public OracleParser(DependencyWorker dependencyWorker, SummaryDBService summaryDBService, ProfileParser profileParser) {
        this.dependencyWorker = dependencyWorker;
        this.summaryDBService = summaryDBService;
        this.profileParser = profileParser;
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

            List<XSSFRow> rows = new ArrayList<>();
            for(int i = firstRowIndex; i <= lastRowIndex; i++) {
                rows.add(sheet.getRow(i));
            }

            rows.parallelStream()
                    .filter(row -> filterLastYearRows(oracleDTOColIndexes, row)) //
                    .map(row -> parseToOracleDTO(oracleDTOColIndexes, row))
                    .map(this::parseSummaryEntityFromOracleDTOAndDependencies)
                    .forEach(summaryDBService::save);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OracleDTO parseToOracleDTO(int[] colIndexes, Row row) {
        OracleDTO oracleDTO = new OracleDTO();
        oracleDTO.setMill(ExcelUtils.getIntValue(colIndexes[0], row));
        oracleDTO.setConsignee(ExcelUtils.getStringValueWithoutQuote(colIndexes[1], row));
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
        SimpleDateFormat shippedDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        oracleDTO.setShippedDate(ExcelUtils.getDateValue(colIndexes[12], row, shippedDateFormat));
        oracleDTO.setVehicleNumber(ExcelUtils.getStringValue(colIndexes[13], row));
        oracleDTO.setInvoiceNumber(ExcelUtils.getIntValue(colIndexes[14], row));
        SimpleDateFormat invoiceDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        oracleDTO.setInvoiceDate(ExcelUtils.getDateValue(colIndexes[15], row, invoiceDateFormat));
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
            String withoutFractional = RegexUtil.removeFractionalPartWithZero(oracleDTO.getProfile());
            profile = RegexUtil.replaceDelimiterAndDot(withoutFractional);
        } else {
            profile = profileParser.parse(oracleDTO.getProductType(), oracleDTO.getSpec(), oracleDTO.getPosition());
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

    private boolean filterLastYearRows(int[] colIndexes, Row row) {
        CurrentDate currentDate = new CurrentDate();
        SimpleDateFormat shippedDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        int orderYear = ExcelUtils.getDateValue(colIndexes[23], row, shippedDateFormat).getYear() + 1900;
        //The month value returned is between 0 and 11
        int orderMonth =  ExcelUtils.getDateValue(colIndexes[23], row, shippedDateFormat).getMonth();
        int currentYear = Integer.parseInt(currentDate.getYear());
        if (currentYear > orderYear){
            if (orderMonth == (10 | 11)){
                return false;
            }
        }
        return true;
    }
}

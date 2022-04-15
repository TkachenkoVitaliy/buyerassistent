package ru.tkachenko.buyerassistent.service.summary;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class OtherFactoriesParser {
    private final Path filePath;
    private final String[] monthSheetNames = {"Январь_2022", "Февраль_2022", "Март_2022", "Апрель_2022", "Май_2022",
            "Июнь_2022", "Июль_2022", "Август_2022", "Сентябрь_2022", "Октябрь_2022", "Ноябрь_2022", "Декабрь_2022"};

    public OtherFactoriesParser(Path filePath) {
        this.filePath = filePath;
    }

    public void parse() {
        try {
            FileInputStream fis = new FileInputStream(filePath.toString());
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            for(String monthSheetName : monthSheetNames) {
                XSSFSheet sheet = wb.getSheet(monthSheetName);
                if(sheet != null) parseSheet(sheet);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseSheet(Sheet sheet) {

        //TODO remove sout
        System.out.println(sheet.getSheetName());
    }



}

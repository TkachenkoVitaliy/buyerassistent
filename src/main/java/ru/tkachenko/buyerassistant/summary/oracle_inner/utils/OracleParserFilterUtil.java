package ru.tkachenko.buyerassistant.summary.oracle_inner.utils;

import org.apache.poi.ss.usermodel.Row;
import ru.tkachenko.buyerassistant.utils.CurrentDate;
import ru.tkachenko.buyerassistant.utils.ExcelUtils;

import java.text.SimpleDateFormat;

public class OracleParserFilterUtil {

    public static boolean filterLastYearRows(int[] colIndexes, Row row) {
        CurrentDate currentDate = new CurrentDate();
        SimpleDateFormat shippedDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        //yellow
        int orderYear = ExcelUtils.getDateValue(colIndexes[23], row, shippedDateFormat).getYear() + 1900;
        int orderMonth = ExcelUtils.getDateValue(colIndexes[23], row, shippedDateFormat).getMonth() + 1;
        int orderHashCode = (orderYear * 12) + orderMonth;
        //green
        int acceptMonth =  ExcelUtils.getIntValue(colIndexes[9], row);// what happens if collum is empty?
        int acceptYear = Integer.parseInt(currentDate.getYear());
        int acceptHashCode = (acceptYear * 12) + acceptMonth;

        if (acceptHashCode - orderHashCode < 0 || acceptHashCode - orderHashCode >= 6){
            return false;
        }
        return true;
    }
}

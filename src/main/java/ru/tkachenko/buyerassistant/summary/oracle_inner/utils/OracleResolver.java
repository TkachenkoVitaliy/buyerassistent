package ru.tkachenko.buyerassistant.summary.oracle_inner.utils;

import ru.tkachenko.buyerassistant.summary.oracle_inner.dto.OracleDTO;
import ru.tkachenko.buyerassistant.utils.CurrentDate;


public class OracleResolver {
    private CurrentDate currentDate = new CurrentDate();

    public int resolveYear(OracleDTO oracleDTO) {
        java.sql.Date orderDate = oracleDTO.getOrderDate();
        int orderYear = orderDate.getYear() + 1900;
        int orderMonth = orderDate.getMonth() + 1;
        int orderCode = getMonthsCount(orderYear, orderMonth);

        int acceptMonth = oracleDTO.getAcceptMonth();
        int acceptYear = currentDate.getYearInt();
        int acceptCode = getMonthsCount(acceptYear, acceptMonth);

        if(acceptCode - orderCode < 0) {
            return acceptYear + 1;
        }
        if(acceptCode - orderCode >= 6) {
            return acceptYear - 1;
        }

        return acceptYear;
    }

    private int getMonthsCount(int year, int month) {
        return year * 12 + month;
    }
}

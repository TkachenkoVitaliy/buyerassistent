package ru.tkachenko.buyerassistent.summary.oracle_inner.service;

public class OracleInfoUtil {
    private final static String MILL_COL_NAME = "Стан";
    private final static String CONSIGNEE_COL_NAME = "Грузополучатель";
    private final static String PRODUCT_TYPE_COL_NAME = "Продукция";
    private final static String PROFILE_COL_NAME = "Профиль";
    private final static String GRADE_COL_NAME = "Марка";
    private final static String RAL_COL_NAME = "RAL";
    private final static String CONTRACT_COL_NAME = "Договор";
    private final static String SPEC_COL_NAME = "Заказ";
    private final static String POSITION_COL_NAME = "Номер строки";
    private final static String ACCEPT_MONTH_COL_NAME = "Месяц отгруз";
    private final static String ACCEPTED_COL_NAME = "Заказано";
    private final static String SHIPPED_COL_NAME = "Нетто отгруз";
    private final static String SHIPPED_DATE_COL_NAME = "Дата отгруз";
    private final static String VEHICLE_NUMBER_COL_NAME = "Вагон";
    private final static String INVOICE_NUMBER_COL_NAME = "Счет-фактура";
    private final static String INVOICE_DATE_COL_NAME = "Дата СФ";

    private final static String PRICE_WITHOUT_NDS_COL_NAME = "Цена";
    private final static String PRT_COL_NAME = "Описание ПРТ";
    private final static String STATION_COL_NAME = "Станция";
    private final static String THICKNESS_COL_NAME = "Толщ";
    private final static String WIDTH_COL_NAME = "Ширина";
    private final static String LENGTH_COL_NAME = "Длина";
    private final static String ADDITIONAL_REQUIREMENTS_COL_NAME = "Тесты";

    private final static String[] oracleColumnsNamesForDTO = {
            MILL_COL_NAME, CONSIGNEE_COL_NAME, PRODUCT_TYPE_COL_NAME, PROFILE_COL_NAME, GRADE_COL_NAME, RAL_COL_NAME,
            CONTRACT_COL_NAME, SPEC_COL_NAME, POSITION_COL_NAME, ACCEPT_MONTH_COL_NAME, ACCEPTED_COL_NAME,
            SHIPPED_COL_NAME, SHIPPED_DATE_COL_NAME, VEHICLE_NUMBER_COL_NAME, INVOICE_NUMBER_COL_NAME,
            INVOICE_DATE_COL_NAME, PRICE_WITHOUT_NDS_COL_NAME, PRT_COL_NAME, STATION_COL_NAME, THICKNESS_COL_NAME,
            WIDTH_COL_NAME, LENGTH_COL_NAME, ADDITIONAL_REQUIREMENTS_COL_NAME};

    public static String[] getOracleColumnsNamesForDTO() {
        return oracleColumnsNamesForDTO;
    }
}

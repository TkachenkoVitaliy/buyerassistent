package ru.tkachenko.buyerassistent.service.summary;

public class SummaryInfoUtil {
    private final static String SUPPLIER_COL_NAME = "Поставщик";
    private final static String MILL_COL_NAME = "Стан";
    private final static String BRANCH_COL_NAME = "База";
    private final static String SELL_TYPE_COL_NAME = "Вид поставки";
    private final static String CLIENT_COL_NAME = "Транзитн. Клиент";
    private final static String CONSIGNEE_COL_NAME = "Грузополучатель";
    private final static String PRODUCT_TYPE_COL_NAME = "Вид продукции";
    private final static String PROFILE_COL_NAME = "Размеры/профиль";
    private final static String GRADE_COL_NAME = "Марка/класс";
    private final static String RAL_COL_NAME = "Класс покрытия /RAL";
    private final static String ISSUED_COL_NAME = "Выдано, тн";
    private final static String CONTRACT_COL_NAME = "Договор";
    private final static String SPEC_COL_NAME = "Номер СПЕЦ";
    private final static String POSITION_COL_NAME = "Номер позиции";
    private final static String ACCEPT_MONTH_COL_NAME = "Месяц акцепта";
    private final static String YEAR_COL_NAME = "Год акцепта";
    private final static String ACCEPTED_COL_NAME = "Акцепт, тн";
    private final static String PRICE_COL_NAME = "Цена с НДС, руб/тн";
    private final static String ACCEPTED_COST_COL_NAME = "Стоимость, руб";
    private final static String SHIPPED_COL_NAME = "Отгруженно, тн";
    private final static String SHIPPED_COST_COL_NAME = "Стоимость отгр, руб";
    private final static String SHIPPED_DATE_COL_NAME = "Дата отгрузки";
    private final static String VEHICLE_NUMBER_COL_NAME = "Номер ТС/вагона";
    private final static String INVOICE_NUMBER_COL_NAME = "№ СФ";
    private final static String INVOICE_DATE_COL_NAME = "Дата СФ";
    private final static String FINAL_PRICE_COL_NAME = "Пересмотр, руб/тн";
    private final static String FINAL_COST_COL_NAME = "Итоговая стоимость, тн";
    private final static String[] fileColumnsNamesForEntity = {SUPPLIER_COL_NAME, MILL_COL_NAME, BRANCH_COL_NAME,
            SELL_TYPE_COL_NAME, CLIENT_COL_NAME, CONSIGNEE_COL_NAME, PRODUCT_TYPE_COL_NAME, PROFILE_COL_NAME,
            GRADE_COL_NAME, RAL_COL_NAME, ISSUED_COL_NAME, CONTRACT_COL_NAME, SPEC_COL_NAME, POSITION_COL_NAME,
            ACCEPT_MONTH_COL_NAME, YEAR_COL_NAME, ACCEPTED_COL_NAME, PRICE_COL_NAME, ACCEPTED_COST_COL_NAME,
            SHIPPED_COL_NAME, SHIPPED_COST_COL_NAME, SHIPPED_DATE_COL_NAME, VEHICLE_NUMBER_COL_NAME,
            INVOICE_NUMBER_COL_NAME, INVOICE_DATE_COL_NAME, FINAL_PRICE_COL_NAME, FINAL_COST_COL_NAME};

    public static String[] getFileColumnsNamesForEntity() {
        return fileColumnsNamesForEntity;
    }
}

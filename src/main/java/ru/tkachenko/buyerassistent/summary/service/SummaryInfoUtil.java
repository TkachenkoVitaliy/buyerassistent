package ru.tkachenko.buyerassistent.summary.service;

public class SummaryInfoUtil {
    private final static String KRASNODAR = "Краснодар";
    private final static String ROSTOV = "Ростов";
    private final static String MOSCOW = "Москва";
    private final static String NOVGOROD = "Новгород";
    private final static String KAZAN = "Казань";
    private final static String NAB_CHELNY = "Набережные Челны";
    private final static String IZHEVSK = "Ижевск";
    private final static String PERM = "Пермь";
    private final static String UFA = "Уфа";
    private final static String CHELYABINSK = "Челябинск";
    private final static String YEKATERINBURG = "Екатеринбург";
    private final static String TUMEN = "Тюмень";
    private final static String OMSK = "Омск";
    private final static String NOVOSIBIRSK = "Новосибирск";
    private final static String NOVOKUZNETSK = "Новокузнецк";
    private final static String[] allBranchesNames = {KRASNODAR, ROSTOV, MOSCOW, NOVGOROD, KAZAN, NAB_CHELNY, IZHEVSK,
            PERM, UFA, CHELYABINSK, YEKATERINBURG, TUMEN, OMSK, NOVOSIBIRSK, NOVOKUZNETSK};

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
    private final static String[] clippedFileColumnsNames = {SUPPLIER_COL_NAME, MILL_COL_NAME, SELL_TYPE_COL_NAME,
            CLIENT_COL_NAME, CONSIGNEE_COL_NAME, PRODUCT_TYPE_COL_NAME, PROFILE_COL_NAME, GRADE_COL_NAME, RAL_COL_NAME,
            SPEC_COL_NAME, POSITION_COL_NAME, ACCEPT_MONTH_COL_NAME, YEAR_COL_NAME, ACCEPTED_COL_NAME, SHIPPED_COL_NAME,
            SHIPPED_DATE_COL_NAME, VEHICLE_NUMBER_COL_NAME};

    private final static String[] monthSheetNames = {"Январь_2022", "Февраль_2022", "Март_2022", "Апрель_2022", "Май_2022",
            "Июнь_2022", "Июль_2022", "Август_2022", "Сентябрь_2022", "Октябрь_2022", "Ноябрь_2022", "Декабрь_2022"};

    public static String[] getFileColumnsNamesForEntity() {
        return fileColumnsNamesForEntity;
    }

    public static String[] getAllBranchesNames() {
        return allBranchesNames;
    }

    public static String[] getMonthSheetNames() {
        return monthSheetNames;
    }

    public static String[] getClippedFileColumnsNames () {
        return clippedFileColumnsNames;
    }
}

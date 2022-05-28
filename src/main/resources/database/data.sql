do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM branches_settings_table;
    IF valueCounts = 0 THEN
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Краснодар'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Ростов'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Москва'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Новгород'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Казань'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Набережные Челны'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Ижевск'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Пермь'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Уфа'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Челябинск'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Екатеринбург'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Тюмень'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Омск'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Новосибирск'', 1, 2022);
        INSERT INTO branches_settings_table (name, start_month, start_year) values (''Новокузнецк'', 1, 2022);
    END IF;
END
' LANGUAGE plpgsql;

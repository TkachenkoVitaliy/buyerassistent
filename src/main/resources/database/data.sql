do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM branches_settings_table;
    IF valueCounts = 0 THEN
        INSERT INTO branches_settings_table (name, start_month) values (''Краснодар'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Ростов'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Москва'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Новгород'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Казань'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Набережные Челны'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Ижевск'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Пермь'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Уфа'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Челябинск'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Екатеринбург'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Тюмень'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Омск'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Новосибирск'', 1);
        INSERT INTO branches_settings_table (name, start_month) values (''Новокузнецк'', 1);
    END IF;
END
' LANGUAGE plpgsql;

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


do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM product_group_table;
    IF valueCounts = 0 THEN
        INSERT INTO product_group_table (name) values (''Арматура'');
        INSERT INTO product_group_table (name) values (''Прокат г/к'');
        INSERT INTO product_group_table (name) values (''Прокат х/к'');
        INSERT INTO product_group_table (name) values (''Прокат ГЦ'');
        INSERT INTO product_group_table (name) values (''Полимеры'');
        INSERT INTO product_group_table (name) values (''Фасон'');
        INSERT INTO product_group_table (name) values (''Труба'');
    END IF;
END
' LANGUAGE plpgsql;


do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM role;
    IF valueCounts = 0 THEN
        INSERT INTO role (name) values (''USER'');
        INSERT INTO role (name) values (''ADMIN'');
    END IF;
END
' LANGUAGE plpgsql;


do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM usr_role;
    IF valueCounts = 0 THEN
        INSERT INTO usr_role (usr_id, role_id) values (1,2);
    END IF;
END
' LANGUAGE plpgsql;


do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM product_type_table;
    IF valueCounts = 0 THEN
        INSERT INTO product_type_table (id, product_type, product_group_id) values (1, ''Лист г/к'', 2);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (2, ''Арматура'', 1);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (3, ''Полоса'', 6);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (4, ''лист х/к'', 3);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (5, ''лист г/к чечевица'', 2);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (6, ''лента г/к'', 2);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (7, ''квадрат'', 6);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (8, ''УГОЛ'', 6);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (9, ''уголок'', 6);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (10, ''швеллер'', 6);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (11, ''рулон х/к'', 3);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (12, ''рулон х/к цинк'', 4);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (13, ''лента х/к'', 3);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (14, ''лист гц'', 4);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (15, ''лента гц'', 4);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (16, ''лента гц'', 2);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (17, ''лист г/к ромбическое рифление'', 2);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (18, ''лист г/к чеч'', 2);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (19, ''лист г/к'', 2);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (20, ''лист г/к чечевичное рифление'', 2);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (21, ''рулон г/ц'', 4);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (22, ''Круг'', 1);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (23, ''Рулон ГЦ_полимер'', 5);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (24, ''Рулон ГЦ'', 4);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (25, ''Уголок г/к'', 6);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (26, ''Лист г/к травленый'', 2);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (27, ''Швеллер г/к'', 6);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (28, ''Профиль арматурный_моток'', 1);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (29, ''Спецпрофиль х/г'', 6);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (30, ''Рулон г/к'', 2);
        INSERT INTO product_type_table (id, product_type, product_group_id) values (31, ''Спецпрофиль г/к'', 6);
    END IF;
END
' LANGUAGE plpgsql; 

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


-- do '
-- DECLARE
--     valueCounts INTEGER;
-- BEGIN
--     SELECT COUNT(*) INTO valueCounts FROM role;
--     IF valueCounts = 0 THEN
--         INSERT INTO role (name) values (''USER'');
--         INSERT INTO role (name) values (''ADMIN'');
--     END IF;
-- END
-- ' LANGUAGE plpgsql;

-- do '
-- DECLARE
--     valueCounts INTEGER;
-- BEGIN
--     SELECT COUNT(*) INTO valueCounts FROM usr;
--     IF valueCounts = 0 THEN
--         INSERT INTO usr (username, password) values (''admin'',''$2a$10$JWkkH/Vi16wcdDOnHJ5rAuZ4kHJ1HSSDkhlbCVND.iysFsF/ae1/y'');
--     END IF;
-- END
-- ' LANGUAGE plpgsql;


-- do '
-- DECLARE
--     valueCounts INTEGER;
-- BEGIN
--     SELECT COUNT(*) INTO valueCounts FROM usr_role;
--     IF valueCounts = 0 THEN
--         INSERT INTO usr_role (usr_id, role_id) values (1,2);
--     END IF;
-- END
-- ' LANGUAGE plpgsql;


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
        INSERT INTO product_group_table (name) values (''Не определена'');
    END IF;
END
' LANGUAGE plpgsql;


do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM product_type_table;
    IF valueCounts = 0 THEN
        INSERT INTO product_type_table (name, product_group_id) values (''Лист г/к'', 2);
        INSERT INTO product_type_table (name, product_group_id) values (''Арматура'', 1);
        INSERT INTO product_type_table (name, product_group_id) values (''Полоса'', 6);
        INSERT INTO product_type_table (name, product_group_id) values (''лист х/к'', 3);
        INSERT INTO product_type_table (name, product_group_id) values (''лист г/к чечевица'', 2);
        INSERT INTO product_type_table (name, product_group_id) values (''лента г/к'', 2);
        INSERT INTO product_type_table (name, product_group_id) values (''квадрат'', 6);
        INSERT INTO product_type_table (name, product_group_id) values (''УГОЛ'', 6);
        INSERT INTO product_type_table (name, product_group_id) values (''уголок'', 6);
        INSERT INTO product_type_table (name, product_group_id) values (''швеллер'', 6);
        INSERT INTO product_type_table (name, product_group_id) values (''рулон х/к'', 3);
        INSERT INTO product_type_table (name, product_group_id) values (''рулон х/к цинк'', 4);
        INSERT INTO product_type_table (name, product_group_id) values (''лента х/к'', 3);
        INSERT INTO product_type_table (name, product_group_id) values (''лист гц'', 4);
        INSERT INTO product_type_table (name, product_group_id) values (''лента гц'', 4);
        INSERT INTO product_type_table (name, product_group_id) values (''лист г/к чечевичка'', 2);
        INSERT INTO product_type_table (name, product_group_id) values (''лист г/к ромбическое рифление'', 2);
        INSERT INTO product_type_table (name, product_group_id) values (''лист г/к чеч'', 2);
        INSERT INTO product_type_table (name, product_group_id) values (''лист г/к'', 2);
        INSERT INTO product_type_table (name, product_group_id) values (''лист г/к чечевичное рифление'', 2);
        INSERT INTO product_type_table (name, product_group_id) values (''рулон г/ц'', 4);
        INSERT INTO product_type_table (name, product_group_id) values (''Круг'', 1);
        INSERT INTO product_type_table (name, product_group_id) values (''Рулон ГЦ_полимер'', 5);
        INSERT INTO product_type_table (name, product_group_id) values (''Рулон ГЦ'', 4);
        INSERT INTO product_type_table (name, product_group_id) values (''Уголок г/к'', 6);
        INSERT INTO product_type_table (name, product_group_id) values (''Лист г/к травленый'', 2);
        INSERT INTO product_type_table (name, product_group_id) values (''Швеллер г/к'', 6);
        INSERT INTO product_type_table (name, product_group_id) values (''Профиль арматурный_моток'', 1);
        INSERT INTO product_type_table (name, product_group_id) values (''Спецпрофиль х/г'', 6);
        INSERT INTO product_type_table (name, product_group_id) values (''Рулон г/к'', 2);
        INSERT INTO product_type_table (name, product_group_id) values (''Спецпрофиль г/к'', 6);
    END IF;
END
' LANGUAGE plpgsql;

do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM total_settings;
    IF valueCounts = 0 THEN
        INSERT INTO total_settings (username, month, year) values (''admin'', 6, 2022);
    END IF;
END
' LANGUAGE plpgsql;

do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM roles;
    IF valueCounts = 0 THEN
        INSERT INTO roles (name) values (''ROLE_ADMIN'');
        INSERT INTO roles (name) values (''ROLE_MODERATOR'');
        INSERT INTO roles (name) values (''ROLE_USER'');
        INSERT INTO roles (name) values (''ROLE_GUEST'');
    END IF;
END
' LANGUAGE plpgsql;

do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM users;
    IF valueCounts = 0 THEN
        INSERT INTO users (email, password, username) values (''v.tkachenko@steeleks.ru'', ''$2a$10$ASD8lbcHBw1u4ZgQ49pSCu2oUwXrUHjfN8q8QBlLxcUG8lneETTie'', ''admin'');
    END IF;
END
' LANGUAGE plpgsql;

do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM user_roles;
    IF valueCounts = 0 THEN
        INSERT INTO user_roles ( user_id, role_id) values (1, 1);
    END IF;
END
' LANGUAGE plpgsql;

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

/*
do '
DECLARE
    valueCounts INTEGER;
BEGIN
    SELECT COUNT(*) INTO valueCounts FROM mail_table;
    IF valueCounts = 0 THEN
        INSERT INTO mail_table (branch_name, email_address) values (''Краснодар'', ''e.sheptunov@krd.uralsibtrade.ru'');
        INSERT INTO mail_table (branch_name, email_address) values (''Ростов'', ''n.dytchenkov@uralsibtrade.ru'');
        INSERT INTO mail_table (branch_name, email_address) values (''Москва'', ''l.galyamova@uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Москва'', ''a.kulagina@msk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Москва'', ''a.lyhin@msk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Москва'', ''m.krasin@msk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Москва'', ''zakup@msk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Москва'', ''buh@msk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Новгород'', ''a.lugovoy@nn.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Казань'', ''t.galeev@kama.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Набережные Челны'', ''n.remeslova@kama.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Набережные Челны'', ''t.galeev@kama.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Ижевск'', ''v.morgunov@izhevsk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Пермь'', ''v.kiselev@ek.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Уфа'', ''a.kostina@ufa.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Уфа'', ''n.loseva@ufa.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Челябинск'', ''a.pakulina@chel.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Екатеринбург'', ''o.zalavin@uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Екатеринбург'', ''d.krupin@ek.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Екатеринбург'', ''zakup@msk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Тюмень'', ''i.pesterev@tumen.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Тюмень'', ''v.rajhert@tumen.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Тюмень'', ''sales@tumen.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Тюмень'', ''zakup@msk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Омск'', ''s.voronov@omsk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Омск'', ''zakup@msk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Омск'', ''a.adabir@omsk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Новосибирск'', ''k.shashenko@nsk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Новокузнецк'', ''matveenko.vn@nvk.uralsibtrade.ru '');
        INSERT INTO mail_table (branch_name, email_address) values (''Новокузнецк'', ''k.shashenko@nsk.uralsibtrade.ru '');
    END IF;
END
' LANGUAGE plpgsql
*/


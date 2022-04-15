CREATE TABLE IF NOT EXISTS saved_files_table
(
    id    BIGINT PRIMARY KEY ,
    file_path VARCHAR (255) NOT NULL ,
    storage_file_name  VARCHAR(150) NOT NULL ,
    saved_timestamp TIMESTAMP(0) NOT NULL ,
    year  VARCHAR(4) NOT NULL ,
    month  VARCHAR(2) NOT NULL ,
    day  VARCHAR(2) NOT NULL ,
    time  VARCHAR(5) NOT NULL ,
    is_actual BOOLEAN
);
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS summary_table
(
    id    BIGINT PRIMARY KEY ,
    supplier VARCHAR(50) NOT NULL,
    mill INTEGER,
    branch VARCHAR(50),
    sell_type VARCHAR(10),
    client VARCHAR(100),
    consignee VARCHAR,
    product_type VARCHAR(30),
    profile VARCHAR(100),
    grade VARCHAR(50),
    ral VARCHAR(50),
    issued NUMERIC,
    contract VARCHAR(50),
    spec VARCHAR(50),
    position INTEGER,
    accept_month INTEGER,
    year INTEGER,
    accepted NUMERIC,
    price NUMERIC,
    accepted_cost NUMERIC,
    shipped NUMERIC,
    shipped_cost NUMERIC,
    shipped_date date,
    vehicle_number VARCHAR(50),
    invoice_number INTEGER,
    invoice_date date,
    final_price NUMERIC,
    final_cost NUMERIC
);
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS accept_table;
CREATE TABLE IF NOT EXISTS accept_table
(
    id    BIGINT PRIMARY KEY,
    spec VARCHAR(50) NOT NULL ,
    position INTEGER NOT NULL ,
    nomenclature VARCHAR(200),
    grade VARCHAR(50),
    thickness NUMERIC,
    width NUMERIC,
    length NUMERIC,
    alter_profile VARCHAR(200),
    accepted NUMERIC,
    accept_month INTEGER,
    additional_requirements VARCHAR
);
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS dependency_table
(
    id    BIGINT PRIMARY KEY ,
    consignee VARCHAR,
    station VARCHAR(50),
    spec VARCHAR(50),
    position INTEGER,
    branch VARCHAR(50) NOT NULL,
    sell_type VARCHAR(10) NOT NULL,
    client VARCHAR(100),
    priority INTEGER
);
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

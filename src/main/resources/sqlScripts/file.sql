use infoportal;


CREATE TABLE home_owner
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(100) NOT NULL,
    surname          VARCHAR(100) NOT NULL,
    date_birth       DATE         NOT NULL,
    passport_details VARCHAR(20)  NOT NULL UNIQUE,
    owner_id         varchar(10)  NOT NULL UNIQUE
);

CREATE TABLE home_address
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    home_address     VARCHAR(50) NOT NULL UNIQUE,
    home_sector      INT         NOT NULL,
    home_area        DECIMAL(10, 2),
    count_residents  INT         NOT NULL,
    areas_payment    DECIMAL(10, 2) DEFAULT 0,
    to_pay           DECIMAL(10, 2) default 0,
    personal_account varchar(20) NOT NULL UNIQUE,
    owner_id         varchar(10) NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES home_owner (owner_id),
    FOREIGN KEY (home_sector) REFERENCES sectors (sector)
);



CREATE TABLE home_user
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    login      VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL unique,
    last_login varchar(30) default 0,
    owner_id   varchar(10)  not null unique,
    foreign key (owner_id) references home_owner (owner_id)

);


create table future_homes
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    home_address     VARCHAR(50) NOT NULL UNIQUE,
    home_sector      INT         NOT NULL,
    home_area        DECIMAL(10, 2),
    personal_account varchar(20) NOT NULL UNIQUE,
    foreign key (home_sector) references sectors (sector)
);

CREATE TABLE sectors
(
    id     int auto_increment primary key,
    sector int not null unique
);

create table archive_payment
(
    id            int auto_increment primary key,
    date_payment  date           not null,
    owner_id      varchar(10)    NOT NULL,
    price_payment DECIMAL(10, 2) not null,
    FOREIGN KEY (owner_id) REFERENCES home_owner (owner_id)
);


create table archive_accruals
(
    id            int auto_increment primary key,
    date_accruals date           not null,
    owner_id      varchar(10)    NOT NULL,
    kilowatt      int default 0,
    price_payment DECIMAL(10, 2) not null,
    FOREIGN KEY (owner_id) REFERENCES home_owner (owner_id)
);



create table keys_for_admin
(
    id        int auto_increment primary key,
    name_role varchar(10) not null,
    value_key varchar(30) not null unique,
    foreign key (name_role) references value_name_role (name_role)
);

create table keys_for_employees
(
    id        int auto_increment primary key,
    name_role varchar(10) not null,
    value_key varchar(30) not null unique,
    foreign key (name_role) references value_name_role (name_role)
);


CREATE INDEX idx_role_name ON value_name_role (name_role);



create table value_name_role
(
    id        int auto_increment primary key,
    name_role varchar(10) not null
);



CREATE TABLE company_employers
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    login           VARCHAR(50)  NOT NULL UNIQUE,
    password        VARCHAR(100) NOT NULL unique,
    last_login      varchar(30) default 0,
    company_role    varchar(10)  not null CHECK (company_role = 'emp'),
    employer_key    varchar(10)  not null,
    employer_sector int          not null,
    foreign key (employer_key) references keys_for_employees (value_key),
    foreign key (company_role) references value_name_role (name_role),
    foreign key (employer_sector) references sectors (sector)

);


CREATE TABLE company_admin
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    login        VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(100) NOT NULL unique,
    last_login   varchar(30) default 0,
    company_role varchar(10)  not null CHECK (company_role = 'adm'),
    admin_key    varchar(10)  not null,
    foreign key (admin_key) references keys_for_admin (value_key),
    foreign key (company_role) references value_name_role (name_role)
);

CREATE TABLE company_contacts
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(30) NOT NULL UNIQUE,
    tel     varchar(50) NOT NULL unique,
    address varchar(50) not null unique

);

CREATE TABLE company_tariff
(
    id                    INT AUTO_INCREMENT PRIMARY KEY,
    per_kilowatt_per_hour DECIMAL(10, 2) NOT NULL UNIQUE
);

CREATE TABLE articles
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    content       TEXT         NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE bank_cards
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    bank_name       VARCHAR(100) NOT NULL,
    number     VARCHAR(16)  NOT NULL unique,
    payment_system  VARCHAR(50)  NOT NULL,
    card_holder_name VARCHAR(100) NOT NULL,
    expiry_date     varchar(10)  NOT NULL,
    cvv            CHAR(3)      NOT NULL
);

CREATE TABLE bank_balance
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(16)  NOT NULL unique,
    card_balance varchar(100),
    foreign key (card_number) references bank_cards (number)
);


CREATE TABLE user_bank
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    login      VARCHAR(100) NOT NULL unique,
    password   VARCHAR(100) NOT NULL unique,
    tel        VARCHAR(20)  NOT NULL UNIQUE,
    card_number VARCHAR(16)  NOT NULL unique,
    foreign key (card_number) references bank_cards (number)
);


/*
 -----------------------------------------------------------------------------------------------------------------------------
 */


INSERT INTO company_contacts (name, tel, address)
VALUES ('Александр Александров', '8 (800) 555-35-35', 'г. Москва, ул. Ленина, д. 1'),
       ('Юлия Юлиевна', '8 (495) 123-45-67', 'г. Москва, ул. Пушкина, д. 2'),
       ('Антон Антонов', '8 (812) 765-43-21', 'г. Санкт-Петербург, наб. реки Фонтанки, д. 3'),
       ('Иван Иванов', '8 (383) 213-45-67', 'г. Новосибирск, ул. Ленинградская, д. 4');


INSERT INTO sectors (sector)
VALUES (1),
       (2),
       (3);

INSERT INTO company_tariff (per_kilowatt_per_hour)
VALUES (10);

INSERT INTO home_address (home_address, home_sector, home_area, count_residents, areas_payment, to_pay,
                          personal_account, owner_id)
VALUES ('ул. Ленина, д. 10', 1, 45.00, 4, 15000.00, 0.00, 'PA1234567890', 'HO1234'),
       ('пр. Мира, д. 20', 2, 60.00, 3, 20000.00, 0.00, 'PA0987654321', 'HO5678'),
       ('ул. Советская, д. 30', 3, 75.00, 5, 25000.00, 0.00, 'PA1122334455', 'HO9101'),
       ('пр. Космонавтов, д. 40', 1, 30.00, 2, 30000.00, 0.00, 'PA5544332211', 'HO1121');

INSERT INTO value_name_role (name_role)
VALUES ('adm'),
       ('emp');

INSERT INTO keys_for_admin (name_role, value_key)
VALUES ('adm', 'A1B2C3D4E5'),
       ('adm', 'A1B2C3D4E6'),
       ('adm', 'A1B2C3D4E7');

INSERT INTO keys_for_employees (name_role, value_key)
VALUES ('emp', 'E5D4C3B2A1'),
       ('emp', 'E5D4C3B2A2'),
       ('emp', 'E5D4C3B2A3');



INSERT INTO future_homes (home_address, home_sector, home_area, personal_account)
VALUES ('123 Future St', 1, 20.00, 'PA1234515290'),
       ('456 Tomorrow Ave', 2, 30.00, 'PA0992554321'),
       ('789 Innovation Blvd', 3, 40.00, 'PA5682399455'),
       ('101 Visionary Rd', 1, 35.00, 'PA2233445566'),
       ('202 Progress Ln', 2, 25.00, 'PA3344556677');















create table weather_record
(
    id              UUID           NOT null primary key,
    timestamp       bigint         not null,
    city_name       varchar(25)    not null,
    wmo_code        varchar(35)    not null,
    phenomenon      varchar(100)   not null,
    air_temperature numeric(20, 1) not null,
    wind_speed      numeric(20, 1) not null
);

create table value_range_rules
(
    id                      UUID                NOT NULL primary key,
    vehicle_fee_data        varchar(1048)       not null,
    min_value               numeric(20, 1)      not null,
    max_value               numeric(20,1)       not null,
    value_unit              varchar(25)         not null
);

create table phenomenon_rules
(
    id                      UUID                NOT NULL primary key,
    vehicle_fee_data        varchar(1048)       not null,
    phenomenon              varchar(100)        not null

);

create table regional_fee_rules
(
    id                      UUID                NOT NULL primary key,
    vehicle_fee_data        varchar(1048)       not null,
    city_name               varchar(100)        not null

);
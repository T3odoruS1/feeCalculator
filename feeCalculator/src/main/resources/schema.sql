create table weather_record(
                               id UUID NOT null primary key ,
                               timestamp bigint not null ,
                               city_name varchar(25) not null,
                               wmo_code varchar(35) not null,
                               phenomenon varchar(100) not null,
                               air_temperature numeric(20, 1) not null,
                               wind_speed numeric(20,1) not null
);
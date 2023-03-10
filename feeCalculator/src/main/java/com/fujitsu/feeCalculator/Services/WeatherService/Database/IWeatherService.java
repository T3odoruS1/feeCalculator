package com.fujitsu.feeCalculator.Services.WeatherService.Database;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.WeatherRecord;

import java.util.List;
import java.util.UUID;

public interface IWeatherService {

    WeatherRecord saveWeatherRecord(WeatherRecord weatherRecord);

    List<WeatherRecord> getAllWeatherRecords();

    WeatherRecord updateWeatherRecord(WeatherRecord weatherRecord, UUID weatherRecordId);

    void  deleteWeatherRecordById(UUID weatherRecordId);

    WeatherRecord getLatestWeatherRecordForCity(ECityName cityName);

    List<WeatherRecord> saveAllWeatherRecords(List<WeatherRecord> weatherRecords);

}

package com.fujitsu.feeCalculator.Services.WeatherService.Database;

import com.fujitsu.feeCalculator.DAL.Interfaces.IWeatherRepo;
import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class WeatherService implements IWeatherService{

    private final IWeatherRepo weatherRepo;

    @Autowired
    public WeatherService(IWeatherRepo weatherRepo) {
        this.weatherRepo = weatherRepo;
    }

    @Override
    public WeatherRecord saveWeatherRecord(WeatherRecord weatherRecord) {
        return weatherRepo.save(weatherRecord);
    }

    @Override
    public List<WeatherRecord> getAllWeatherRecords() {
        return (List<WeatherRecord>) weatherRepo.findAll();
    }

    @Override
    public WeatherRecord updateWeatherRecord(WeatherRecord weatherRecord, UUID weatherRecordId) {
        // TODO
        return null;
    }
    @Override
    public void deleteWeatherRecordById(UUID weatherRecordId) {
        weatherRepo.deleteById(weatherRecordId);
    }

    @Override
    public WeatherRecord getLatestWeatherRecordForCity(ECityName cityName) {
        List<WeatherRecord> records = (List<WeatherRecord>) weatherRepo.findAll();
        return records.stream()
                .sorted(Comparator.comparing(WeatherRecord::getTimestamp).reversed())
                .filter(r -> r.getCityName()
                        .equals(cityName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<WeatherRecord> saveAllWeatherRecords(List<WeatherRecord> weatherRecords) {
        return (List<WeatherRecord>) weatherRepo.saveAll(weatherRecords);
    }
}

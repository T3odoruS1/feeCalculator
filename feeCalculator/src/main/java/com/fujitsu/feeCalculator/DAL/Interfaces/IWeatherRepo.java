package com.fujitsu.feeCalculator.DAL.Interfaces;

import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IWeatherRepo extends CrudRepository<WeatherRecord, UUID> {
}

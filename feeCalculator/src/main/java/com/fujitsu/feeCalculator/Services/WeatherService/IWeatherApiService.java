package com.fujitsu.feeCalculator.Services.WeatherService;

import com.fujitsu.feeCalculator.Domain.WeatherRecord;

import java.util.List;

public interface IWeatherApiService {
    List<WeatherRecord> getLatestObservations();

}

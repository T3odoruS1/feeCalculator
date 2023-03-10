package com.fujitsu.feeCalculator.Services.WeatherService.API;

import com.fujitsu.feeCalculator.Domain.WeatherRecord;

import java.util.List;

public interface IWeatherApiService {
    List<WeatherRecord> getLatestObservations();

}

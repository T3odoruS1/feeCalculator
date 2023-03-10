package com.fujitsu.feeCalculator.BLL;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.Helpers.EnumLabelMapper;
import com.fujitsu.feeCalculator.Domain.Message;
import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import com.fujitsu.feeCalculator.Services.WeatherService.API.WeatherApiService;
import com.fujitsu.feeCalculator.Services.WeatherService.Database.WeatherService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ResponseFactory {

    private final HashMap<String, String> cityNameToStationNameMap = new HashMap<>(){{
        put("tallinn", "tallinn-harku");
        put("tartu", "Tartu-Tõravere");
        put("parnu", "pärnu");
        put("pärnu", "pärnu");
    }};
    private final FeeCalculator feeCalculator = new FeeCalculator();
    private final WeatherService weatherService;
    @Autowired
    public ResponseFactory(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public Message getMessageFromRequest(String city, String vehicle){
        EVehicleType vehicleType = EnumLabelMapper.getVehicleFromString(vehicle);
        ECityName cityName = EnumLabelMapper.getCityNameFromString(getStationName(city));
        WeatherRecord weatherRecord = getWeatherRecord(cityName, vehicleType);
        if(weatherRecord == null){
            return MessageBuilder.getMessage("Incorrect parameters provided. Acceptable parameters: city={tallinn,tartu,parnu}&vehicle={car,bike,scooter}");
        }
        Double fee = feeCalculator.calculateFee(weatherRecord, vehicleType);

        return MessageBuilder.getMessage(fee, weatherRecord);

    }

    private String getStationName(String cityName){
        return cityNameToStationNameMap.get(cityName);
    }


    private WeatherRecord getWeatherRecord(@NotNull @NotBlank ECityName city, @NotNull @NotBlank EVehicleType vehicleType) {
        if(city == null || vehicleType == null){
            return null;
        }
        WeatherRecord weatherRecord = weatherService.getLatestWeatherRecordForCity(city);
        if(weatherRecord == null){
            weatherRecord = requestLatestWeatherFromApi(city);
        }
        return weatherRecord;
    }

    private WeatherRecord requestLatestWeatherFromApi(ECityName cityName){
        WeatherApiService weatherApiService = new WeatherApiService();
        List<WeatherRecord> records = weatherApiService.getLatestObservations();
        System.out.println("requestLatestWeatherFromApi: " + records);
        return records
                .stream()
                .filter(w -> w.getCityName().equals(cityName))
                .findFirst()
                .orElse(null);
    }

}

package com.fujitsu.feeCalculator.BLL;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.Helpers.EnumLabelMapper;
import com.fujitsu.feeCalculator.REST.DataClasses.IRestResponseMessage;
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
    private final FeeCalculator feeCalculator;
    private final WeatherService weatherService;
    @Autowired
    public ResponseFactory(FeeCalculator feeCalculator, WeatherService weatherService) {
        this.feeCalculator = feeCalculator;
        this.weatherService = weatherService;
    }

    /**
     * Produces a Message object using city name and vehicle.
     * @param city city name
     * @param vehicle vehicle name
     * @return Message with delivery fee or error description
     */
    public IRestResponseMessage getMessageFromRequest(String city, String vehicle){
        EVehicleType vehicleType = EnumLabelMapper.getVehicleFromString(vehicle);
        ECityName cityName = EnumLabelMapper.getCityNameFromString(getStationName(city));
        WeatherRecord weatherRecord = getWeatherRecord(cityName, vehicleType);
        Double fee = feeCalculator.calculateFee(weatherRecord, vehicleType);

        return MessageBuilder.getMessage(fee, weatherRecord);

    }

    private String getStationName(String cityName){
        return cityNameToStationNameMap.get(cityName) != null ? cityNameToStationNameMap.get(cityName) : cityName;
    }


    private WeatherRecord getWeatherRecord(@NotNull @NotBlank ECityName city, @NotNull @NotBlank EVehicleType vehicleType) {
        if(city == null || vehicleType == null){
            return null;
        }
        WeatherRecord weatherRecord = weatherService.getLatestWeatherRecordForCity(city);
        if(weatherRecord == null){
            weatherRecord = requestLatestWeatherFromApi(city, true);

        }
        return weatherRecord;
    }

    /**
     * Sends request for weather data from api. Possible to save data into db.
     * @param cityName City name
     * @param addToDb Toggle to add recived data to db
     * @return latest weather record form api
     */
    private WeatherRecord requestLatestWeatherFromApi(ECityName cityName, boolean addToDb){
        WeatherApiService weatherApiService = new WeatherApiService();
        List<WeatherRecord> records = weatherApiService.getLatestObservations();
        if(addToDb){
            weatherService.saveAllWeatherRecords(records);
        }
        System.out.println("requestLatestWeatherFromApi: " + records);
        return records
                .stream()
                .filter(w -> w.getCityName().equals(cityName))
                .findFirst()
                .orElse(null);
    }

}

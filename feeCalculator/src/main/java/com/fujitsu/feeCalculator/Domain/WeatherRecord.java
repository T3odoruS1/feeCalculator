package com.fujitsu.feeCalculator.Domain;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Helpers.EnumLabelMapper;

import java.util.UUID;


public class WeatherRecord {

    private final UUID id;
    private Long timestamp;
    private final ECityName cityName;
    private final String WMOCode;
    private final EPhenomenonType phenomenon;
    private final Double airTemperature;
    private final Double windSpeed;

    public WeatherRecord(Long timestamp, String cityName, String WMOCode, String phenomenon, Double airTemperature, Double windSpeed) {
        this.id = UUID.randomUUID();
        this.timestamp = timestamp;
        this.cityName = EnumLabelMapper.getCityNameFromString(cityName);
        this.WMOCode = WMOCode;
        this.phenomenon = EnumLabelMapper.getPhenomenonTypeFromString(phenomenon);
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
    }

    public String toString() {
        return "WeatherRecord{" +
                "id= " + id +
                ", timestamp= " + timestamp +
                ", cityName= " + cityName +
                ", WMOCode= '" + WMOCode + '\'' +
                ", phenomenon= " + phenomenon +
                ", airTemperature= " + airTemperature +
                ", windSpeed= "  + windSpeed;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WeatherRecord other)) {
            return false;
        }
        return
//                this.id.equals(other.id) && -- disabled for unit testing
                this.timestamp.equals(other.timestamp) &&
                this.cityName == other.cityName &&
                this.WMOCode.equals(other.WMOCode) &&
                this.phenomenon == other.phenomenon &&
                this.airTemperature.equals(other.airTemperature) &&
                this.windSpeed.equals(other.windSpeed);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

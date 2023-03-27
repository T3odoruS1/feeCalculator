package com.fujitsu.feeCalculator.BLL;

import com.fujitsu.feeCalculator.Domain.*;
import com.fujitsu.feeCalculator.Domain.Enums.EValueUnit;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.Interfaces.IFixedValueBusinessRule;
import com.fujitsu.feeCalculator.Services.WeatherService.Database.BusinessRuleService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeCalculator {

    private final BusinessRuleService businessRuleService;

    @Autowired
    public FeeCalculator(BusinessRuleService businessRuleService) {
        this.businessRuleService = businessRuleService;
    }

    /**
     * Calculates delivery fee considering a location inside a weather record and a provided vehicle type.
     * @param weatherRecord weather record
     * @param vehicleType vehicle type
     * @return Calculated delivery fee if delivery is allowed or null if delivery is prohibited
     */
    public Double calculateFee(WeatherRecord weatherRecord, EVehicleType vehicleType){

        Double RBF = getBaseFeeOrDefault(weatherRecord, vehicleType);
        System.out.println(RBF + " RBF");
        Double ATEF = getAdditionalRangeValueFee(weatherRecord, vehicleType, EValueUnit.TEMPERATURE);
        System.out.println(ATEF + " ATEF");
        Double WSEF = getAdditionalRangeValueFee(weatherRecord, vehicleType, EValueUnit.WIND_SPEED);
        System.out.println(WSEF + " WSEF");
        Double WPEF = getPhenomenonFeeOrDefault(weatherRecord, vehicleType);
        System.out.println(WPEF + " WPEF");
        if(WSEF == null || WPEF == null){
            return null;
        }
        return RBF + ATEF + WSEF + WPEF;
    }

    private Double getPhenomenonFeeOrDefault(WeatherRecord weatherRecord, EVehicleType vehicleType){
        PhenomenonBusinessRule WPEFRule = businessRuleService.getPhenomenonBusinessRule(weatherRecord.getPhenomenon());

        // If business rule is not null - use it, else use default enum value
        return WPEFRule != null ? WPEFRule.getAdditionalFee(vehicleType) : weatherRecord
                .getPhenomenon()
                .getAdditionalFee(vehicleType);
    }

    private Double getBaseFeeOrDefault(WeatherRecord weatherRecord,
                                       EVehicleType vehicleType){

        RegionalBaseFeeBusinessRule RBFRule = businessRuleService.getRegionalBaseFeeBusinessRule(
                weatherRecord.getCityName()
        );
        // If business rule is not null - use it, else use default enum value
        return RBFRule != null ? RBFRule
                .getAdditionalFee(vehicleType) : weatherRecord
                .getCityName()
                .getBaseFee(vehicleType);
    }

    private Double getAdditionalRangeValueFee(WeatherRecord weatherRecord, EVehicleType vehicleType, EValueUnit valueUnit){
        ValueRangeBusinessRule rangeBusinessRule = businessRuleService
                .getValueRangeBusinessRule(getCorrectUnitsFromWeatherRecord(weatherRecord, valueUnit), valueUnit);
        // If custom rule present use it.
        if(rangeBusinessRule != null){
            System.out.println("Range rule: " + rangeBusinessRule);
            return rangeBusinessRule.getAdditionalFee(vehicleType);
        }
        // If custom rule not present use default ones.
        return valueUnit.equals(EValueUnit.WIND_SPEED) ?
                getDefaultAdditionalWindFee(weatherRecord, vehicleType) :
                getDefaultAdditionalTemperatureFee(weatherRecord, vehicleType);
    }

    private Double getCorrectUnitsFromWeatherRecord(WeatherRecord weatherRecord, EValueUnit valueUnit){
        return valueUnit.equals(EValueUnit.TEMPERATURE) ?
                weatherRecord.getAirTemperature() : weatherRecord.getWindSpeed();
    }

    private Double getDefaultAdditionalWindFee(WeatherRecord weatherRecord, EVehicleType vehicleType){
        if(vehicleType.equals(EVehicleType.BIKE)){
            if(weatherRecord.getWindSpeed() <= 20.0 && weatherRecord.getWindSpeed() > 10.0){
                return 0.5;
            }
            if(weatherRecord.getWindSpeed() > 20.0){
                return null;
            }
        }
        return 0.0;
    }

    private Double getDefaultAdditionalTemperatureFee(WeatherRecord weatherRecord, EVehicleType vehicleType){
        if(vehicleType.equals(EVehicleType.BIKE) || vehicleType.equals(EVehicleType.SCOOTER)){
            if(weatherRecord.getAirTemperature() <= -10.0){
                return 1.0;
            }
            if(weatherRecord.getAirTemperature() > -10 && weatherRecord.getAirTemperature() <= 0.0){
                return 0.5;
            }
        }
        return 0.0;
    }
}

package com.fujitsu.feeCalculator.BLL;

import com.fujitsu.feeCalculator.DAL.BusinessRuleRepo;
import com.fujitsu.feeCalculator.Domain.*;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.Interfaces.IFixedValueBusinessRule;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

@Service
public class FeeCalculator {

    private final BusinessRuleRepo businessRuleRepo = new BusinessRuleRepo();


    @Nullable
    // If delivery is possible return price, else return null
    public Double calculateFee(WeatherRecord weatherRecord, EVehicleType vehicleType){

        Double RBF = getBaseFeeOrDefault(weatherRecord, vehicleType);
        System.out.println(RBF + " RBF");
        Double ATEF = getDefaultAdditionalTemperatureFee(weatherRecord, vehicleType);
        System.out.println(ATEF + " ATEF");
        Double WSEF = getDefaultAdditionalWindFee(weatherRecord, vehicleType);
        System.out.println(WSEF + " WSEF");
        Double WPEF = getPhenomenonFeeOrDefault(weatherRecord, vehicleType);
        System.out.println(WPEF + " WPEF");
        if(WSEF == null || WPEF == null){
            return null;
        }
        return RBF + ATEF + WSEF + WPEF;
    }

    // Look in repo for data. If no data - use enums with default data
    private <T extends IFixedValueBusinessRule> Double getFeeByFixedRuleOrDefault(
            T ruleType,
            WeatherRecord weatherRecord,
            EVehicleType vehicleType){
        if(ruleType instanceof RegionalBaseFeeBusinessRule){
            return getBaseFeeOrDefault(weatherRecord, vehicleType);
        }
        if(ruleType instanceof PhenomenonBusinessRule){
            return getPhenomenonFeeOrDefault(weatherRecord, vehicleType);
        }
        return 0.0;
    }

    private Double getPhenomenonFeeOrDefault(WeatherRecord weatherRecord, EVehicleType vehicleType){
        PhenomenonBusinessRule WPEFRule = businessRuleRepo.getPhenomenonCalculationRule(weatherRecord.getPhenomenon());

        // If business rule is not null - use it, else use default enum value
        return WPEFRule != null ? WPEFRule.getAdditionalFee(vehicleType) : weatherRecord
                .getPhenomenon()
                .getAdditionalFee(vehicleType);
    }

    private Double getBaseFeeOrDefault(WeatherRecord weatherRecord,
                                       EVehicleType vehicleType){

        RegionalBaseFeeBusinessRule RBFRule = businessRuleRepo.getRegionalBaseFeeCalculationRule(
                weatherRecord.getCityName()
        );
        // If business rule is not null - use it, else use default enum value
        return RBFRule != null ? RBFRule
                .getAdditionalFee(vehicleType) : weatherRecord
                .getCityName()
                .getBaseFee(vehicleType);
    }

    private Double getDefaultAdditionalWindFee(WeatherRecord weatherRecord, EVehicleType vehicleType){
        if(vehicleType.equals(EVehicleType.BIKE)){
            if(weatherRecord.getWindSpeed() < 20.0 && weatherRecord.getWindSpeed() > 10.0){
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
            if(weatherRecord.getAirTemperature() < -10.0){
                return 1.0;
            }
            if(weatherRecord.getAirTemperature() > -10 && weatherRecord.getAirTemperature() < 0.0){
                return 0.5;
            }
        }
        return 0.0;
    }
}

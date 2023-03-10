package com.fujitsu.feeCalculator.Domain;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.Helpers.EnumLabelMapper;
import com.fujitsu.feeCalculator.Domain.Interfaces.IBusinessRule;
import com.fujitsu.feeCalculator.Domain.Interfaces.IFixedValueBusinessRule;
import jakarta.annotation.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class RegionalBaseFeeBusinessRule implements IBusinessRule, IFixedValueBusinessRule {

    private final UUID id = UUID.randomUUID();
    private final HashMap<EVehicleType, Double> vehicleFeeData;
    private final ECityName cityName;

    public RegionalBaseFeeBusinessRule(ECityName cityName, HashMap<EVehicleType, Double> vehicleFeeData) {
        this.cityName = cityName;
        this.vehicleFeeData = vehicleFeeData;
    }

    public RegionalBaseFeeBusinessRule(String cityName, HashMap<EVehicleType, Double> vehicleFeeData){
        this(EnumLabelMapper.getCityNameFromString(cityName), vehicleFeeData);
    }

    @Override
    @Nullable
    public Double getAdditionalFee(EVehicleType vehicleType) {
        return vehicleFeeData.get(vehicleType);
    }

    public ECityName getCityName() {
        return cityName;
    }
}

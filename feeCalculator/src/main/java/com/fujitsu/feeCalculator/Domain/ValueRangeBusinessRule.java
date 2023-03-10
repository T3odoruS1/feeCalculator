package com.fujitsu.feeCalculator.Domain;


import com.fujitsu.feeCalculator.Domain.Enums.EValueUnit;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.Interfaces.IBusinessRule;

import java.util.HashMap;
import java.util.UUID;


public class ValueRangeBusinessRule implements IBusinessRule {

    private final UUID id = UUID.randomUUID();
    private final HashMap<EVehicleType, Double> vehicleFeeData;
    private final double minValue;
    private final double maxValue;
    private final EValueUnit valueUnit;

    public ValueRangeBusinessRule(Double minValue,
                                  Double maxValue,
                                  EValueUnit valueUnit,
                                  HashMap<EVehicleType, Double> vehicleFeeData) {

        this.minValue = minValue;
        this.maxValue = maxValue;
        this.valueUnit = valueUnit;
        this.vehicleFeeData = vehicleFeeData;
    }

    public Double getAdditionalFee(EVehicleType vehicleType) {
        return vehicleFeeData.get(vehicleType);
    }

    public boolean checkIfValueInRange(double value){
        return minValue < value && maxValue > value;
    }

    public EValueUnit getValueUnit() {
        return valueUnit;
    }
}

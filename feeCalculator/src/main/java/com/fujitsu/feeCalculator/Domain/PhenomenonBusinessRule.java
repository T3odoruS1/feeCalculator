package com.fujitsu.feeCalculator.Domain;

import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.Helpers.EnumLabelMapper;
import com.fujitsu.feeCalculator.Domain.Interfaces.IBusinessRule;
import com.fujitsu.feeCalculator.Domain.Interfaces.IFixedValueBusinessRule;

import java.util.HashMap;
import java.util.UUID;

public class PhenomenonBusinessRule implements IBusinessRule, IFixedValueBusinessRule {

    private final UUID id = UUID.randomUUID();

    private final EPhenomenonType phenomenonType;

    private final HashMap<EVehicleType, Double> vehicleFeeData;

    PhenomenonBusinessRule(EPhenomenonType phenomenonType, HashMap<EVehicleType, Double> vehicleFeeData){
        this.phenomenonType = phenomenonType;
        this.vehicleFeeData = vehicleFeeData;
    }
    PhenomenonBusinessRule(String phenomenonName, HashMap<EVehicleType, Double> vehicleFeeData){
        this(EnumLabelMapper.getPhenomenonTypeFromString(phenomenonName), vehicleFeeData);
    }
    @Override
    public Double getAdditionalFee(EVehicleType vehicleType) {
        return vehicleFeeData.get(vehicleType);
    }

    public EPhenomenonType getPhenomenonType() {
        return phenomenonType;
    }
}

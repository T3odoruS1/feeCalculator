package com.fujitsu.feeCalculator.DAL.Interfaces;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Interfaces.IBusinessRule;
import com.fujitsu.feeCalculator.Domain.PhenomenonBusinessRule;
import com.fujitsu.feeCalculator.Domain.RegionalBaseFeeBusinessRule;
import com.fujitsu.feeCalculator.Domain.ValueRangeBusinessRule;

public interface IBusinessRuleRepo {

    void saveCalculationRule(IBusinessRule calculationRule);

    PhenomenonBusinessRule getPhenomenonCalculationRule(EPhenomenonType phenomenonType);

    RegionalBaseFeeBusinessRule getRegionalBaseFeeCalculationRule(ECityName cityName);

    ValueRangeBusinessRule getCalculationRuleForWindSpeed(Double minSpeed, Double maxSpeed);

    ValueRangeBusinessRule getCalculationRuleForTemperature(Double minTemp, Double maxTemp);
}

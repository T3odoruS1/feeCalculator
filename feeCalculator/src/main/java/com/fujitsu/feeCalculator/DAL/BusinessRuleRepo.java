package com.fujitsu.feeCalculator.DAL;

import com.fujitsu.feeCalculator.DAL.Interfaces.IBusinessRuleRepo;
import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Interfaces.IBusinessRule;
import com.fujitsu.feeCalculator.Domain.PhenomenonBusinessRule;
import com.fujitsu.feeCalculator.Domain.RegionalBaseFeeBusinessRule;
import com.fujitsu.feeCalculator.Domain.ValueRangeBusinessRule;
import org.springframework.stereotype.Repository;

@Repository("H2Business")
public class BusinessRuleRepo implements IBusinessRuleRepo {
    @Override
    public void saveCalculationRule(IBusinessRule calculationRule) {

    }

    @Override
    public PhenomenonBusinessRule getPhenomenonCalculationRule(EPhenomenonType phenomenonType) {
        return null;
    }

    @Override
    public RegionalBaseFeeBusinessRule getRegionalBaseFeeCalculationRule(ECityName cityName) {
        return null;
    }

    @Override
    public ValueRangeBusinessRule getCalculationRuleForWindSpeed(Double minSpeed, Double maxSpeed) {
        return null;
    }

    @Override
    public ValueRangeBusinessRule getCalculationRuleForTemperature(Double minTemp, Double maxTemp) {
        return null;
    }
}

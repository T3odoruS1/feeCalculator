package com.fujitsu.feeCalculator.Services.WeatherService.Database;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Enums.EValueUnit;
import com.fujitsu.feeCalculator.Domain.PhenomenonBusinessRule;
import com.fujitsu.feeCalculator.Domain.RegionalBaseFeeBusinessRule;
import com.fujitsu.feeCalculator.Domain.ValueRangeBusinessRule;

import java.util.List;
import java.util.UUID;

public interface IBusinessRuleService {

    // -----------> Phenomenon rule
    PhenomenonBusinessRule savePhenomenonBusinessRule(PhenomenonBusinessRule phenomenonBusinessRule);

    PhenomenonBusinessRule updatePhenomenonBusinessRule(
            PhenomenonBusinessRule phenomenonBusinessRule);

    void deletePhenomenonBusinessRule(UUID id);

    void deletePhenomenonBusinessRule(EPhenomenonType phenomenonType);

    PhenomenonBusinessRule getPhenomenonBusinessRule(EPhenomenonType phenomenonType);

    List<PhenomenonBusinessRule> getAllPhenomenonBusinessRules();

    // -----------> Phenomenon rule

    // -----------> Base fee rule

    RegionalBaseFeeBusinessRule saveRegionalBaseFeeBusinessRule(RegionalBaseFeeBusinessRule regionalBaseFeeBusinessRule);

    RegionalBaseFeeBusinessRule updateRegionalBaseFeeBusinessRule(
            RegionalBaseFeeBusinessRule regionalBaseFeeBusinessRule);

    void deleteRegionalBaseFeeBusinessRule(UUID id);

    void deleteRegionalBaseFeeBusinessRule(ECityName cityName);

    RegionalBaseFeeBusinessRule getRegionalBaseFeeBusinessRule(ECityName cityName);

    List<RegionalBaseFeeBusinessRule> getAllRegionalBaseFeeBusinessRules();

    // -----------> Base fee rule

    // -----------> Value range rule

    ValueRangeBusinessRule saveValueRangeBusinessRule(ValueRangeBusinessRule valueRangeBusinessRule);

    ValueRangeBusinessRule updateValueRangeBusinessRule(
            ValueRangeBusinessRule valueRangeBusinessRule
    );

    void deleteValueRangeBusinessRule(UUID id);
    void deleteValueRangeBusinessRule(Double valueInRange, EValueUnit valueUnit);

    ValueRangeBusinessRule getValueRangeBusinessRule(Double valueInRange, EValueUnit valueUnit);

    ValueRangeBusinessRule getValueRangeBusinessRule(UUID id);

    List<ValueRangeBusinessRule> getAllValueRangeBusinessRules(EValueUnit valueUnit);

    // -----------> Value range rule


}

package com.fujitsu.feeCalculator.DomainTests;

import com.fujitsu.feeCalculator.Domain.Enums.EValueUnit;
import com.fujitsu.feeCalculator.Domain.PhenomenonBusinessRule;
import com.fujitsu.feeCalculator.Domain.RegionalBaseFeeBusinessRule;
import com.fujitsu.feeCalculator.Domain.ValueRangeBusinessRule;
import com.fujitsu.feeCalculator.Services.WeatherService.Database.IBusinessRuleService;

public class Helpers {

    public void ensureEmptyDb(IBusinessRuleService service){
        for (PhenomenonBusinessRule phenomenonBusinessRule : service.getAllPhenomenonBusinessRules()) {
            service.deletePhenomenonBusinessRule(phenomenonBusinessRule.getId());
        }
        for (ValueRangeBusinessRule valueRangeBusinessRule : service.getAllValueRangeBusinessRules(EValueUnit.WIND_SPEED)) {
            service.deleteValueRangeBusinessRule(valueRangeBusinessRule.getId());
        }
        for (ValueRangeBusinessRule valueRangeBusinessRule : service.getAllValueRangeBusinessRules(EValueUnit.TEMPERATURE)) {
            service.deleteValueRangeBusinessRule(valueRangeBusinessRule.getId());
        }
        for (RegionalBaseFeeBusinessRule regionalBaseFeeBusinessRule : service.getAllRegionalBaseFeeBusinessRules()) {
            service.deleteRegionalBaseFeeBusinessRule(regionalBaseFeeBusinessRule.getId());
        }
    }
}

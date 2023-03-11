package com.fujitsu.feeCalculator.DomainTests;

import com.fujitsu.feeCalculator.BLL.FeeCalculator;
import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Enums.EValueUnit;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.PhenomenonBusinessRule;
import com.fujitsu.feeCalculator.Domain.RegionalBaseFeeBusinessRule;
import com.fujitsu.feeCalculator.Domain.ValueRangeBusinessRule;
import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import com.fujitsu.feeCalculator.Services.WeatherService.Database.BusinessRuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class feeCalculationWithCustomRulesTest {

    private final Helpers helpers = new Helpers();
    private final FeeCalculator feeCalculator;
    private final BusinessRuleService service;

    private HashMap<EVehicleType, Double> map = new HashMap<>(){{
        put(EVehicleType.CAR, 10.0);
        put(EVehicleType.BIKE, 8.0);
        put(EVehicleType.SCOOTER, 9.0);
    }};

    private HashMap<EVehicleType, Double> map2 = new HashMap<>(){{
        put(EVehicleType.CAR, 12.0);
        put(EVehicleType.BIKE, 8.0);
        put(EVehicleType.SCOOTER, 9.0);
    }};

    private final WeatherRecord weatherRecord = new WeatherRecord(
            1678187750L,
            "Tallinn-Harku",
            "26038",
            "Overcast",
            15.0,
            7.0
    );

    @Autowired
    public feeCalculationWithCustomRulesTest(FeeCalculator feeCalculator, BusinessRuleService service) {
        this.feeCalculator = feeCalculator;
        this.service = service;
    }

    @Test
    public void testCalculationWithCustomFees(){
        helpers.ensureEmptyDb(service);
        RegionalBaseFeeBusinessRule baseFeeBusinessRule = new RegionalBaseFeeBusinessRule(ECityName.TALLINN, map);
        service.saveRegionalBaseFeeBusinessRule(baseFeeBusinessRule);
        Assertions.assertEquals(10.0, feeCalculator.calculateFee(weatherRecord, EVehicleType.CAR));
        baseFeeBusinessRule.setVehicleFeeData(map2);
        service.updateRegionalBaseFeeBusinessRule(baseFeeBusinessRule);
        Assertions.assertEquals(12.0, feeCalculator.calculateFee(weatherRecord, EVehicleType.CAR));
        service.deleteRegionalBaseFeeBusinessRule(baseFeeBusinessRule.getId());
        Assertions.assertEquals(4.0, feeCalculator.calculateFee(weatherRecord, EVehicleType.CAR));
    }

    @Test
    public void testCalculationsWithCustomPhenomenonFees(){
        helpers.ensureEmptyDb(service);
        PhenomenonBusinessRule phenomenonBusinessRule = new PhenomenonBusinessRule(EPhenomenonType.OVERCAST, map);
        service.savePhenomenonBusinessRule(phenomenonBusinessRule);
        Assertions.assertEquals(14.0, feeCalculator.calculateFee(weatherRecord, EVehicleType.CAR));
        service.deletePhenomenonBusinessRule(phenomenonBusinessRule.getId());
        Assertions.assertEquals(4.0, feeCalculator.calculateFee(weatherRecord, EVehicleType.CAR));
    }

    @Test
    public void testCalculationsWithCustomWindAndTemperatureFees() throws InterruptedException {
        helpers.ensureEmptyDb(service);
        ValueRangeBusinessRule ruleWind = new ValueRangeBusinessRule(5.0, 15.0, EValueUnit.WIND_SPEED, map);
        service.saveValueRangeBusinessRule(ruleWind);
        System.out.println(service.getValueRangeBusinessRule(7.0, EValueUnit.WIND_SPEED));
        Assertions.assertEquals(14.0, feeCalculator.calculateFee(weatherRecord, EVehicleType.CAR));
        ValueRangeBusinessRule ruleTemp = new ValueRangeBusinessRule(12.0, 17.0, EValueUnit.TEMPERATURE, map2);
        service.saveValueRangeBusinessRule(ruleTemp);
        System.out.println(service.getValueRangeBusinessRule(15.0, EValueUnit.TEMPERATURE));
        Assertions.assertEquals(26.0, feeCalculator.calculateFee(weatherRecord, EVehicleType.CAR));
        service.deleteValueRangeBusinessRule(ruleWind.getId());
        Assertions.assertEquals(16.0, feeCalculator.calculateFee(weatherRecord, EVehicleType.CAR));
        service.deleteValueRangeBusinessRule(ruleTemp.getId());
        Assertions.assertEquals(4.0, feeCalculator.calculateFee(weatherRecord, EVehicleType.CAR));
    }

    @Test
    public void testCalculationsMinInclusiveMaxExclusive(){
        helpers.ensureEmptyDb(service);
        ValueRangeBusinessRule ruleWind = new ValueRangeBusinessRule(7.0, 15.0, EValueUnit.WIND_SPEED, map);
        service.saveValueRangeBusinessRule(ruleWind);
        Assertions.assertEquals(14.0, feeCalculator.calculateFee(weatherRecord, EVehicleType.CAR));
        ruleWind.setMinValue(0.1);
        ruleWind.setMaxValue(7.0);
        service.updateValueRangeBusinessRule(ruleWind);
        Assertions.assertEquals(4.0, feeCalculator.calculateFee(weatherRecord, EVehicleType.CAR));

    }




}

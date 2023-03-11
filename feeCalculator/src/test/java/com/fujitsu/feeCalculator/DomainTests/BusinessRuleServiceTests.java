package com.fujitsu.feeCalculator.DomainTests;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Enums.EValueUnit;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.PhenomenonBusinessRule;
import com.fujitsu.feeCalculator.Domain.RegionalBaseFeeBusinessRule;
import com.fujitsu.feeCalculator.Domain.ValueRangeBusinessRule;
import com.fujitsu.feeCalculator.Exceptions.BusinessRuleAlreadyImplementedException;
import com.fujitsu.feeCalculator.Services.WeatherService.Database.BusinessRuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class BusinessRuleServiceTests {

    private final BusinessRuleService service;
    private final Helpers helpers = new Helpers();

    private HashMap<EVehicleType, Double>  map = new HashMap<>(){{
        put(EVehicleType.CAR, 5.5);
        put(EVehicleType.BIKE, 2.0);
        put(EVehicleType.SCOOTER, 1.5);
    }};
    private HashMap<EVehicleType, Double>  map2 = new HashMap<>(){{
        put(EVehicleType.CAR, 5.5);
        put(EVehicleType.BIKE, 2.0);
        put(EVehicleType.SCOOTER, 10.5);
    }};

    @Autowired
    public BusinessRuleServiceTests(BusinessRuleService businessRuleService) {
        this.service = businessRuleService;
    }

    @Test
    public void testServiceCanSaveAndDeletePhenomenon(){
        helpers.ensureEmptyDb(service);
        PhenomenonBusinessRule phenomenonBusinessRule = new PhenomenonBusinessRule(EPhenomenonType.GLAZE, map);
        service.savePhenomenonBusinessRule(phenomenonBusinessRule);
        PhenomenonBusinessRule fromDb = service.getPhenomenonBusinessRule(EPhenomenonType.GLAZE);
        Assertions.assertEquals(phenomenonBusinessRule, fromDb);
        service.deletePhenomenonBusinessRule(phenomenonBusinessRule.getId());
        Assertions.assertNull(service.getPhenomenonBusinessRule(EPhenomenonType.GLAZE));
    }

    @Test
    public void testSeviceCanAddAndDeleteBaseFee(){
        helpers.ensureEmptyDb(service);
        RegionalBaseFeeBusinessRule rule = new RegionalBaseFeeBusinessRule(ECityName.TALLINN, map);
        service.saveRegionalBaseFeeBusinessRule(rule);
        Assertions.assertEquals(rule, service.getRegionalBaseFeeBusinessRule(ECityName.TALLINN));
        service.deleteRegionalBaseFeeBusinessRule(ECityName.TALLINN);
        Assertions.assertNull(service.getRegionalBaseFeeBusinessRule(ECityName.TALLINN));
        service.saveRegionalBaseFeeBusinessRule(rule);
        service.deleteRegionalBaseFeeBusinessRule(rule.getId());
        Assertions.assertNull(service.getRegionalBaseFeeBusinessRule(ECityName.TALLINN));
    }

    @Test
    public void testSeviceCanAddAndDeleteValueRange(){
        helpers.ensureEmptyDb(service);

        ValueRangeBusinessRule rule = new ValueRangeBusinessRule(-9.0, 1.0, EValueUnit.TEMPERATURE, map);
        service.saveValueRangeBusinessRule(rule);
        Assertions.assertEquals(rule, service.getValueRangeBusinessRule(-5.0, EValueUnit.TEMPERATURE));
        service.deleteValueRangeBusinessRule(rule.getId());
        Assertions.assertNull(service.getValueRangeBusinessRule(-5.0, EValueUnit.TEMPERATURE));
    }


    @Test
    public void testServiceThrowsWhenPhenomenonOverlapping(){
        helpers.ensureEmptyDb(service);

        PhenomenonBusinessRule phenomenonBusinessRule = new PhenomenonBusinessRule(EPhenomenonType.GLAZE, map);
        PhenomenonBusinessRule phenomenonBusinessRule2 = new PhenomenonBusinessRule(EPhenomenonType.GLAZE, map2);
        service.savePhenomenonBusinessRule(phenomenonBusinessRule);
        Assertions.assertThrows(
                BusinessRuleAlreadyImplementedException.class,
                () -> service.savePhenomenonBusinessRule(phenomenonBusinessRule2));
        service.deletePhenomenonBusinessRule(phenomenonBusinessRule.getId());

    }

    @Test
    public void testServiceThrowsWhenBaseOverlapping(){
        helpers.ensureEmptyDb(service);

        RegionalBaseFeeBusinessRule rule = new RegionalBaseFeeBusinessRule(ECityName.TALLINN, map);
        RegionalBaseFeeBusinessRule rule2 = new RegionalBaseFeeBusinessRule(ECityName.TALLINN, map2);
        service.saveRegionalBaseFeeBusinessRule(rule);
        Assertions.assertThrows(BusinessRuleAlreadyImplementedException.class,
                () -> service.saveRegionalBaseFeeBusinessRule(rule2));
        service.deleteRegionalBaseFeeBusinessRule(rule.getId());
        Assertions.assertNull(service.getRegionalBaseFeeBusinessRule(ECityName.TALLINN));

    }

    @Test
    public void testServiceThrowsWhenRangeOverlapping(){
        helpers.ensureEmptyDb(service);

        ValueRangeBusinessRule rule = new ValueRangeBusinessRule(-9.0, 1.0, EValueUnit.TEMPERATURE, map);
        ValueRangeBusinessRule rule2 = new ValueRangeBusinessRule(1.0, 2.0, EValueUnit.TEMPERATURE, map);
        ValueRangeBusinessRule rule3 = new ValueRangeBusinessRule(-15.0, -8.0, EValueUnit.TEMPERATURE, map);
        ValueRangeBusinessRule rule4 = new ValueRangeBusinessRule(-900.0, -9.0, EValueUnit.TEMPERATURE, map);
        ValueRangeBusinessRule rule5 = new ValueRangeBusinessRule(1.0, 2.0, EValueUnit.WIND_SPEED, map);

        service.saveValueRangeBusinessRule(rule);
        service.saveValueRangeBusinessRule(rule2);
        Assertions.assertThrows(BusinessRuleAlreadyImplementedException.class,
                () -> service.saveValueRangeBusinessRule(rule3));
        service.saveValueRangeBusinessRule(rule4);
        service.saveValueRangeBusinessRule(rule5);
        service.deleteValueRangeBusinessRule(rule.getId());
        service.deleteValueRangeBusinessRule(rule2.getId());
        service.deleteValueRangeBusinessRule(rule4.getId());
        service.deleteValueRangeBusinessRule(rule5.getId());

    }

    @Test
    public void testServiceUpdatePhenomenon(){
        helpers.ensureEmptyDb(service);

        PhenomenonBusinessRule phenomenonBusinessRule = new PhenomenonBusinessRule(EPhenomenonType.GLAZE, map);
        service.savePhenomenonBusinessRule(phenomenonBusinessRule);
        phenomenonBusinessRule = service.getPhenomenonBusinessRule(EPhenomenonType.GLAZE);
        phenomenonBusinessRule.setVehicleFeeData(map2);
        service.updatePhenomenonBusinessRule(phenomenonBusinessRule);
        Assertions.assertEquals(phenomenonBusinessRule, service.getPhenomenonBusinessRule(EPhenomenonType.GLAZE));
        service.deletePhenomenonBusinessRule(EPhenomenonType.GLAZE);
    }

    @Test
    public void testServiceUpdateBaseFee(){
        helpers.ensureEmptyDb(service);

        RegionalBaseFeeBusinessRule rule = new RegionalBaseFeeBusinessRule(ECityName.TALLINN, map);
        service.saveRegionalBaseFeeBusinessRule(rule);
        rule = service.getRegionalBaseFeeBusinessRule(ECityName.TALLINN);
        rule.setVehicleFeeData(map2);
        service.updateRegionalBaseFeeBusinessRule(rule);
        Assertions.assertEquals(rule, service.getRegionalBaseFeeBusinessRule(ECityName.TALLINN));
        service.deleteRegionalBaseFeeBusinessRule(rule.getId());
        Assertions.assertNull(service.getRegionalBaseFeeBusinessRule(ECityName.TALLINN));

    }

    @Test
    public void testServiceRangeUpdate(){
        helpers.ensureEmptyDb(service);

        ValueRangeBusinessRule rule = new ValueRangeBusinessRule(-9.0, 1.0, EValueUnit.TEMPERATURE, map);
        service.saveValueRangeBusinessRule(rule);
        rule.setMinValue(-15);
        service.updateValueRangeBusinessRule(rule);
        Assertions.assertEquals(rule, service.getValueRangeBusinessRule(rule.getId()));
        service.deleteValueRangeBusinessRule(rule.getId());
    }

    @Test
    public void cannotUpdateRangeToOverlap(){
        helpers.ensureEmptyDb(service);

        ValueRangeBusinessRule rule = new ValueRangeBusinessRule(-9.0, 1.0, EValueUnit.TEMPERATURE, map);
        ValueRangeBusinessRule rule2 = new ValueRangeBusinessRule(1.0, 5.0, EValueUnit.TEMPERATURE, map);
        service.saveValueRangeBusinessRule(rule);
        service.saveValueRangeBusinessRule(rule2);
        rule.setMaxValue(2.0);
        Assertions.assertThrows(BusinessRuleAlreadyImplementedException.class,
                () -> service.updateValueRangeBusinessRule(rule));
        service.deleteValueRangeBusinessRule(rule.getId());
        service.deleteValueRangeBusinessRule(rule2.getId());
    }

    @Test
    public void cannotUpdateUsingObjectsWithOtherId(){
        helpers.ensureEmptyDb(service);

        ValueRangeBusinessRule rule = new ValueRangeBusinessRule(-9.0, 1.0, EValueUnit.TEMPERATURE, map);
        ValueRangeBusinessRule rule2 = new ValueRangeBusinessRule(-9.0, 1.0, EValueUnit.TEMPERATURE, map2);
        service.saveValueRangeBusinessRule(rule);
        Assertions.assertThrows(RuntimeException.class,
                () -> service.updateValueRangeBusinessRule(rule2));
        service.deleteValueRangeBusinessRule(rule.getId());
        RegionalBaseFeeBusinessRule rule3 = new RegionalBaseFeeBusinessRule(ECityName.TALLINN, map);
        RegionalBaseFeeBusinessRule rule4 = new RegionalBaseFeeBusinessRule(ECityName.TALLINN, map2);
        service.saveRegionalBaseFeeBusinessRule(rule3);
        Assertions.assertThrows(RuntimeException.class,
                () -> service.updateRegionalBaseFeeBusinessRule(rule4));

        service.deleteRegionalBaseFeeBusinessRule(rule3.getId());

        PhenomenonBusinessRule phenomenonBusinessRule = new PhenomenonBusinessRule(EPhenomenonType.GLAZE, map);
        PhenomenonBusinessRule phenomenonBusinessRule2 = new PhenomenonBusinessRule(EPhenomenonType.GLAZE, map2);

        service.savePhenomenonBusinessRule(phenomenonBusinessRule);
        Assertions.assertThrows(RuntimeException.class,
                () -> service.updatePhenomenonBusinessRule(phenomenonBusinessRule2));
        service.deletePhenomenonBusinessRule(phenomenonBusinessRule.getId());
    }

    @Test
    public void testRangeValuesQueries(){
        helpers.ensureEmptyDb(service);

        ValueRangeBusinessRule ruleWind = new ValueRangeBusinessRule(5.0, 15.0, EValueUnit.WIND_SPEED, map);
        ValueRangeBusinessRule ruleTemp = new ValueRangeBusinessRule(12.0, 17.0, EValueUnit.TEMPERATURE, map2);

        service.saveValueRangeBusinessRule(ruleWind);
        service.saveValueRangeBusinessRule(ruleTemp);
        Assertions.assertEquals(ruleWind, service.getValueRangeBusinessRule(7.0, EValueUnit.WIND_SPEED));
        Assertions.assertEquals(ruleTemp, service.getValueRangeBusinessRule(15.0, EValueUnit.TEMPERATURE));

    }



}

package com.fujitsu.feeCalculator.REST;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Enums.EValueUnit;
import com.fujitsu.feeCalculator.Domain.Helpers.EnumLabelMapper;
import com.fujitsu.feeCalculator.Domain.PhenomenonBusinessRule;
import com.fujitsu.feeCalculator.Domain.RegionalBaseFeeBusinessRule;
import com.fujitsu.feeCalculator.Domain.ValueRangeBusinessRule;
import com.fujitsu.feeCalculator.Services.WeatherService.Database.BusinessRuleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/delivery_fee/config/")
@RestController
public class ConfigurationController {

    private final BusinessRuleService service;

    @Autowired
    public ConfigurationController(BusinessRuleService service) {
        this.service = service;
    }

    @GetMapping(path="phenomenon")
    public List<PhenomenonBusinessRule> getPhenomenonBusinessRules(){
        return service.getAllPhenomenonBusinessRules();
    }

    @PostMapping(path = "phenomenon")
    public PhenomenonBusinessRule addPhenomenonBusinessRule(@Valid @NotNull @RequestBody PhenomenonBusinessRule rule){
        return service.savePhenomenonBusinessRule(rule);
    }

    @PutMapping(path = "phenomenon")
    public void updatePhenomenonBusinessRule(@Valid @NotNull @RequestBody PhenomenonBusinessRule rule){
        service.updatePhenomenonBusinessRule(rule);
    }

    @DeleteMapping(path = "phenomenon/{type}")
    public void deletePhenomenonBusinessRuleByType(@PathVariable("type") EPhenomenonType type){
        service.deletePhenomenonBusinessRule(type);
    }

    @GetMapping(path = "regional")
    public List<RegionalBaseFeeBusinessRule> getRegionalFeeRules(){
        return service.getAllRegionalBaseFeeBusinessRules();
    }

    @PostMapping(path = "regional")
    public RegionalBaseFeeBusinessRule addRegionalFeeRule(
            @Valid @NotNull @RequestBody RegionalBaseFeeBusinessRule rule
    ){
        return service.saveRegionalBaseFeeBusinessRule(rule);
    }

    @PutMapping(path = "regional")
    public void updateRegionalFeeRule(@Valid @NotNull @RequestBody RegionalBaseFeeBusinessRule rule){
        service.updateRegionalBaseFeeBusinessRule(rule);
    }

    @DeleteMapping("regional/{city}")
    public void deleteRegionalFeeRule(@PathVariable("city") ECityName city){
        service.deleteRegionalBaseFeeBusinessRule(city);
    }

    @GetMapping(path = "value_range/{type}")
    public List<ValueRangeBusinessRule> getTemperatureBusinessRules(@PathVariable("type") String type){
        EValueUnit unit = EnumLabelMapper.getValueUnitFromString(type);
        return service.getAllValueRangeBusinessRules(unit);
    }

    @PostMapping(path = "value_range")
    public ValueRangeBusinessRule addValueRangeBusinessRule(
             @Valid @NotNull @RequestBody ValueRangeBusinessRule rule
    ){
        return service.saveValueRangeBusinessRule(rule);
    }

    @PutMapping(path = "value_range")
    public void updateValueRangeRule(
            @NotNull @RequestBody ValueRangeBusinessRule rule
    ){

        service.updateValueRangeBusinessRule(rule);
    }

    @DeleteMapping(path = "value_range/{id}")
    public void deleteValueRangeRuleById(
            @PathVariable("id") UUID id
    ){
        service.deleteValueRangeBusinessRule(id);
    }

}

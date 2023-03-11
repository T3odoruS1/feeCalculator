package com.fujitsu.feeCalculator.Services.WeatherService.Database;

import com.fujitsu.feeCalculator.DAL.Interfaces.IBaseFeeRepo;
import com.fujitsu.feeCalculator.DAL.Interfaces.IPhenomenonRepo;
import com.fujitsu.feeCalculator.DAL.Interfaces.IValueRangeRuleRepo;
import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Enums.EValueUnit;
import com.fujitsu.feeCalculator.Domain.PhenomenonBusinessRule;
import com.fujitsu.feeCalculator.Domain.RegionalBaseFeeBusinessRule;
import com.fujitsu.feeCalculator.Domain.ValueRangeBusinessRule;
import com.fujitsu.feeCalculator.Exceptions.BusinessRuleAlreadyImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BusinessRuleService implements IBusinessRuleService{

    private final IPhenomenonRepo phenomenonRepo;
    private final IBaseFeeRepo baseFeeRepo;
    private final IValueRangeRuleRepo valueRangeRepo;

    @Autowired
    public BusinessRuleService(
            IPhenomenonRepo phenomenonRepo,
            IBaseFeeRepo baseFeeRepo,
            IValueRangeRuleRepo valueRangeRepo) {
        this.phenomenonRepo = phenomenonRepo;
        this.baseFeeRepo = baseFeeRepo;
        this.valueRangeRepo = valueRangeRepo;
    }

    @Override
    public PhenomenonBusinessRule savePhenomenonBusinessRule(PhenomenonBusinessRule phenomenonBusinessRule) {
        if(!canSavePhenomenonBusinessRule(phenomenonBusinessRule))
            throw new BusinessRuleAlreadyImplementedException(phenomenonBusinessRule);
        return phenomenonRepo.save(phenomenonBusinessRule);
    }

    @Override
    public PhenomenonBusinessRule updatePhenomenonBusinessRule(PhenomenonBusinessRule phenomenonBusinessRule) {
        PhenomenonBusinessRule ruleToUpdate = getPhenomenonBusinessRule(phenomenonBusinessRule.getPhenomenonType());
        if(!ruleToUpdate.getId().equals( phenomenonBusinessRule.getId())){
            throw new RuntimeException("Different business rules provided for updating(id not matching)");
        }
        ruleToUpdate.setVehicleFeeData(phenomenonBusinessRule.getVehicleFeeData());
        phenomenonRepo.deleteById(phenomenonBusinessRule.getId());
        phenomenonRepo.save(phenomenonBusinessRule);
        return null;
    }

    @Override
    public void deletePhenomenonBusinessRule(UUID id) {
        phenomenonRepo.deleteById(id);
    }

    @Override
    public void deletePhenomenonBusinessRule(EPhenomenonType phenomenonType) {
        PhenomenonBusinessRule result = getPhenomenonBusinessRule(phenomenonType);
        if(result == null) return;
        deletePhenomenonBusinessRule(result.getId());
    }

    @Override
    public PhenomenonBusinessRule getPhenomenonBusinessRule(EPhenomenonType phenomenonType) {
        return getAllPhenomenonBusinessRules()
                .stream()
                .filter(r -> r.getPhenomenonType().equals(phenomenonType))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<PhenomenonBusinessRule> getAllPhenomenonBusinessRules() {
        return (List<PhenomenonBusinessRule>) phenomenonRepo.findAll();
    }

    @Override
    public RegionalBaseFeeBusinessRule saveRegionalBaseFeeBusinessRule(RegionalBaseFeeBusinessRule regionalBaseFeeBusinessRule) {
        if(!canSaveRegionalBaseFee(regionalBaseFeeBusinessRule.getCityName())){
            throw new BusinessRuleAlreadyImplementedException(regionalBaseFeeBusinessRule);
        }
        return baseFeeRepo.save(regionalBaseFeeBusinessRule);
    }

    @Override
    public RegionalBaseFeeBusinessRule updateRegionalBaseFeeBusinessRule(RegionalBaseFeeBusinessRule regionalBaseFeeBusinessRule) {
        RegionalBaseFeeBusinessRule ruleToUpdate = getRegionalBaseFeeBusinessRule(regionalBaseFeeBusinessRule.getCityName());
        if(ruleToUpdate == null){
            throw new RuntimeException("Rule that is being updated does not exist in the database.");
        }
        if(!ruleToUpdate.getId().equals(regionalBaseFeeBusinessRule.getId())){
            throw new RuntimeException("Different business rules provided for updating(id not matching) : "
                    + ruleToUpdate.getId().toString()  + " and " +
                    regionalBaseFeeBusinessRule.getId().toString());
        }
        ruleToUpdate.setVehicleFeeData(regionalBaseFeeBusinessRule.getDeserializedVehicleFeeData());
        baseFeeRepo.deleteById(regionalBaseFeeBusinessRule.getId());
        return baseFeeRepo.save(ruleToUpdate);
    }

    @Override
    public void deleteRegionalBaseFeeBusinessRule(UUID id) {
        baseFeeRepo.deleteById(id);
    }

    @Override
    public void deleteRegionalBaseFeeBusinessRule(ECityName cityName) {
        RegionalBaseFeeBusinessRule rbf = getRegionalBaseFeeBusinessRule(cityName);
        if(rbf == null) return;
        baseFeeRepo.deleteById(rbf.getId());
    }

    @Override
    public RegionalBaseFeeBusinessRule getRegionalBaseFeeBusinessRule(ECityName cityName) {
        return getAllRegionalBaseFeeBusinessRules()
                .stream()
                .filter(r -> r.getCityName().equals(cityName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<RegionalBaseFeeBusinessRule> getAllRegionalBaseFeeBusinessRules() {
        return (List<RegionalBaseFeeBusinessRule>) baseFeeRepo.findAll();
    }

    @Override
    public ValueRangeBusinessRule saveValueRangeBusinessRule(ValueRangeBusinessRule valueRangeBusinessRule) {
        if(!canSaveValueInRangeBusinessRule(valueRangeBusinessRule)){
            throw new BusinessRuleAlreadyImplementedException(valueRangeBusinessRule);
        }
        valueRangeRepo.save(valueRangeBusinessRule);
        return null;
    }

    @Override
    public ValueRangeBusinessRule updateValueRangeBusinessRule(ValueRangeBusinessRule valueRangeBusinessRule) {
        ValueRangeBusinessRule ruleFromDb = getValueRangeBusinessRule(valueRangeBusinessRule.getId());
        if(ruleFromDb == null){
            throw new RuntimeException("Rule that is being updated does not exist in the database.");
        }
        if(!ruleFromDb.getId().equals(valueRangeBusinessRule.getId())){
            throw new RuntimeException("Different business rules provided for updating(id not matching)");
        }
        deleteValueRangeBusinessRule(ruleFromDb.getId());
        if(!canSaveValueInRangeBusinessRule(valueRangeBusinessRule)){
            saveValueRangeBusinessRule(ruleFromDb);
            throw new BusinessRuleAlreadyImplementedException(valueRangeBusinessRule);
        }

        ruleFromDb.setMaxValue(valueRangeBusinessRule.getMaxValue());
        ruleFromDb.setMinValue(valueRangeBusinessRule.getMinValue());
        ruleFromDb.setVehicleFeeData(valueRangeBusinessRule.getDeserializedVehicleFeeData());
        valueRangeRepo.save(ruleFromDb);
        return ruleFromDb;
    }

    @Override
    public void deleteValueRangeBusinessRule(UUID id) {
        valueRangeRepo.deleteById(id);
    }

    @Override
    public void deleteValueRangeBusinessRule(Double valueInRange, EValueUnit valueUnit) {
        ValueRangeBusinessRule rule = getValueRangeBusinessRule(valueInRange, valueUnit);
        valueRangeRepo.deleteById(rule.getId());
    }

    @Override
    public ValueRangeBusinessRule getValueRangeBusinessRule(Double valueInRange, EValueUnit valueUnit) {
        return getAllValueRangeBusinessRules(valueUnit)
                .stream()
                .filter(r -> r.checkIfValueInRangeMinInclusive(valueInRange))
                .filter(r -> r.getValueUnit().equals(valueUnit))
                .findFirst()
                .orElse(null);
    }

    @Override
    public ValueRangeBusinessRule getValueRangeBusinessRule(UUID id) {
        return valueRangeRepo.findById(id).orElse(null);
    }

    @Override
    public List<ValueRangeBusinessRule> getAllValueRangeBusinessRules(EValueUnit valueUnit) {
        List<ValueRangeBusinessRule> results = (List<ValueRangeBusinessRule>) valueRangeRepo.findAll();
        return results
                .stream()
                .filter(r -> r.getValueUnit().equals(valueUnit))
                .toList();
    }

    private boolean canSaveValueInRangeBusinessRule(ValueRangeBusinessRule businessRule){
        List<ValueRangeBusinessRule> rules = getAllValueRangeBusinessRules(businessRule.getValueUnit());
        for (ValueRangeBusinessRule rule: rules) {
            if(
                    businessRule.checkIfValueInRange(rule.getMaxValue()) ||
                    businessRule.checkIfValueInRange(rule.getMinValue()))
            {
                return false;
            }
        }
        return true;
    }

    private boolean canSavePhenomenonBusinessRule(PhenomenonBusinessRule businessRule){
        PhenomenonBusinessRule ruleFromDb = getPhenomenonBusinessRule(businessRule.getPhenomenonType());
        return ruleFromDb == null;
    }


    private boolean canSaveRegionalBaseFee(ECityName cityName){
        return getAllRegionalBaseFeeBusinessRules()
                .stream()
                .filter(f -> f.getCityName().equals(cityName))
                .findFirst()
                .orElse(null) == null;
    }



}

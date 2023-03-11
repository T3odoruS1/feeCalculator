package com.fujitsu.feeCalculator.DAL.Interfaces;

import com.fujitsu.feeCalculator.Domain.ValueRangeBusinessRule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IValueRangeRuleRepo extends CrudRepository<ValueRangeBusinessRule, UUID> {

}

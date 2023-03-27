package com.fujitsu.feeCalculator.Exceptions;

import com.fujitsu.feeCalculator.Domain.Interfaces.IBusinessRule;

/**
 * Thrown when trying to save business rule that is overlapping with other custom business rule. Default rule is OK.
 */
public class BusinessRuleAlreadyImplementedException extends RuntimeException{

    public BusinessRuleAlreadyImplementedException(IBusinessRule businessRule){
        super(
                "Business rule already implemented: "
                + businessRule.toString() +
                " Before saving this rule, you should delete/update rule that is overlapping with this one");
    }

}

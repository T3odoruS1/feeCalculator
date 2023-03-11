package com.fujitsu.feeCalculator.Exceptions;

import com.fujitsu.feeCalculator.Domain.Interfaces.IBusinessRule;

public class BusinessRuleAlreadyImplementedException extends RuntimeException{

    public BusinessRuleAlreadyImplementedException(IBusinessRule businessRule){
        super(
                "Business rule already implemented: "
                + businessRule.toString() +
                " Before saving this rule, you should delete/update rule that is overlapping with this one");
    }

}

package com.fujitsu.feeCalculator.Exceptions;

public class InvalidValueRangeBusinessRuleType extends RuntimeException{
    public InvalidValueRangeBusinessRuleType(){
        super("Tried to save a wrong business rule type (wind speed/temperature) through wrong endpoint.");
    }
}

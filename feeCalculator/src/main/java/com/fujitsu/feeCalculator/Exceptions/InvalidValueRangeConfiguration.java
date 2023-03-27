package com.fujitsu.feeCalculator.Exceptions;


// Used when creating/updating ValueRangeBusinessRule and min/max values are not correct
public class InvalidValueRangeConfiguration extends RuntimeException{

    public InvalidValueRangeConfiguration(){
        super("Incorrect value range business rule configuration. Minimum was bigger than maximum or wind speed is negative");
    }
}

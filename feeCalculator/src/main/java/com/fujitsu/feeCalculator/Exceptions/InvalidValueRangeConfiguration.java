package com.fujitsu.feeCalculator.Exceptions;

public class InvalidValueRangeConfiguration extends RuntimeException{

    public InvalidValueRangeConfiguration(){
        super("Incorrect value range business rule configuration. Minimum was bigger than maximum or wind speed is negative");
    }
}

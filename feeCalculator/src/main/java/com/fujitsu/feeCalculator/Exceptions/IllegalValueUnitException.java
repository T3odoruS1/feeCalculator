package com.fujitsu.feeCalculator.Exceptions;

public class IllegalValueUnitException extends RuntimeException{
    public IllegalValueUnitException(String label){
        super("Provided illegal value unit: "+ label);
    }
}

package com.fujitsu.feeCalculator.Exceptions;

/**
 * Used when value unit couldn't be decoded from string. Thrown when invalid value unit provided in business rule REST service.
  */
public class IllegalValueUnitException extends RuntimeException{
    public IllegalValueUnitException(String label){
        super("Provided illegal value unit: "+ label);
    }
}

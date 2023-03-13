package com.fujitsu.feeCalculator.Exceptions;

public class PhenomenonTypeNotFound extends RuntimeException{
    public PhenomenonTypeNotFound(String label){
        super("Phenomenon type not found: " + label);
    }
}

package com.fujitsu.feeCalculator.Exceptions;

// Deprecated. Now when phenomenon is not found form string NO_DATA enum from EPhenomenonType is used.
@Deprecated
public class PhenomenonTypeNotFound extends RuntimeException{
    public PhenomenonTypeNotFound(String label){
        super("Phenomenon type not found: " + label);
    }
}

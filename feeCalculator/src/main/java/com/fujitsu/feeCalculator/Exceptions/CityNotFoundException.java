package com.fujitsu.feeCalculator.Exceptions;

public class CityNotFoundException extends RuntimeException{

    public CityNotFoundException(String providedCity){
        super("City not found: Did not manage to find a city with the parameter that was provided: " + providedCity);
    }
}

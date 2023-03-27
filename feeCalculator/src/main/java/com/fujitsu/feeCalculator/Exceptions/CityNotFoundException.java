package com.fujitsu.feeCalculator.Exceptions;

/**
  * Used when provided incorrect city name string and ECityName enum couldn't be constructed.
 */
public class CityNotFoundException extends RuntimeException{

    public CityNotFoundException(String providedCity){
        super("City not found: Did not manage to find a city with the parameter that was provided: " + providedCity);
    }
}

package com.fujitsu.feeCalculator.Exceptions;


/**
 * Used when invalid vehicle string was provided and couldn't be decoded.
 */
public class VehicleNotFoundException extends RuntimeException{

    public VehicleNotFoundException(String providedVehicle){
        super("Vehicle not found: Did not manage to find a vehicle with the parameter that was provided: " + providedVehicle);
    }
}

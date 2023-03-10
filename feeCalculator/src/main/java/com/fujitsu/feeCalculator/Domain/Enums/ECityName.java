package com.fujitsu.feeCalculator.Domain.Enums;

import java.util.HashMap;

public enum ECityName{
    TALLINN("Tallinn-Harku", new HashMap<>() {{
        put(EVehicleType.CAR, 4.0);
        put(EVehicleType.SCOOTER, 3.5);
        put(EVehicleType.BIKE, 3.0);
    }} ),
    TARTU("Tartu-Tõravere", new HashMap<>() {{
        put(EVehicleType.CAR, 3.5);
        put(EVehicleType.SCOOTER, 3.0);
        put(EVehicleType.BIKE, 2.5);
    }} ),
    PARNU("Pärnu", new HashMap<>() {{
        put(EVehicleType.CAR, 3.0);
        put(EVehicleType.SCOOTER, 2.5);
        put(EVehicleType.BIKE, 2.0);
    }} );


    public final String label;
    public final HashMap<EVehicleType, Double> defaultBaseFee;


    ECityName(String label, HashMap<EVehicleType, Double> defaultBaseFee) {
        this.label = label;
        this.defaultBaseFee = defaultBaseFee;
    }

    public String getLabel(){
        return this.label;
    }

    public double getBaseFee(EVehicleType vehicleType) {
        return this.defaultBaseFee.get(vehicleType);
    }
}

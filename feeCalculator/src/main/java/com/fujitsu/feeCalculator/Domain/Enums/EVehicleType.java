package com.fujitsu.feeCalculator.Domain.Enums;

public enum EVehicleType {
    CAR("car"),
    BIKE("bike"),
    SCOOTER("scooter");

    public String label;

    EVehicleType(String label){
        this.label = label;
    }

}

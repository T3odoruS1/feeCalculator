package com.fujitsu.feeCalculator.Domain.Enums;

public enum EValueUnit {
    TEMPERATURE("temperature"),
    WIND_SPEED("wind");

    public final String label;

    EValueUnit(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

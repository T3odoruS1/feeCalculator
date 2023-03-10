package com.fujitsu.feeCalculator.Domain.Enums;

public enum EValueUnit {
    TEMPERATURE("tmp"),
    WIND_SPEED("wnd");

    public final String label;

    EValueUnit(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

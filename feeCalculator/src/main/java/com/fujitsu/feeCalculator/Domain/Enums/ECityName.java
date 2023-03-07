package com.fujitsu.feeCalculator.Domain.Enums;

public enum ECityName{
    TALLINN("Tallinn-Harku"),
    TARTU("Tartu-Tõravere"),
    PARNU("Pärnu");

    public final String label;

    ECityName(String label) {
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

}

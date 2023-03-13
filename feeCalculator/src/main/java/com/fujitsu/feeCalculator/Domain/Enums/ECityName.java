package com.fujitsu.feeCalculator.Domain.Enums;

import com.fujitsu.feeCalculator.Domain.DeliveryFeeHashmapStore;

import java.util.HashMap;

public enum ECityName{
    TALLINN("Tallinn-Harku", DeliveryFeeHashmapStore.tallinnDefault),
    TARTU("Tartu-Tõravere", DeliveryFeeHashmapStore.tartuDefault),
    PARNU("Pärnu", DeliveryFeeHashmapStore.parnuDefault);

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

package com.fujitsu.feeCalculator.Domain;

import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;

import java.util.HashMap;

/**
 * Class for storing default delivery fee rules.
 */
public class DeliveryFeeHashmapStore {
    public static final HashMap<EVehicleType, Double> tallinnDefault = new HashMap<>(){{
        put(EVehicleType.CAR, 4.0);
        put(EVehicleType.SCOOTER, 3.5);
        put(EVehicleType.BIKE, 3.0);
    }};

    public static final HashMap<EVehicleType, Double> tartuDefault = new HashMap<>(){{
        put(EVehicleType.CAR, 3.5);
        put(EVehicleType.SCOOTER, 3.0);
        put(EVehicleType.BIKE, 2.5);
    }};

    public static final HashMap<EVehicleType, Double> parnuDefault = new HashMap<>() {{
        put(EVehicleType.CAR, 3.0);
        put(EVehicleType.SCOOTER, 2.5);
        put(EVehicleType.BIKE, 2.0);
    }};

    public static final HashMap<EVehicleType, Double> snowPhenomenonDefault = new HashMap<>(){{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 1.0);
        put(EVehicleType.BIKE, 1.0);
    }};

    public static final HashMap<EVehicleType, Double> rainPhenomenonDefault = new HashMap<>(){{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 0.5);
        put(EVehicleType.BIKE, 0.5);
    }};

    public static final HashMap<EVehicleType, Double> carOnlyDelivery = new HashMap<>(){{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, null);
        put(EVehicleType.BIKE, null);
    }};



}

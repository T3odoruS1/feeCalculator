package com.fujitsu.feeCalculator.Domain.Enums;

import java.util.HashMap;

public enum EPhenomenonType{

    NO_DATA(""),
    CLEAR("Clear"),
    FEW_CLOUDS("Few clouds"),
    VARIABLE_CLOUDS("Variable clouds"),
    CLOUDY_WITH_CLEAR_SPELLS("Cloudy with clear spells"),
    OVERCAST("Overcast"),
    LIGHT_SNOW_SHOWER("Light snow shower", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 1.0);
        put(EVehicleType.BIKE, 1.0);
    }}),
    MODERATE_SNOW_SHOWER("Moderate snow shower", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 1.0);
        put(EVehicleType.BIKE, 1.0);
    }}),
    HEAVY_SNOW_SHOWER("Heavy snow shower", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 1.0);
        put(EVehicleType.BIKE, 1.0);
    }}),
    LIGHT_SHOWER("Light shower", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 0.5);
        put(EVehicleType.BIKE, 0.5);
    }}),
    MODERATE_SHOWER("Moderate shower", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 0.5);
        put(EVehicleType.BIKE, 0.5);
    }}),
    HEAVY_SHOWER("Heavy shower", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 0.5);
        put(EVehicleType.BIKE, 0.5);
    }}),
    LIGHT_RAIN("Light rain", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 0.5);
        put(EVehicleType.BIKE, 0.5);
    }}),
    MODERATE_RAIN("Moderate rain", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 0.5);
        put(EVehicleType.BIKE, 0.5);
    }}),
    HEAVY_RAIN("Heavy rain", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 0.5);
        put(EVehicleType.BIKE, 0.5);
    }}),
    GLAZE("Glaze", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, null);
        put(EVehicleType.BIKE, null);
    }}),
    LIGHT_SLEET("Light sleet", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 1.0);
        put(EVehicleType.BIKE, 1.0);
    }}),
    MODERATE_SLEET("Moderate sleet", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 1.0);
        put(EVehicleType.BIKE, 1.0);
    }}),
    LIGHT_SNOWFALL("Light snowfall", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 1.0);
        put(EVehicleType.BIKE, 1.0);
    }}),
    MODERATE_SNOWFALL("Moderate snowfall", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 1.0);
        put(EVehicleType.BIKE, 1.0);
    }}),
    HEAVY_SNOWFALL("Heavy snowfall", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 1.0);
        put(EVehicleType.BIKE, 1.0);
    }}),
    BLOWING_SNOW("Blowing snow", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 1.0);
        put(EVehicleType.BIKE, 1.0);
    }}),
    DRIFTING_SNOW("Drifting snow", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, 1.0);
        put(EVehicleType.BIKE, 1.0);
    }}),
    HAIL("Hail", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, null);
        put(EVehicleType.BIKE, null);
    }}),
    MIST("Mist"),
    FOG("Fog"),
    THUNDER("Thunder", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, null);
        put(EVehicleType.BIKE, null);
    }}),
    THUNDERSTORM("Thunderstorm", new HashMap<>() {{
        put(EVehicleType.CAR, 0.0);
        put(EVehicleType.SCOOTER, null);
        put(EVehicleType.BIKE, null);
    }});

    public final String label;
    public final HashMap<EVehicleType, Double> defaultVehicleFees;


    EPhenomenonType(String label, HashMap<EVehicleType, Double> vehicleTypeValues) {
        this.label = label;
        this.defaultVehicleFees = vehicleTypeValues;
    }


    // This constructor is used when phenomenon is not associated with and fees
    EPhenomenonType(String label){
        this(label, new HashMap<>() {{
            put(EVehicleType.CAR, 0.0);
            put(EVehicleType.SCOOTER, 0.0);
            put(EVehicleType.BIKE, 0.0);
        }});
    }

    public Double getAdditionalFee(EVehicleType vehicleType){
        return defaultVehicleFees.get(vehicleType);
    }

}

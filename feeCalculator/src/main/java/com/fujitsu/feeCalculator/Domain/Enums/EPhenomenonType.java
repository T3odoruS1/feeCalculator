package com.fujitsu.feeCalculator.Domain.Enums;

import com.fujitsu.feeCalculator.Domain.DeliveryFeeHashmapStore;

import java.util.HashMap;

public enum EPhenomenonType{

    NO_DATA(""),
    CLEAR("Clear"),
    FEW_CLOUDS("Few clouds"),
    VARIABLE_CLOUDS("Variable clouds"),
    CLOUDY_WITH_CLEAR_SPELLS("Cloudy with clear spells"),
    OVERCAST("Overcast"),
    LIGHT_SNOW_SHOWER("Light snow shower", DeliveryFeeHashmapStore.snowPhenomenonDefault),
    MODERATE_SNOW_SHOWER("Moderate snow shower", DeliveryFeeHashmapStore.snowPhenomenonDefault),
    HEAVY_SNOW_SHOWER("Heavy snow shower", DeliveryFeeHashmapStore.snowPhenomenonDefault),
    LIGHT_SHOWER("Light shower", DeliveryFeeHashmapStore.rainPhenomenonDefault),
    MODERATE_SHOWER("Moderate shower", DeliveryFeeHashmapStore.rainPhenomenonDefault),
    HEAVY_SHOWER("Heavy shower", DeliveryFeeHashmapStore.rainPhenomenonDefault),
    LIGHT_RAIN("Light rain", DeliveryFeeHashmapStore.rainPhenomenonDefault),
    MODERATE_RAIN("Moderate rain", DeliveryFeeHashmapStore.rainPhenomenonDefault),
    HEAVY_RAIN("Heavy rain", DeliveryFeeHashmapStore.rainPhenomenonDefault),
    GLAZE("Glaze", DeliveryFeeHashmapStore.carOnlyDelivery),
    LIGHT_SLEET("Light sleet", DeliveryFeeHashmapStore.snowPhenomenonDefault),
    MODERATE_SLEET("Moderate sleet", DeliveryFeeHashmapStore.snowPhenomenonDefault),
    LIGHT_SNOWFALL("Light snowfall", DeliveryFeeHashmapStore.snowPhenomenonDefault),
    MODERATE_SNOWFALL("Moderate snowfall", DeliveryFeeHashmapStore.snowPhenomenonDefault),
    HEAVY_SNOWFALL("Heavy snowfall", DeliveryFeeHashmapStore.snowPhenomenonDefault),
    BLOWING_SNOW("Blowing snow", DeliveryFeeHashmapStore.snowPhenomenonDefault),
    DRIFTING_SNOW("Drifting snow", DeliveryFeeHashmapStore.snowPhenomenonDefault),
    HAIL("Hail", DeliveryFeeHashmapStore.carOnlyDelivery),
    MIST("Mist"),
    FOG("Fog"),
    THUNDER("Thunder", DeliveryFeeHashmapStore.carOnlyDelivery),
    THUNDERSTORM("Thunderstorm", DeliveryFeeHashmapStore.carOnlyDelivery);

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

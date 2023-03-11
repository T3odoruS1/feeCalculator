package com.fujitsu.feeCalculator.Domain.Helpers;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Exceptions.CityNotFoundException;
import com.fujitsu.feeCalculator.Exceptions.VehicleNotFoundException;

public class EnumLabelMapper {

    public static EPhenomenonType getPhenomenonTypeFromString(String label) {
        EPhenomenonType returnValue = null;
        for (EPhenomenonType phenomenonType : EPhenomenonType.values()) {
            if (phenomenonType.label.equalsIgnoreCase(label)) {
                returnValue = phenomenonType;
            }
        }
        if(returnValue == null){
            return EPhenomenonType.NO_DATA;
        }
        return returnValue;
    }

    public static ECityName getCityNameFromString(String label){
        ECityName returnValue = null;
        for (ECityName cityName : ECityName.values()) {
            if(cityName.label.equalsIgnoreCase(label)){
                returnValue = cityName;
            }
        }
        if(returnValue == null){
            throw new CityNotFoundException(label);
        }
        return returnValue;
    }

    public static EVehicleType getVehicleFromString(String label){
        EVehicleType returnValue = null;
        for (EVehicleType vehicleType: EVehicleType.values()){
            if(vehicleType.label.equalsIgnoreCase(label)){
                returnValue = vehicleType;
            }
        }
        if(returnValue == null){
            throw new VehicleNotFoundException(label);
        }
        return returnValue;
    }


}

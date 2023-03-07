package com.fujitsu.feeCalculator.Domain.Helpers;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;

public class EnumLabelMapper {

    public static EPhenomenonType getPhenomenonTypeFromString(String label) {
        EPhenomenonType returnValue = null;
        for (EPhenomenonType phenomenonType : EPhenomenonType.values()) {
            if (phenomenonType.label.equals(label)) {
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
            if(cityName.label.equals(label)){
                returnValue = cityName;
            }
        }
        return returnValue;
    }


}

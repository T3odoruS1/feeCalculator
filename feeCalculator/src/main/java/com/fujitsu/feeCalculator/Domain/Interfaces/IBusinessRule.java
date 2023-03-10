package com.fujitsu.feeCalculator.Domain.Interfaces;

import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;

public interface IBusinessRule {

    Double getAdditionalFee(EVehicleType vehicleType);
}

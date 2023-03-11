package com.fujitsu.feeCalculator.Domain.Interfaces;

import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;


public interface IBusinessRule {

    Double getAdditionalFee(EVehicleType vehicleType);
}

package com.fujitsu.feeCalculator.Domain.Interfaces;

import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;


public interface IBusinessRule {
    /**
     * Get additional fee from business rule considering the type of transport
     * @param vehicleType type of transport
     * @return delivery fee if delivery allowed, null if prohibited
     */
    Double getAdditionalFee(EVehicleType vehicleType);
}

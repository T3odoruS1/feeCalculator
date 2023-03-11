package com.fujitsu.feeCalculator.Domain;

import com.fujitsu.feeCalculator.BLL.HashMapSerializator;
import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.Helpers.EnumLabelMapper;
import com.fujitsu.feeCalculator.Domain.Interfaces.IBusinessRule;
import com.fujitsu.feeCalculator.Domain.Interfaces.IFixedValueBusinessRule;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "regional_fee_rules")
public class RegionalBaseFeeBusinessRule implements IBusinessRule, IFixedValueBusinessRule {

    @Id
    private final UUID id = UUID.randomUUID();
    private String vehicleFeeData;
    @Column(unique=true)
    private ECityName cityName;

    public RegionalBaseFeeBusinessRule(ECityName cityName, HashMap<EVehicleType, Double> vehicleFeeData) {
        this.cityName = cityName;
        HashMapSerializator<EVehicleType> serializator = new HashMapSerializator<>();

        this.vehicleFeeData = serializator.serializeHashMapToString(vehicleFeeData);
    }

    public RegionalBaseFeeBusinessRule(String cityName, HashMap<EVehicleType, Double> vehicleFeeData){
        this(EnumLabelMapper.getCityNameFromString(cityName), vehicleFeeData);
    }

    public RegionalBaseFeeBusinessRule() {

    }

    public HashMap<EVehicleType, Double> getDeserializedVehicleFeeData(){
        HashMapSerializator<EVehicleType> serializator = new HashMapSerializator<>();
        return serializator.deserializeHashMapFromString(this.vehicleFeeData, EVehicleType.class);
    }

    @Override
    @Nullable
    public Double getAdditionalFee(EVehicleType vehicleType) {
        return getDeserializedVehicleFeeData().get(vehicleType);
    }

    public ECityName getCityName() {
        return cityName;
    }

    public UUID getId() {
        return id;
    }

    public void setVehicleFeeData(HashMap<EVehicleType, Double> vehicleFeeData) {
        HashMapSerializator<EVehicleType> serializator = new HashMapSerializator<>();
        this.vehicleFeeData = serializator.serializeHashMapToString(vehicleFeeData);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RegionalBaseFeeBusinessRule that = (RegionalBaseFeeBusinessRule) obj;
        return Objects.equals(id, that.id) && Objects.equals(cityName, that.cityName)
                && Objects.equals(vehicleFeeData, that.vehicleFeeData);
    }

    @Override
    public String toString() {
        return "RegionalBaseFeeBusinessRule{" +
                "id=" + id +
                ", vehicleFeeData='" + vehicleFeeData + '\'' +
                ", cityName=" + cityName +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}

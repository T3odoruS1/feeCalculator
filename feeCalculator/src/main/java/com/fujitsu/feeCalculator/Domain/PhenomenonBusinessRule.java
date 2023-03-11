package com.fujitsu.feeCalculator.Domain;

import com.fujitsu.feeCalculator.BLL.HashMapSerializator;
import com.fujitsu.feeCalculator.Domain.Enums.EPhenomenonType;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.Helpers.EnumLabelMapper;
import com.fujitsu.feeCalculator.Domain.Interfaces.IBusinessRule;
import com.fujitsu.feeCalculator.Domain.Interfaces.IFixedValueBusinessRule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "phenomenon_rules")
public class PhenomenonBusinessRule implements IBusinessRule, IFixedValueBusinessRule {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(unique=true)
    private EPhenomenonType phenomenonType;

    private String vehicleFeeData;

    public PhenomenonBusinessRule(EPhenomenonType phenomenonType, HashMap<EVehicleType, Double> vehicleFeeData){
        this.phenomenonType = phenomenonType;
        HashMapSerializator<EVehicleType> serializator = new HashMapSerializator<>();

        this.vehicleFeeData = serializator.serializeHashMapToString(vehicleFeeData);
    }
    PhenomenonBusinessRule(String phenomenonName, HashMap<EVehicleType, Double> vehicleFeeData){
        this(EnumLabelMapper.getPhenomenonTypeFromString(phenomenonName), vehicleFeeData);
    }

    public PhenomenonBusinessRule() {

    }

    HashMap<EVehicleType, Double> getDeserializedVehicleFeeData(){
        HashMapSerializator<EVehicleType> serializator = new HashMapSerializator<>();
        return serializator.deserializeHashMapFromString(this.vehicleFeeData, EVehicleType.class);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PhenomenonBusinessRule that = (PhenomenonBusinessRule) obj;
        return Objects.equals(id, that.id) && Objects.equals(phenomenonType, that.phenomenonType)
                && Objects.equals(vehicleFeeData, that.vehicleFeeData);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Double getAdditionalFee(EVehicleType vehicleType) {
        return getVehicleFeeData().get(vehicleType);
    }

    public EPhenomenonType getPhenomenonType() {
        return phenomenonType;
    }

    public UUID getId() {
        return id;
    }

    public void setVehicleFeeData(HashMap<EVehicleType, Double> vehicleFeeData) {
        HashMapSerializator<EVehicleType> serializator = new HashMapSerializator<>();

        this.vehicleFeeData = serializator.serializeHashMapToString(vehicleFeeData);
    }

    @Override
    public String toString() {
        return "PhenomenonBusinessRule{" +
                "id=" + id +
                ", phenomenonType=" + phenomenonType +
                ", vehicleFeeData=" + vehicleFeeData +
                '}';
    }


    public HashMap<EVehicleType, Double> getVehicleFeeData() {
        return getDeserializedVehicleFeeData();
    }


}

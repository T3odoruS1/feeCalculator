package com.fujitsu.feeCalculator.Domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fujitsu.feeCalculator.BLL.HashMapSerializator;
import com.fujitsu.feeCalculator.Domain.Enums.EValueUnit;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.Interfaces.IBusinessRule;
import com.fujitsu.feeCalculator.Exceptions.InvalidValueRangeConfiguration;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Entity

@Table(name = "value_range_rules")
public class ValueRangeBusinessRule implements IBusinessRule {

    @Id
    private UUID id;
    private String vehicleFeeData;
    @NotNull
    private Double minValue;
    @NotNull
    private Double maxValue;
    private EValueUnit valueUnit;

    @JsonIgnore
    public ValueRangeBusinessRule(Double minValue,
                                  Double maxValue,
                                  EValueUnit valueUnit,
                                  HashMap<EVehicleType, Double> vehicleFeeData) {


        validateMinMaxValues(minValue, maxValue, valueUnit);
        this.minValue = minValue;
        this.maxValue = maxValue;

        this.valueUnit = valueUnit;
        HashMapSerializator<EVehicleType> serializator = new HashMapSerializator<>();

        this.vehicleFeeData = serializator.serializeHashMapToString(vehicleFeeData);
    }
    @JsonCreator
    public ValueRangeBusinessRule(@Nullable @JsonProperty("id") UUID id,
                                  @JsonProperty("minValue") Double minValue,
                                  @JsonProperty("maxValue") Double maxValue,
                                  @JsonProperty("valueUnit") EValueUnit valueUnit,
                                  @JsonProperty("vehicleFeeData") HashMap<EVehicleType, Double> vehicleFeeData){
        this(minValue, maxValue, valueUnit, vehicleFeeData);
        this.id = id;
    }


    @JsonIgnore
    public ValueRangeBusinessRule() {
    }

    public void generateId(){
        if(this.id == null)this.id = UUID.randomUUID();
    }

    public HashMap<EVehicleType, Double> getDeserializedVehicleFeeData(){
        HashMapSerializator<EVehicleType> serializator = new HashMapSerializator<>();

        return serializator.deserializeHashMapFromString(this.vehicleFeeData, EVehicleType.class);
    }


    public Double getAdditionalFee(EVehicleType vehicleType) {
        return getDeserializedVehicleFeeData().get(vehicleType);
    }

    /**
     * Check if provided value is in range of this business rule. Min and max are exclusive
     * @param value value to check
     * @return true if in range, false if not
     */
    public boolean checkIfValueInRange(Double value){
        return minValue < value && maxValue > value;
    }

    /**
     * Check if provided value is in range on this business rule. Min is inclusive, max is not
     * @param value value to check
     * @return true if in range, false if not
     */
    public boolean checkIfValueInRangeMinInclusive(Double value){
        return minValue <= value && maxValue > value;
    }

    public EValueUnit getValueUnit() {
        return valueUnit;
    }

    public UUID getId() {
        return id;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMaxValue(double maxValue) {
        validateMinMaxValues(minValue, maxValue, valueUnit);

        this.maxValue = maxValue;
    }

    public void setMinValue(double minValue) {
        validateMinMaxValues(minValue, maxValue, valueUnit);
        this.minValue = minValue;
    }

    public void setVehicleFeeData(HashMap<EVehicleType, Double> vehicleFeeData) {
        HashMapSerializator<EVehicleType> serializator = new HashMapSerializator<>();
        this.vehicleFeeData = serializator.serializeHashMapToString(vehicleFeeData);
    }

    private void validateMinMaxValues(Double min, Double max, EValueUnit valueUnit){
        if(min > max){
            throw new InvalidValueRangeConfiguration();
        }
        if(valueUnit.equals(EValueUnit.WIND_SPEED) && min < 0.0){
            throw  new InvalidValueRangeConfiguration();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ValueRangeBusinessRule)) return false;
        ValueRangeBusinessRule other = (ValueRangeBusinessRule) obj;
        return Double.compare(minValue, other.minValue) == 0 &&
                Double.compare(maxValue, other.maxValue) == 0 &&
                valueUnit == other.valueUnit &&
                Objects.equals(getDeserializedVehicleFeeData(), other.getDeserializedVehicleFeeData());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValueRangeBusinessRule {")
                .append("id=").append(id)
                .append(", minValue=").append(minValue)
                .append(", maxValue=").append(maxValue)
                .append(", valueUnit=").append(valueUnit)
                .append(", vehicleFeeData=").append(vehicleFeeData)
                .append("}");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

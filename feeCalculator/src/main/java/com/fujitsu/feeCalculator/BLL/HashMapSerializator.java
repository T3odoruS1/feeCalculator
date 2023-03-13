package com.fujitsu.feeCalculator.BLL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fujitsu.feeCalculator.Domain.Enums.ECityName;

import java.io.IOException;
import java.util.HashMap;

public class HashMapSerializator<E extends Enum<E>> {

    /**
     * Serialize hashmap to string.
     * @param hashMap hashmap to serialize
     * @return serialized hashmap
     */
    public String serializeHashMapToString(HashMap<E, Double> hashMap) {
        String json = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(hashMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Deserialize hashmap from string
     * @param json json string containing a hashmap
     * @param enumClass type of key in the hashmap
     * @return deserialized from string hashmap
     */
    public HashMap<E, Double> deserializeHashMapFromString(String json, Class<E> enumClass) {
        HashMap<E, Double> hashMap = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            hashMap = objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(HashMap.class, enumClass, Double.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

}


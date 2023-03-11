package com.fujitsu.feeCalculator.BLL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fujitsu.feeCalculator.Domain.Enums.ECityName;

import java.io.IOException;
import java.util.HashMap;

public class HashMapSerializator<E extends Enum<E>> {

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


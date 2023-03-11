package com.fujitsu.feeCalculator.REST.DataClasses;

import com.fujitsu.feeCalculator.Domain.WeatherRecord;

public class Message implements IRestResponseMessage{
    public Double deliveryFee;
    public WeatherRecord weatherRecord;
    public String message;

}

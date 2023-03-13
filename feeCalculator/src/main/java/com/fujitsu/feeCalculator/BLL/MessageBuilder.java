package com.fujitsu.feeCalculator.BLL;

import com.fujitsu.feeCalculator.REST.DataClasses.IRestResponseMessage;
import com.fujitsu.feeCalculator.REST.DataClasses.Message;
import com.fujitsu.feeCalculator.Domain.WeatherRecord;

public class MessageBuilder {

    /**
     * Constructs a message using a weather record and a delivery fee
     * @param deliveryFee delivery fee or null for prohibited delivery
     * @param weatherRecord weather record
     * @return response message for REST interface
     */
    public static IRestResponseMessage getMessage(Double deliveryFee, WeatherRecord weatherRecord){
        Message message = new Message();
        message.weatherRecord = weatherRecord;
        if(deliveryFee == null){
            message.message = "Usage of selected vehicle type is forbidden";
        }else{
            message.message = "Delivery allowed";
        }
        message.deliveryFee = deliveryFee;
        return message;
    }

}

package com.fujitsu.feeCalculator.BLL;

import com.fujitsu.feeCalculator.REST.DataClasses.IRestResponseMessage;
import com.fujitsu.feeCalculator.REST.DataClasses.Message;
import com.fujitsu.feeCalculator.Domain.WeatherRecord;

public class MessageBuilder {

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

    public static Message getMessage(String message){
        Message message1 = new Message();
        message1.message = message;
        return message1;
    }

}

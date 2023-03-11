package com.fujitsu.feeCalculator.DomainTests;

import com.fujitsu.feeCalculator.BLL.ResponseFactory;
import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import com.fujitsu.feeCalculator.Services.WeatherService.Database.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResponseFactoryTests {

    private final ResponseFactory responseFactory;
    private final WeatherService weatherService;

    @Autowired
    public ResponseFactoryTests(ResponseFactory responseFactory, WeatherService weatherService) {
        this.responseFactory = responseFactory;
        this.weatherService = weatherService;
    }

    @Test
    public void TestMessageResponseForSavedWeather(){
        WeatherRecord weatherWithTallinnBikeForbiden = new WeatherRecord(
                1678187750L,
                "Tallinn-Harku",
                "26038",
                "Overcast",
                15.0,
                40.1
        );

        WeatherRecord weatherWithTallinnBikeAllowed = new WeatherRecord(
                1678187750L,
                "Tallinn-Harku",
                "26038",
                "Overcast",
                15.0,
                40.1
        );

        // TODO -- run tests

    }




}

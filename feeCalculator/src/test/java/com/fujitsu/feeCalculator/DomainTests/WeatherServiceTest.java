package com.fujitsu.feeCalculator.DomainTests;

import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import com.fujitsu.feeCalculator.Services.WeatherService.Database.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WeatherServiceTest {


    private final WeatherService weatherService;

    @Autowired
    public WeatherServiceTest(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Test
    void testSavingFunctionality(){
        WeatherRecord record = new WeatherRecord(
                1678187750L,
                "Tallinn-Harku",
                "26038",
                "Overcast",
                -3.4,
                3.1

        );

        weatherService.saveWeatherRecord(record);

        List<WeatherRecord> records = weatherService.getAllWeatherRecords();

        Assertions.assertEquals(1, records.size());
        Assertions.assertEquals(record, records.get(0));
    }

    @Test
    void testDeletingFunctionality(){
        WeatherRecord record = new WeatherRecord(
                1678187750L,
                "Tallinn-Harku",
                "26038",
                "Overcast",
                -3.4,
                3.1

        );

        weatherService.saveWeatherRecord(record);
        weatherService.deleteWeatherRecordById(record.getId());
        List<WeatherRecord> records = weatherService.getAllWeatherRecords();

        Assertions.assertEquals(0, records.size());
    }

    @Test
    void getLatestWeatherRecordByCity(){
        WeatherRecord recordTallinnFirst = new WeatherRecord(
                1678187750L,
                "Tallinn-Harku",
                "26038",
                "Overcast",
                -3.4,
                3.1

        );

        WeatherRecord recordTallinnLast = new WeatherRecord(
                1679187750L,
                "Tallinn-Harku",
                "26038",
                "Overcast",
                -3.4,
                3.1

        );

        WeatherRecord recordTartuFirst = new WeatherRecord(
                1678187750L,
                "Tartu-Tõravere",
                "26038",
                "Overcast",
                -3.4,
                3.1

        );
        WeatherRecord recordTartuLast = new WeatherRecord(
                1679187750L,
                "Tartu-Tõravere",
                "26038",
                "Overcast",
                -3.4,
                3.1

        );

        weatherService.saveWeatherRecord(recordTallinnLast);
        weatherService.saveWeatherRecord(recordTartuFirst);
        weatherService.saveWeatherRecord(recordTartuLast);
        weatherService.saveWeatherRecord(recordTallinnFirst);

        WeatherRecord recievedTallinn= weatherService.getLatestWeatherRecordForCity(ECityName.TALLINN);

        Assertions.assertEquals(recievedTallinn, recordTallinnLast);

        WeatherRecord recievedTartu = weatherService.getLatestWeatherRecordForCity(ECityName.TARTU);

        Assertions.assertEquals(recordTartuLast, recievedTartu);

        weatherService.deleteWeatherRecordById(recordTallinnLast.getId());
        weatherService.deleteWeatherRecordById(recordTartuFirst.getId());
        weatherService.deleteWeatherRecordById(recordTartuLast.getId());
        weatherService.deleteWeatherRecordById(recordTallinnFirst.getId());
    }


}

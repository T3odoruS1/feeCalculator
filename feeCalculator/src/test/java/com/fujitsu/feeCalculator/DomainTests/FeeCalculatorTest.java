package com.fujitsu.feeCalculator.DomainTests;


import com.fujitsu.feeCalculator.BLL.FeeCalculator;
import com.fujitsu.feeCalculator.Domain.Enums.EVehicleType;
import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class FeeCalculatorTest {

    private final WeatherRecord weatherWithNoExtraFeesTallinn = new WeatherRecord(
                    1678187750L,
                            "Tallinn-Harku",
                            "26038",
                            "Overcast",
                            15.0,
                            3.1
    );

    private final WeatherRecord weatherWithNoExtraFeesTartu = new WeatherRecord(
            1678187750L,
            "Tartu-Tõravere",
            "26038",
            "Overcast",
            15.0,
            3.1
    );

    private final WeatherRecord weatherWithNoExtraFeesParnu = new WeatherRecord(
            1678187750L,
            "Pärnu",
            "26038",
            "Overcast",
            15.0,
            3.1
    );

    private final FeeCalculator feeCalculator;

    @Autowired
    FeeCalculatorTest(FeeCalculator feeCalculator){
        this.feeCalculator = feeCalculator;
    }

    @Test
    void testFeeCalculatorWithRegionalBaseFee(){
        // test each city with bike

        Double calculatedTallinn = feeCalculator.calculateFee(
                weatherWithNoExtraFeesTallinn,
                EVehicleType.BIKE);
        Double calculatedTartu = feeCalculator.calculateFee(
                weatherWithNoExtraFeesTartu,
                EVehicleType.BIKE
        );
        Double calculatedParnu = feeCalculator.calculateFee(
                weatherWithNoExtraFeesParnu,
                EVehicleType.BIKE
        );

        Assertions.assertEquals(3.0, calculatedTallinn);
        Assertions.assertEquals(2.0, calculatedParnu);
        Assertions.assertEquals(2.5, calculatedTartu);
    }

    @Test
    void testFeeCalculatorWithTransportFees(){
        Double calculatedScooter = feeCalculator.calculateFee(
                weatherWithNoExtraFeesTallinn,
                EVehicleType.SCOOTER);

        Double calculatedCar = feeCalculator.calculateFee(
                weatherWithNoExtraFeesTallinn,
                EVehicleType.CAR);

        Assertions.assertEquals(3.5, calculatedScooter);
        Assertions.assertEquals(4.0, calculatedCar);
    }

    @Test
    void testFeeCalculatorAddsATEFForBikeAndScooter(){
        // Test Tartu bike, scooter with -15 and -5
        // Car should not get ATEF
        WeatherRecord weatherTartuMinus15 = new WeatherRecord(
                1678187750L,
                "Tartu-Tõravere",
                "26038",
                "Overcast",
                -15.0,
                3.1
        );
        WeatherRecord weatherTartuMinus5 = new WeatherRecord(
                1678187750L,
                "Tartu-Tõravere",
                "26038",
                "Overcast",
                -5.0,
                3.1
        );
        Assertions.assertEquals(3.5, feeCalculator.calculateFee(weatherTartuMinus15, EVehicleType.BIKE));
        Assertions.assertEquals(4.0, feeCalculator.calculateFee(weatherTartuMinus15, EVehicleType.SCOOTER));
        Assertions.assertEquals(3.0, feeCalculator.calculateFee(weatherTartuMinus5, EVehicleType.BIKE));
        Assertions.assertEquals(3.5, feeCalculator.calculateFee(weatherTartuMinus5, EVehicleType.SCOOTER));
        Assertions.assertEquals(3.5, feeCalculator.calculateFee(weatherTartuMinus15, EVehicleType.CAR));
        Assertions.assertEquals(3.5, feeCalculator.calculateFee(weatherTartuMinus5, EVehicleType.CAR));
    }

    @Test
    void testFeeCalculatorWithWSEFForBikeAndScooterAndCar(){
        //Test Parnu Bike, Car, Scooter 15m/s, 25m/s
        // Scooter and car should not get WSEF
        // Bike value should be null on 25m/s


        WeatherRecord weatherParnu15ms = new WeatherRecord(
                1678187750L,
                "Pärnu",
                "26038",
                "Overcast",
                15.0,
                15.0
        );


        WeatherRecord weatherParnu25ms = new WeatherRecord(
                1678187750L,
                "Pärnu",
                "26038",
                "Overcast",
                15.0,
                25.0
        );


        Assertions.assertEquals(2.5, feeCalculator.calculateFee(weatherParnu15ms, EVehicleType.BIKE));
        Assertions.assertNull(feeCalculator.calculateFee(weatherParnu25ms, EVehicleType.BIKE));
        Assertions.assertEquals(3.0, feeCalculator.calculateFee(weatherParnu15ms, EVehicleType.CAR));
        Assertions.assertEquals(2.5, feeCalculator.calculateFee(weatherParnu15ms, EVehicleType.SCOOTER));
        Assertions.assertEquals(3.0, feeCalculator.calculateFee(weatherParnu25ms, EVehicleType.CAR));
        Assertions.assertEquals(2.5, feeCalculator.calculateFee(weatherParnu25ms, EVehicleType.SCOOTER));
    }

    @Test
    void testFeeCalculatorWithWeatherPhenomenons(){
        WeatherRecord weatherParnuThunder = new WeatherRecord(
                1678187750L,
                "Pärnu",
                "26038",
                "Thunder",
                15.0,
                2.0
        );
        WeatherRecord weatherParnuSnow = new WeatherRecord(
                1678187750L,
                "Pärnu",
                "26038",
                "Blowing snow",
                15.0,
                2.0
        );
        WeatherRecord weatherParnuRain = new WeatherRecord(
                1678187750L,
                "Pärnu",
                "26038",
                "Light rain",
                15.0,
                2.0
        );

        Assertions.assertNull(feeCalculator.calculateFee(weatherParnuThunder, EVehicleType.BIKE));
        Assertions.assertNull(feeCalculator.calculateFee(weatherParnuThunder, EVehicleType.SCOOTER));
        Assertions.assertEquals(3.0, feeCalculator.calculateFee(weatherParnuThunder, EVehicleType.CAR));

        Assertions.assertEquals(3.0, feeCalculator.calculateFee(weatherParnuSnow, EVehicleType.BIKE));
        Assertions.assertEquals(3.5, feeCalculator.calculateFee(weatherParnuSnow, EVehicleType.SCOOTER));
        Assertions.assertEquals(3.0, feeCalculator.calculateFee(weatherParnuSnow, EVehicleType.CAR));

        Assertions.assertEquals(2.5, feeCalculator.calculateFee(weatherParnuRain, EVehicleType.BIKE));
        Assertions.assertEquals(3.0, feeCalculator.calculateFee(weatherParnuRain, EVehicleType.SCOOTER));
        Assertions.assertEquals(3.0, feeCalculator.calculateFee(weatherParnuRain, EVehicleType.CAR));
    }



}

package com.fujitsu.feeCalculator;

import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import com.fujitsu.feeCalculator.Services.WeatherService.API.WeatherApiService;
import com.fujitsu.feeCalculator.Services.WeatherService.Database.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class FeeCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeeCalculatorApplication.class, args);

    }
    @Autowired
    WeatherService weatherService;

    @Scheduled(cron = "${app.cronExpression}")
    void someJob() {
        System.out.println(new Date());
        WeatherApiService service = new WeatherApiService();
        List<WeatherRecord> records = service.getLatestObservations();
        weatherService.saveAllWeatherRecords(records);
    }
}

@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfig {

}

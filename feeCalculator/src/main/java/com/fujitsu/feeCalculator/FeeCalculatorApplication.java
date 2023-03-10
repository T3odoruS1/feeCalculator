package com.fujitsu.feeCalculator;

import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import com.fujitsu.feeCalculator.Services.WeatherService.API.WeatherApiService;
import com.fujitsu.feeCalculator.Services.WeatherService.Database.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@SpringBootApplication
public class FeeCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeeCalculatorApplication.class, args);

    }

//    @Bean
//    CommandLineRunner commandLineRunner() {
//        return args -> {
//        };
//    }


    @Autowired
    WeatherService weatherService;
    // Cron job notes.
    // * * * * * * - every second, every minute, every hour...
    // */5 */5 * * * * - every 5 seconds, each 5th minute
    // * * * * * - run every minute
    // 15 * * * * - every hour on the 15th minute

    @Scheduled(cron = "${app.cronExpression}")
    void someJob() {
//        System.out.println("cron job");
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

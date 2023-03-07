package com.fujitsu.feeCalculator;

import com.fujitsu.feeCalculator.Services.WeatherService.WeatherApiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
public class FeeCalculatorApplication {

    WeatherApiService service = new WeatherApiService();

    public static void main(String[] args) {
        SpringApplication.run(FeeCalculatorApplication.class, args);
    }


    // Cron job notes.
    // * * * * * * - every second, every minute, every hour...
    // */5 */5 * * * * - every 5 seconds, each 5th minute
    // * * * * * - run every minute
    // 15 * * * * - every hour on the 15th minute

    @Scheduled(cron = "*/5 * * * * *")
    void someJob(){

        System.out.println(service.getLatestObservations());
    }

}
@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfig{

}

package com.fujitsu.feeCalculator.DomainTests;

import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import com.fujitsu.feeCalculator.Services.WeatherService.WeatherApiService;
import com.fujitsu.feeCalculator.Services.WeatherService.XMLDecoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest

public class WeatherApiServiceTests {

    @Test
    void testRequest() {
        WeatherApiService service = new WeatherApiService();
        try {
            String result = service.preformRequest();
//            System.out.println(result);
            assert (!result.isEmpty());
            assert (result).contains("<observations timestamp");

        } catch (Exception e) {
            System.out.println("From test: response not retrieved");
        }
    }

    @Test
    void testRequestContainsNeededLocations() {
        WeatherApiService service = new WeatherApiService();
        try {
            String result = service.preformRequest();
//            System.out.println(result);
            assert (result).contains("<name>Tallinn-Harku</name>");
            assert (result).contains("<name>Tartu-Tõravere</name>");
            assert (result).contains("<name>Pärnu</name>");

        } catch (Exception e) {
            System.out.println("From test: response not retrieved");
        }
    }

    @Test
    void testXmlDecodedWithNoExceptions(){
        WeatherApiService service = new WeatherApiService();
        try{
            String res = service.preformRequest();
            XMLDecoder decoder = new XMLDecoder();
            List<WeatherRecord> records = decoder.decodeStringIntoWeatherRecord(res);
            // 3 cities
            assert(records.size() == 3);

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void testXmlDecoderValues(){
        try{
            String xml = readFileAsString("/Users/edgarvildt/Developer/projects/IdeaProjects/feeCalculator/feeCalculator/testXMLData.xml");
            XMLDecoder decoder = new XMLDecoder();
            List<WeatherRecord> records = decoder.decodeStringIntoWeatherRecord(xml);
            // Tallinn is always first in the list
            WeatherRecord expected = new WeatherRecord(
                    1678187750L,
                    "Tallinn-Harku",
                    "26038",
                    "Overcast",
                    -3.4,
                    3.1

            );
            assert(records.get(0)).equals(expected);

        }catch (IOException e){
            System.out.println(e.toString());
        }
    }





    public static String readFileAsString(String fileLocation) throws IOException {
        byte[] encodedFileContent = Files.readAllBytes(Paths.get(fileLocation));
        return new String(encodedFileContent);
    }
}

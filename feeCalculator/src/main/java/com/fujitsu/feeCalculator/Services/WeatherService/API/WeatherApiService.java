package com.fujitsu.feeCalculator.Services.WeatherService.API;
import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import com.fujitsu.feeCalculator.BLL.XMLDecoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class WeatherApiService implements IWeatherApiService {

    private final String URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    // Returns a response body as a string
    public String preformRequest() throws Exception {
        HttpURLConnection conn = prepareConnection(URL);

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {

            // Get the response body as an InputStream
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            // Read the response body into a String
            StringBuilder responseBody = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine);
            }
            in.close();

            // Print the response body
            return responseBody.toString();
        } else {
            System.out.println("Error: " + responseCode);
            throw new RuntimeException("No data was retrieved");
        }
    }

    private HttpURLConnection prepareConnection(String urlString, String requestMethod, String contentType)
            throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(requestMethod);
        conn.setRequestProperty("Content-Type", contentType);
        return conn;
    }

    private HttpURLConnection prepareConnection(String urlString) throws Exception {
        return prepareConnection(urlString, "GET", "application/xml");
    }

    @Override
    public List<WeatherRecord> getLatestObservations() {
        try{
            String xml = preformRequest();
            XMLDecoder decoder = new XMLDecoder();
            List<WeatherRecord> records = decoder.decodeStringIntoWeatherRecord(xml);
            System.out.println(records);
            return records;
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }
}

package com.fujitsu.feeCalculator.BLL;


import com.fujitsu.feeCalculator.Domain.Enums.ECityName;
import com.fujitsu.feeCalculator.Domain.WeatherRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XMLDecoder {

    private final List<String> RELEVANT_STATIONS = getRelevantStations();


    public List<WeatherRecord> decodeStringIntoWeatherRecord(String inputData){
        Document document = getDocumentWithData(inputData);
        if(document == null){
            return null;
        }
        List<WeatherRecord> weatherRecords = new ArrayList<>();
        try{
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("station");
            Long timestamp = getTimestamp(document);
            weatherRecords = getWeatherRecordsFromNodeList(nodeList, timestamp);

        }catch (NullPointerException e){
            System.out.println("Null pointer exception during parsing xml data");
        }
        return weatherRecords;
    }

    private List<WeatherRecord> getWeatherRecordsFromNodeList(NodeList nodeList, Long timestamp){
        List<WeatherRecord> result = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node stationData = nodeList.item(i);
            if(stationData.getNodeType() == Node.ELEMENT_NODE){

                Element station = (Element) stationData;
                if(stationIsRelevant(station)){
                    result.add(getWeatherRecordFromStationNode(station, timestamp));
                }

            }
        }
        return result;
    }

    private WeatherRecord getWeatherRecordFromStationNode(Element station, Long timestamp){
        String stationName = getElementData(station, "name");
        String wmoCode = getElementData(station, "wmocode");
        String phenomenonType = getElementData(station, "phenomenon");
        Double airTemp = Double.parseDouble(getElementData(station, "airtemperature"));
        Double windSpeed = Double.parseDouble(getElementData(station, "windspeed"));

        return new WeatherRecord(timestamp,
                stationName,
                wmoCode,
                phenomenonType,
                airTemp,
                windSpeed);
    }

    private String getElementData(Element element, String nodeName){
        return element.getElementsByTagName(nodeName).item(0).getTextContent();
    }

    private boolean stationIsRelevant(Element element){
        return RELEVANT_STATIONS.contains(getElementData(element, "name"));
    }

    private Document getDocumentWithData(String inputData){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(inputData)));
        }catch (Exception e){
            System.out.println("did not create the document");
            return null;
        }
    }

    private List<String> getRelevantStations(){
        return Arrays.stream(ECityName.values())
                .map(ECityName::getLabel)
                .toList();
    }

    private Long getTimestamp(Document document){
        Element observation = (Element) document.getElementsByTagName("observations").item(0);
        return Long.parseLong(observation.getAttribute("timestamp"));
    }

}

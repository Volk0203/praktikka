package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {
    private static final String API_KEY = "YOUR_API";
    public static void main(String[] args) {
        try {
            String location = getLocation("Saransk");
            String weather = getWeather(location);
            System.out.println("Погода в " + location + ": " + weather);
        } catch (IOException e) {
            System.err.println("Ошибка при получении погоды:");
            e.printStackTrace();
        }
    }
    private static String getLocation(String query) throws IOException {
        String url = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + query;
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.toString());
            return jsonNode.at("/location/name").asText();
        }
        throw new IOException("Ошибка при получении города");
    }
    private static String getWeather(String location) throws IOException {
        String url = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + location;
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.toString());
            return jsonNode.at("/current/condition/text").asText();
        }
        throw new IOException("Ошибка при получении погоды");
    }
}

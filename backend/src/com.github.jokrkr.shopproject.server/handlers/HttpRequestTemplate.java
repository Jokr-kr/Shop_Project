package com.github.jokrkr.shopproject.server.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpRequestTemplate {

    public static void main(String[] args) {
         sendCreateRequest();
         sendDeleteRequest();
         sendPutRequest();
         sendGetRequest();
    }

    //------------------------
    //
    private static void sendCreateRequest() {
        try {
            URL url = new URL("http://localhost:8080/items");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            setRequest(connection, "{\"type\": \"typeA\", \"name\": \"NewItem\", \"price\": 20.0, \"quantity\": 10, \"value\": 200.0}");
            int responseCode = connection.getResponseCode();
            System.out.println("POST Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("POST Response: " + response);
                }
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //------------------------
    //
    private static void setRequest(HttpURLConnection connection, String jsonInputString) throws IOException {
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }

    //------------------------
    //
    private static void sendDeleteRequest() {
        try {
            URL url = new URL("http://localhost:8080/items");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            setRequest(connection, "{\"type\": \"typeA\", \"name\": \"NewItem\", \"price\": 20.0, \"quantity\": 10, \"value\": 200.0}");

            int responseCode = connection.getResponseCode();
            System.out.println("DELETE Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("DELETE Response: " + response);
                }
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //------------------------
    //
    private static void sendGetRequest() {
        try {
            URL url = new URL("http://localhost:8080/items");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("GET Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim()).append("\n");
                    }
                    System.out.println("GET Response: " + response);
                }
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //------------------------
    //
    private static void sendPutRequest() {
        try {
            URL url = new URL("http://localhost:8080/items");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonInputString = "{\"type\": \"typeA\", \"name\": \"UpdatedItem\", \"price\": 25.0, \"quantity\": 5, \"value\": 125.0}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("PUT Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("PUT Response: " + response);
                }
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

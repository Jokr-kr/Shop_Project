package com.github.jokrkr.shopproject.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.json.JSONObject;

public class ServerCommunication {

    private static final String BASE_URL = System.getenv("SERVER_URL") != null ? System.getenv("SERVER_URL") : "http://localhost:8080";

    public static String sendRequest(String method, String context, String sessionId, JSONObject jsonPayload) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + context))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json");

            if (sessionId != null) {
                requestBuilder.header("Session-ID", sessionId);
            }

            if (jsonPayload != null && (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT"))) {
                requestBuilder.method(method, HttpRequest.BodyPublishers.ofString(jsonPayload.toString()));
            } else {
                requestBuilder.method(method, HttpRequest.BodyPublishers.noBody());
            }

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            int responseCode = httpResponse.statusCode();
            if (responseCode == 200) {
                return httpResponse.body();
            } else if (responseCode == 404) {
                throw new RuntimeException("Error: 404 Not Found - The requested resource could not be found.");
            } else if (responseCode == 204) {
                return "";
            } else {
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Network error occurred: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Request was interrupted: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred: " + e.getMessage());
        }
    }
}
package com.example.plcus.multimedia;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClientHTTP {

    private String serverIpAddress;
    private String port;

    public ClientHTTP(String serverIpAddress, String port) {
        this.serverIpAddress = "http://" + serverIpAddress;
        this.port = port;
    }

    OkHttpClient client = new OkHttpClient();

    public String run(String url) {

        Request request = new Request.Builder()
                .url(serverIpAddress + port + url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception e) {
            return null;
        }
    }
}

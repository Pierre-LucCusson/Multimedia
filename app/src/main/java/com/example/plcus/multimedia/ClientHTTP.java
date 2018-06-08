package com.example.plcus.multimedia;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClientHTTP {

    private String serverIpAddress;

    public ClientHTTP(String serverIpAddress) {
        this.serverIpAddress = "http://" + serverIpAddress;
    }

    OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {

        Request request = new Request.Builder()
                .url(serverIpAddress + url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception e) {
            return null;
        }
    }
}

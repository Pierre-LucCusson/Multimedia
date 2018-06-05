package com.example.plcus.multimedia;

import android.util.Log;

import fi.iki.elonen.NanoHTTPD;

// Ref. : https://stackoverflow.com/questions/14309256/using-nanohttpd-in-android
public class ServerHTTPD extends NanoHTTPD {

    public ServerHTTPD()
    {
        super(8080);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "Hello, you have connected.";
        try {
            // Fetch JSON song files

        } catch(Exception e) {
            Log.w("Httpd", e.toString());
        }


        return newFixedLengthResponse(msg);
    }
}


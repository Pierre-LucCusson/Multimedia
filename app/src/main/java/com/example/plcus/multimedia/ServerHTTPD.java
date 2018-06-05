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
        Response response;
            response = newFixedLengthResponse("{testField:test}");
            response.setMimeType("application/json");


        //Allow CORS
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Headers","auth-user, auth-password");
        return response;
    }
}


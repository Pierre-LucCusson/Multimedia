package com.example.plcus.multimedia;

import android.util.Log;

import fi.iki.elonen.NanoHTTPD;

// Ref. : https://stackoverflow.com/questions/14309256/using-nanohttpd-in-android
public class ServerHTTPD extends NanoHTTPD {

    private Playlist playlist;

    public ServerHTTPD(Playlist playlist)
    {
        super(8080);
        this.playlist = playlist;
    }

    @Override
    public Response serve(IHTTPSession session) {
        Response response;
//            response = newFixedLengthResponse("{testField:test}");
        response = newFixedLengthResponse(playlist.toJson());
            response.setMimeType("application/json");


        //Allow CORS
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Headers","auth-user, auth-password");
        return response;
    }
}


package com.example.plcus.multimedia;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

// Ref. : https://stackoverflow.com/questions/14309256/using-nanohttpd-in-android
public class ServerHTTPD extends NanoHTTPD {

    public ServerHTTPD()
    {
        super(8080);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String answer = "";
        try {
            // Open file from SD Card
            File root = Environment.getExternalStorageDirectory();
            // A changer, stream la musique au lieu
            FileReader index = new FileReader(root.getAbsolutePath() +
                    "/www/index.html");
            BufferedReader reader = new BufferedReader(index);
            String line = "";
            while ((line = reader.readLine()) != null) {
                answer += line;
            }
            reader.close();

        } catch(IOException ioe) {
            Log.w("Httpd", ioe.toString());
        }


        return newFixedLengthResponse(answer);
    }
}


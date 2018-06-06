package com.example.plcus.multimedia;

import android.util.Log;
import android.widget.Toast;

public class ServerMusicPlayer extends MusicPlayer {

    private ServerHTTPD serverHTTPD;

    @Override
    public void initialise(MainActivity activity) {
        this.activity = activity;
        playlist = new Playlist(activity);

        serverHTTPD = new ServerHTTPD(playlist);
        try {
            serverHTTPD.start();
        } catch(Exception e) {

        }

        Toast.makeText(activity, R.string.server_start_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void playOrPause() {
        Log.d(this.getClass().getName(), "playButtonClick");
    }

    @Override
    public void stop() {
        Log.d(this.getClass().getName(), "stopButtonClick");
    }

    @Override
    public void previous() {
        Log.d(this.getClass().getName(), "previousButtonClick");
    }

    @Override
    public void next() {
        Log.d(this.getClass().getName(), "nextButtonClick");
    }

    @Override
    public void repeat() {
        Log.d(this.getClass().getName(), "repeatButtonClick");
    }

    @Override
    public void shuffle() {
        Log.d(this.getClass().getName(), "shuffleButtonClick");
    }
}

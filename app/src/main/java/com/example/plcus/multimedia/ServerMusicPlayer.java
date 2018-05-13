package com.example.plcus.multimedia;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ServerMusicPlayer extends MusicPlayer {

    @Override
    public void initialise(AppCompatActivity activity) {
        this.activity = activity;
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

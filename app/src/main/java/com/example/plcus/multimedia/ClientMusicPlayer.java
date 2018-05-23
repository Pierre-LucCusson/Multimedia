package com.example.plcus.multimedia;

import android.util.Log;
import android.widget.Toast;

public class ClientMusicPlayer extends MusicPlayer {

    @Override
    public void initialise(MainActivity activity) {
        this.activity = activity;
        Toast.makeText(activity, R.string.client_could_not_connect, Toast.LENGTH_LONG).show();
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

package com.example.plcus.multimedia;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

public class MusicPlayer implements IMusicPlayer {

    MediaPlayer mediaPlayer;

    public MusicPlayer(AppCompatActivity activity) {

        mediaPlayer = MediaPlayer.create(activity.getApplicationContext(), R.raw.good_life);
        mediaPlayer.setVolume(1,1);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    @Override
    public void playOrPause() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
        else {
            mediaPlayer.start();
        }
    }

    @Override
    public void stop() {
        mediaPlayer.pause();
    }

    @Override
    public void previous() {

    }

    @Override
    public void next() {

    }

    @Override
    public void repeat() {
        if(mediaPlayer.isLooping()) {
            mediaPlayer.setLooping(false);
        }
        else {
            mediaPlayer.setLooping(true);
        }

    }

    @Override
    public void shuffle() {

    }
}

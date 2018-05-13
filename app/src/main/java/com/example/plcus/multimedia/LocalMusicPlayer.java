package com.example.plcus.multimedia;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LocalMusicPlayer extends MusicPlayer {

    @Override
    public void initialise(AppCompatActivity activity) {
        this.activity = activity;
        playlist = new Playlist();

        prepareMediaPlayer(playlist.getCurrentSong());
        mediaPlayer.start();
    }

    @Override
    public void playOrPause() {
        Log.d(this.getClass().getName(), "playButtonClick");
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
        else {
            mediaPlayer.start();
        }
    }

    @Override
    public void stop() {
        Log.d(this.getClass().getName(), "stopButtonClick");
        mediaPlayer.pause();
    }

    @Override
    public void previous() {
        Log.d(this.getClass().getName(), "previousButtonClick");
        playSong(playlist.getPreviousSong());
    }

    @Override
    public void next() {
        Log.d(this.getClass().getName(), "nextButtonClick");
        playSong(playlist.getNextSong());
        mediaPlayer.start();
    }

    @Override
    public void repeat() {
        Log.d(this.getClass().getName(), "repeatButtonClick");
        if(mediaPlayer.isLooping()) {
            mediaPlayer.setLooping(false);
        }
        else {
            mediaPlayer.setLooping(true);
        }
    }

    @Override
    public void shuffle() {
        Log.d(this.getClass().getName(), "shuffleButtonClick");
        playlist.shuffle();
    }

}

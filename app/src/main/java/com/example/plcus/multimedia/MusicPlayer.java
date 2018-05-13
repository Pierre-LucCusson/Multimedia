package com.example.plcus.multimedia;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

public abstract class MusicPlayer implements IMusicPlayer{

    protected AppCompatActivity activity;
    protected MediaPlayer mediaPlayer;
    protected Playlist playlist;

    public abstract void  initialise(AppCompatActivity activity);

    public abstract void playOrPause();

    public abstract void stop();

    public abstract void previous();

    public abstract void next();

    public abstract void repeat();

    public abstract void shuffle();

    protected void playSong(int songId) {
        prepareMediaPlayer(songId);
        mediaPlayer.start();
    }

    protected void prepareMediaPlayer(int songId) {

        if(mediaPlayer != null) {
            mediaPlayer.reset();
        }

        mediaPlayer = MediaPlayer.create(activity, songId);
        mediaPlayer.setVolume(1,1);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

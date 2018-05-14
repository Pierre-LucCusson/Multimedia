package com.example.plcus.multimedia;

import android.media.MediaPlayer;

public abstract class MusicPlayer implements IMusicPlayer{

    protected MainActivity activity;
    protected MediaPlayer mediaPlayer;
    protected Playlist playlist;

    public abstract void  initialise(MainActivity activity);

    public abstract void playOrPause();

    public abstract void stop();

    public abstract void previous();

    public abstract void next();

    public abstract void repeat();

    public abstract void shuffle();

    protected void playSong(Song song) {
        prepareMediaPlayer(song);
        mediaPlayer.start();
    }

    protected void prepareMediaPlayer(Song song) {

        if(mediaPlayer != null) {
            mediaPlayer.reset();
        }

        mediaPlayer = MediaPlayer.create(activity, song.getId());
        mediaPlayer.setVolume(1,1);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        activity.updateTextFor(song);
    }

}

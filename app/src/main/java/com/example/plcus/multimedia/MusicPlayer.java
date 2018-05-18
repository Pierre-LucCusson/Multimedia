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

    public void seekTo(int mSec) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(mSec);
        }
    }

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

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playSong(playlist.getNextSong());
            }
        });
    }

    public boolean isMediaPlayerNull() {
        return mediaPlayer == null;
    }

    public int getMediaPlayerCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getMediaPlayerDuration() {
        return mediaPlayer.getDuration();
    }



}

package com.example.plcus.multimedia;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

public class MusicPlayer implements IMusicPlayer {

    private MediaPlayer mediaPlayer;
    private Playlist playlist;
    private AppCompatActivity activity;

    public MusicPlayer(AppCompatActivity activity) {

        this.activity = activity;
        playlist = new Playlist();

        prepareMediaPlayer(playlist.getCurrentSong());
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
        playSong(playlist.getPreviousSong());
    }

    @Override
    public void next() {
        playSong(playlist.getNextSong());
        mediaPlayer.start();
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
        playlist.shuffle();
    }

    private void playSong(int songId) {
        prepareMediaPlayer(songId);
        mediaPlayer.start();
    }

    private void prepareMediaPlayer(int songId) {

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

package com.example.plcus.multimedia;

import android.util.Log;
import android.widget.Toast;

public class LocalMusicPlayer extends MusicPlayer {

    @Override
    public void initialise(MainActivity activity) {
        this.activity = activity;
        initializeVolume();
        playlist = new Playlist(activity);
        prepareMediaPlayer(playlist.getCurrentSong());
    }

    @Override
    public void playOrPause() {
        Log.d(this.getClass().getName(), "playButtonClick");
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            visualizer.setEnabled(false);
        }
        else {
            mediaPlayer.start();
            visualizer.setEnabled(true);
        }
    }

    @Override
    public void stop() {
        Log.d(this.getClass().getName(), "stopButtonClick");
        mediaPlayer.pause();
        visualizer.setEnabled(false);
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

    @Override
    public void toggleStreamMusicState() {
        Toast.makeText(activity, R.string.not_supported_for_LocalMusicPlayer, Toast.LENGTH_SHORT).show();
    }

}

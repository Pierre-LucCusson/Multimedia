package com.example.plcus.multimedia;

import android.media.MediaPlayer;
import android.media.AudioManager;

import java.io.IOException;

public class MusicPlayer implements IMusicPlayer {

    MediaPlayer mediaPlayer;

    public MusicPlayer() {
//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        try {
//            mediaPlayer.setDataSource("C:\\Users\\plcus\\Music\\Good Life.mp3");
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.setVolume(1,1);
//        mediaPlayer.start();

    }

    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void next() {

    }

    @Override
    public void repeat() {

    }

    @Override
    public void shuffle() {

    }
}

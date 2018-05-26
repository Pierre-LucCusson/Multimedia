package com.example.plcus.multimedia;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;

public abstract class MusicPlayer implements IMusicPlayer{

    protected MainActivity activity;
    protected MediaPlayer mediaPlayer;
    protected Playlist playlist;

    protected Visualizer visualizer;

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

        mediaPlayer = MediaPlayer.create(activity, song.getUri());
        mediaPlayer.setVolume(1,1);

        activity.updateViewInformationFor(song);
        initialiseVisualizer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playSong(playlist.getNextSong());
                visualizer.setEnabled(false);
            }
        });
    }

    protected void initialiseVisualizer() {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if (visualizer != null && visualizer.getEnabled()) {
            visualizer.setEnabled(false);
        }
        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        visualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {
                        activity.updateVisualizer(bytes);
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                    }
                }, Visualizer.getMaxCaptureRate() / 2, true, false);

        visualizer.setEnabled(true);
    }

    public boolean isMediaPlayerNull() {
        return mediaPlayer == null;
    }

    public boolean isPlaying() {
        if(mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    public int getMediaPlayerCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getMediaPlayerDuration() {
        return mediaPlayer.getDuration();
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (visualizer != null) {
            visualizer.setEnabled(false);
            visualizer.release();
            visualizer = null;
        }
    }

}

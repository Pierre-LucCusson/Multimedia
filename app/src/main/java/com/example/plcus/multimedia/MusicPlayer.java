package com.example.plcus.multimedia;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.widget.SeekBar;

public abstract class MusicPlayer implements IMusicPlayer{

    protected MainActivity activity;
    protected MediaPlayer mediaPlayer;
    protected Playlist playlist;

    protected Visualizer visualizer;
    private AudioManager audioManager;

    protected abstract void initialise(MainActivity activity);

    public abstract void playOrPause();

    public abstract void stop();

    public abstract void previous();

    public abstract void next();

    public abstract void repeat();

    public abstract void shuffle();

    public abstract void toggleStreamMusicState();

    public void seekTo(int mSec) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(mSec);
        }
    }

    public void setVolumeTo(int level) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                level, 0);
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
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (visualizer != null) {
            visualizer.setEnabled(false);
            visualizer.release();
            visualizer = null;
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void initializeVolume()
    {
        SeekBar volumeBar = activity.findViewById(R.id.volumeBar);
        audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        volumeBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));


        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar arg0)
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0)
            {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
            {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
            }
        });
    }
}

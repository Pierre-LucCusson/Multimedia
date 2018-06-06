package com.example.plcus.multimedia;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ServerMusicPlayer extends MusicPlayer {

    private ServerHTTPD serverHTTPD;
    private Boolean isStreaming = false;

    @Override
    public void initialise(MainActivity activity) {
        this.activity = activity;
        playlist = new Playlist(activity);
        prepareMediaPlayer(playlist.getCurrentSong());

        serverHTTPD = new ServerHTTPD(playlist) {
            @Override
            public void playOrPauseCommand() {
                playOrPause();
            }

            @Override
            public void playCommand() {
                play();
            }

            @Override
            public void pauseCommand() {
                pause();
            }

            @Override
            public void stopCommand() {
                pause();
            }

            @Override
            public void previousCommand() {
                previous();
            }

            @Override
            public void nextCommand() {
                next();
            }

            @Override
            public void loopCommand() {
                repeat();
            }

            @Override
            public void shuffleCommand() {
                shuffle();
            }

            @Override
            public void seekToCommand(int mSec) {
                seekTo(mSec);
            }
        };

        try {
            serverHTTPD.start();
            TextView ipText = activity.findViewById(R.id.ipText);
            ipText.setText(serverHTTPD.toString());
        } catch(Exception e) {
            Toast.makeText(activity, R.string.server_start_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void playOrPause() {
        Log.d(this.getClass().getName(), "playButtonClick");
        if(mediaPlayer.isPlaying()){
            pause();
        }
        else {
            play();
        }
    }

    @Override
    public void stop() {
        Log.d(this.getClass().getName(), "stopButtonClick");
        if(!isStreaming) {
            pause();
        }
    }

    @Override
    public void previous() {
        Log.d(this.getClass().getName(), "previousButtonClick");
        if(!isStreaming) {
            playSong(playlist.getPreviousSong());
        }
    }

    @Override
    public void next() {
        Log.d(this.getClass().getName(), "nextButtonClick");
        if(!isStreaming) {
            playSong(playlist.getNextSong());
            mediaPlayer.start();
        }
    }

    @Override
    public void repeat() {
        Log.d(this.getClass().getName(), "repeatButtonClick");
        if(!isStreaming) {
            if (mediaPlayer.isLooping()) {
                mediaPlayer.setLooping(false);
            } else {
                mediaPlayer.setLooping(true);
            }
        }
    }

    @Override
    public void shuffle() {
        Log.d(this.getClass().getName(), "shuffleButtonClick");
        if(!isStreaming) {
            playlist.shuffle();
        }
    }

    public void play() {
        if(!isStreaming) {
            mediaPlayer.start();
            visualizer.setEnabled(true);
        }
    }

    public void pause() {
        if(!isStreaming) {
            mediaPlayer.pause();
            visualizer.setEnabled(false);
        }
    }
}

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

        serverHTTPD = new ServerHTTPD() {
            @Override
            public Song playOrPauseCommand() {
                playOrPause();
                return playlist.getCurrentSong();
            }

            @Override
            public Song playCommand() {
                play();
                return playlist.getCurrentSong();
            }

            @Override
            public Song pauseCommand() {
                pause();
                return playlist.getCurrentSong();
            }

            @Override
            public Song stopCommand() {
                pause();
                return playlist.getCurrentSong();
            }

            @Override
            public Song previousCommand() {
                previous();
                return playlist.getCurrentSong();
            }

            @Override
            public Song nextCommand() {
                next();
                return playlist.getCurrentSong();
            }

            @Override
            public Boolean loopCommand() {
                repeat();
                return mediaPlayer.isLooping();
            }

            @Override
            public Boolean shuffleCommand() {
                shuffle();
                return playlist.isShuffled();
            }

            @Override
            public void seekToCommand(int mSec) {
                seekTo(mSec);
            }

            @Override
            public Song getCurrentSong() {
                return playlist.getCurrentSong();
            }
        };

        try {
            serverHTTPD.start();
            TextView ipText = activity.findViewById(R.id.ipText);
//            ipText.setText(serverHTTPD.toString());
//            InetAddress add = Inet4Address.getLocalHost();
//            String ip = add.toString();
            ipText.setText("I am Server");

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

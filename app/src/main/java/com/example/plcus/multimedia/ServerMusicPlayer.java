package com.example.plcus.multimedia;

import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import static android.content.Context.WIFI_SERVICE;

public class ServerMusicPlayer extends MusicPlayer {

    private ServerHTTPD serverHTTPD;
    private Boolean isStreaming = false;
    private TextView streamingText;
    private TextView preparingText;

    @Override
    public void initialise(MainActivity activity) {
        this.activity = activity;

        streamingText = activity.findViewById(R.id.streamingText);
        streamingText.setText(R.string.mode_server_local);
        preparingText = activity.findViewById(R.id.preparingText);

        playlist = new Playlist(activity);
        prepareMediaPlayer(playlist.getCurrentSong());
        initializeVolume();
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
            public void setVolumeCommand(int level) {
                setVolumeTo(level);
            }

            @Override
            public InputStream inputStreamCommand() {
                return playlist.getCurrentSong().getInputStream();
            }

            @Override
            public Song getCurrentSongCommand() {
                return playlist.getCurrentSong();
            }

            @Override
            public Boolean toggleStreamMusicCommand() {
                toggleStreamMusicState();
                return isStreaming;
            }
        };

        try {
            serverHTTPD.start();
            setIpAddressToIpTextView();
        } catch(Exception e) {
            Toast.makeText(activity, R.string.server_start_error, Toast.LENGTH_LONG).show();
        }
    }

    private void setIpAddressToIpTextView() {
        try {
            TextView ipText = activity.findViewById(R.id.ipText);
            WifiManager wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(WIFI_SERVICE);
            String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
            ipText.setText(ipAddress);
            playlist.setIpAddressToSongs(ipAddress);
        } catch (Exception e) {
            Toast.makeText(activity, R.string.permission_access_network_state_is_required, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void playOrPause() {
        Log.d(this.getClass().getName(), "playButtonClick");
        if (!isStreaming) {
            if (mediaPlayer.isPlaying()) {
                pause();
            } else {
                play();
            }
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
        if(isStreaming) {
            playlist.getPreviousSong();
        }
        else {
            playSong(playlist.getPreviousSong());
        }
    }

    @Override
    public void next() {
        Log.d(this.getClass().getName(), "nextButtonClick");
        if(isStreaming) {
            playlist.getNextSong();
        }
        else {
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

    @Override
    public void toggleStreamMusicState() {
        isStreaming = !isStreaming;
        if (isStreaming) {
            activity.updateStreamingText(R.string.mode_streaming);
            releaseMediaPlayer();
        }
        else {
            activity.updateStreamingText(R.string.mode_server_local);
            prepareMediaPlayer(playlist.getCurrentSong());
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

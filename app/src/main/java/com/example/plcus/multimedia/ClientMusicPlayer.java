package com.example.plcus.multimedia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

// SharedPreferences inspiration :
// https://stackoverflow.com/questions/23024831/android-shared-preferences-example
public class ClientMusicPlayer extends MusicPlayer {

    private ClientHTTP client;
    private String serverIpAddress;
    private Boolean isStreaming;
    private Song songStreaming;
    private TextView streamingText;
    private TextView preparingText;
    private boolean isPrepared;

    public ClientMusicPlayer(MainActivity activity) {
        askForServerIpAddress(activity);
    }

    @Override
    protected void initialise(MainActivity activity) {
        this.activity = activity;
        isPrepared = false;
        initializeVolume();
        updateMode(R.string.connecting);

        isStreaming = false;
        new Thread(new Runnable(){
            @Override
            public void run() {
                String jsonSong = sendToServerCommand(ServerCommand.SONG);
                if( checkResponse(jsonSong) ) {
                    updateViewInformationFor(jsonSong);
                    updateMode(R.string.mode_command);
                }
                else
                {
                    updateMode(R.string.not_connected);
                }
            }
        }).start();
    }

    private boolean checkResponse(String json)
    {
        boolean connected = true;

        if( json == null )
        {
            displayMessage(R.string.not_connected);
            connected = false;
        }

        return connected;
    }

    private void initialiseMusicPlayerForStreaming(final String serverCommand) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                String jsonSong = sendToServerCommand(serverCommand);
                if( checkResponse(jsonSong) ) {
                    songStreaming = new Gson().fromJson(jsonSong, Song.class);
                    prepareMediaPlayer(songStreaming);
                }
            }
        }).start();
    }

    @Override
    public void prepareMediaPlayer(Song song) {

        releaseMediaPlayer();

        isPrepared = false;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {

            mediaPlayer.setDataSource(song.getUrl());
            updatePreparingText(R.string.preparing_song);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){

                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    isPrepared = true;
                    updatePreparingText(R.string.preparing_song_done);
                }
            });
            initialiseVisualizer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
//                    playSong(songStreaming); //  TODO did this break something ?
                    next();
                    visualizer.setEnabled(false);
                }
            });
            mediaPlayer.prepareAsync();

            activity.updateViewInformationFor(song);

        }
        catch (Exception e) {
            isStreaming = false;
            visualizer.setEnabled(false);
            Toast.makeText(activity, R.string.could_not_stream, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void playOrPause() {
        Log.d(this.getClass().getName(), "playButtonClick");
        //TODO button image should change image when server sends for multiple command

        if(isStreaming) {
            if(mediaPlayer.isPlaying()) {
                stop();
            }
            else {
                play();
            }
        }
        else {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    String jsonSong = sendToServerCommand(ServerCommand.PLAY_OR_PAUSE);
                    if (checkResponse(jsonSong)) {
                        songStreaming = new Gson().fromJson(jsonSong, Song.class);
                        activity.updateViewInformationFor(songStreaming);
                    }
                }
            }).start();
        }
    }

    public void play() {
        new Thread(new Runnable(){
            @Override
            public void run() {

                if(isStreaming) {
                    if (songStreaming == null) {
                        String jsonSong = sendToServerCommand(ServerCommand.PLAY);
                        if (checkResponse(jsonSong)) {
                            songStreaming = new Gson().fromJson(jsonSong, Song.class);
                            activity.updateViewInformationFor(songStreaming);
                            prepareMediaPlayer(songStreaming);
                        }
                    }
                    else {
                        mediaPlayer.start();
                        visualizer.setEnabled(true);
                    }
                }
                else {
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        visualizer.setEnabled(true);
                    }
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        Log.d(this.getClass().getName(), "stopButtonClick");
        new Thread(new Runnable(){
            @Override
            public void run() {
                String jsonSong = sendToServerCommand(ServerCommand.STOP);
                if( checkResponse(jsonSong) ) {
                    if (songStreaming == null) {
                        songStreaming = new Gson().fromJson(jsonSong, Song.class);
                        activity.updateViewInformationFor(songStreaming);
                    }
                    if (isStreaming) {
                        mediaPlayer.pause();
                        visualizer.setEnabled(false);
                    }
                }
            }
        }).start();
    }

    @Override
    public void previous() {
        Log.d(this.getClass().getName(), "previousButtonClick");
        new Thread(new Runnable(){
            @Override
            public void run() {
                String jsonSong = sendToServerCommand(ServerCommand.PREVIOUS);
                if( checkResponse(jsonSong) ) {
                    songStreaming = new Gson().fromJson(jsonSong, Song.class);
                    activity.updateViewInformationFor(songStreaming);
                    if (isStreaming) {
                        playSong(songStreaming);
                    }
                }
            }
        }).start();
    }

    @Override
    public void next() {
        Log.d(this.getClass().getName(), "nextButtonClick");
        new Thread(new Runnable(){
            @Override
            public void run() {
                String jsonSong = sendToServerCommand(ServerCommand.NEXT);
                if( checkResponse(jsonSong) ) {
                    songStreaming = new Gson().fromJson(jsonSong, Song.class);
                    activity.updateViewInformationFor(songStreaming);
                    if (isStreaming) {
                        playSong(songStreaming);
                    }
                }
            }
        }).start();
    }

    @Override
    public void repeat() {
        Log.d(this.getClass().getName(), "repeatButtonClick");
        new Thread(new Runnable(){
            @Override
            public void run() {
                if (isStreaming) {
                    mediaPlayer.setLooping(!mediaPlayer.isLooping());
                }
                else {
                    Boolean isLooping = Boolean.valueOf(sendToServerCommand(ServerCommand.LOOP));
                }
            }
        }).start();
    }

    @Override
    public void shuffle() {
        Log.d(this.getClass().getName(), "shuffleButtonClick");
        new Thread(new Runnable(){
            @Override
            public void run() {
                String response = sendToServerCommand(ServerCommand.SHUFFLE);
                if( checkResponse(response) )
                {
                    Boolean isShuffled = Boolean.valueOf(response);
                }
            }
        }).start();
    }

    @Override
    public void seekTo(int mSec) {
        if (isStreaming) {
            super.seekTo(mSec);
        }
        else {
            sendToServerCommand(ServerCommand.SEEK);
        }
    }

    @Override
    public void toggleStreamMusicState() {
        Log.d(this.getClass().getName(), "shuffleButtonClick");
        isStreaming = !isStreaming;
        if(isStreaming) {
            updateStreamingText(R.string.mode_streaming);
            initialiseMusicPlayerForStreaming(ServerCommand.SONG);
        }
        else {
            updateStreamingText(R.string.mode_command);
            releaseMediaPlayer();
        }
    }

    private void askForServerIpAddress(final MainActivity activity) {
        final EditText editText = new EditText(activity);

        String ipAddr = activity.getString(R.string.default_server_ip_address);
        String port = activity.getString(R.string.default_server_port);

        // Retrieve data from the preferences to display it
        SharedPreferences prefs = activity.getSharedPreferences("MyPrefsSettings", MODE_PRIVATE);
        String restoredText = prefs.getString("ipAddr", null);
        if (restoredText != null) {
            ipAddr = prefs.getString("ipAddr", ipAddr);
            port = prefs.getString("port", port);
        }

        editText.setText(ipAddr + ":" + port, TextView.BufferType.EDITABLE);

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(R.string.ip_address_required);
        alertDialog.setMessage(activity.getResources().getString(R.string.ask_for_server_ip_address));
        alertDialog.setView(editText);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        serverIpAddress = editText.getText().toString();
                        String[] tab = serverIpAddress.split(":");
                        String ipAddr = tab[0];
                        String port = tab[1];

                        // Set preferences for server ip and port
                        SharedPreferences.Editor editor = activity.getSharedPreferences("MyPrefsSettings", MODE_PRIVATE).edit();
                        editor.putString("ipAddr", ipAddr);
                        editor.putString("port", port);
                        editor.apply();

                        client = new ClientHTTP(ipAddr, port);
                        initialise(activity);
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, activity.getResources().getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void displayMessage(final int message)
    {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(activity, message, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private String sendToServerCommand(String command)
    {
        return client.run(command);
    }

    private void updateMode(final int mode)
    {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                streamingText = activity.findViewById(R.id.streamingText);
                streamingText.setText(mode);
                preparingText = activity.findViewById(R.id.preparingText);
            }
        });
    }

    private void updateViewInformationFor(String jsonSong) {
        Song currentSong = new Gson().fromJson(jsonSong, Song.class);
        activity.updateViewInformationFor(currentSong);
    }

    private void updateStreamingText(int streamTextId) {
        activity.updateStreamingText(streamTextId);
    }

    private void updatePreparingText(int preparingTextId) {
        activity.updatePrepareText(preparingTextId);
    }

//    @Override
//    public void onCompletion(MediaPlayer mp) {
//        playSong(songStreaming);
//        visualizer.setEnabled(false);
//    }

//    @Override
//    public void onPrepared(MediaPlayer mp) {
//        mp.start();
//    }
//
//    @Override
//    public boolean onError(MediaPlayer mp, int what, int extra) {
////        Toast.makeText(activity, "Error preparing stream", Toast.LENGTH_LONG).show();
//        return false;
//    }
}

package com.example.plcus.multimedia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.IOException;

public class ClientMusicPlayer extends MusicPlayer {

    private ClientHTTP client;

    public ClientMusicPlayer(MainActivity activity) {
        askForServerIpAddress(activity);
    }

    @Override
    protected void initialise(MainActivity activity) {
        this.activity = activity;
        new Thread(new Runnable(){
            @Override
            public void run() {
                String jsonSong = sendToServerCommand(ServerCommand.SONG);
                updateViewInformationFor(jsonSong);
            }
        }).start();
    }

    @Override
    public void playOrPause() {
        Log.d(this.getClass().getName(), "playButtonClick");
        new Thread(new Runnable(){
            @Override
            public void run() {
                String jsonSong = sendToServerCommand(ServerCommand.PLAY_OR_PAUSE);
                updateViewInformationFor(jsonSong);
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
                updateViewInformationFor(jsonSong);
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
                updateViewInformationFor(jsonSong);
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
                updateViewInformationFor(jsonSong);
            }
        }).start();
    }

    @Override
    public void repeat() {
        Log.d(this.getClass().getName(), "repeatButtonClick");
        new Thread(new Runnable(){
            @Override
            public void run() {
                sendToServerCommand(ServerCommand.LOOP);
            }
        }).start();
    }

    @Override
    public void shuffle() {
        Log.d(this.getClass().getName(), "shuffleButtonClick");
        new Thread(new Runnable(){
            @Override
            public void run() {
                sendToServerCommand(ServerCommand.SHUFFLE);
            }
        }).start();
    }

    private void askForServerIpAddress(final MainActivity activity) {
        final EditText editText = new EditText(activity);
        editText.setText(R.string.default_server_ip_address, TextView.BufferType.EDITABLE);

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(R.string.ip_address_required);
        alertDialog.setMessage(activity.getResources().getString(R.string.ask_for_server_ip_address));
        alertDialog.setView(editText);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String serverIpAddress = editText.getText().toString();
                        client = new ClientHTTP(serverIpAddress);
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

    private String sendToServerCommand(String command)
    {
        try {
            return client.run(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateViewInformationFor(String jsonSong) {
        Song currentSong = new Gson().fromJson(jsonSong, Song.class);
        activity.updateViewInformationFor(currentSong);
    }
}

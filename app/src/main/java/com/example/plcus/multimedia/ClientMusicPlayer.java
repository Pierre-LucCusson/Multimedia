package com.example.plcus.multimedia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class ClientMusicPlayer extends MusicPlayer {

    private String serverIpAddress;

    public ClientMusicPlayer(MainActivity activity) {
        askForServerIpAddress(activity);
    }

    @Override
    protected void initialise(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void playOrPause() {
        Log.d(this.getClass().getName(), "playButtonClick");
    }

    @Override
    public void stop() {
        Log.d(this.getClass().getName(), "stopButtonClick");
    }

    @Override
    public void previous() {
        Log.d(this.getClass().getName(), "previousButtonClick");
    }

    @Override
    public void next() {
        Log.d(this.getClass().getName(), "nextButtonClick");
    }

    @Override
    public void repeat() {
        Log.d(this.getClass().getName(), "repeatButtonClick");
    }

    @Override
    public void shuffle() {
        Log.d(this.getClass().getName(), "shuffleButtonClick");
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
                        serverIpAddress = editText.getText().toString();
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
}

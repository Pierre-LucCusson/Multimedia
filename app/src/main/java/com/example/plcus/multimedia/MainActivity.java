package com.example.plcus.multimedia;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private ImageButton previousButton;
    private ImageButton playButton;
    private ImageButton stopButton;
    private ImageButton nextButton;
    private ImageButton shuffleButton;
    private ImageButton repeatButton;

    private TextView songText;
    private TextView artistText;
    private TextView albumText;

    private MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        songText = findViewById(R.id.songText);
        artistText = findViewById(R.id.artistText);
        albumText = findViewById(R.id.albumText);

        initPreviousButton();
        initPlayButton();
        initStopButton();
        initNextButton();
        initShuffleButton();
        initRepeatButton();

        musicPlayer = new LocalMusicPlayer();
        musicPlayer.initialise(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_local) {
            setMusicPlayerToLocal();
            return true;
        }
        else if (id == R.id.action_client) {
            setMusicPlayerToClient();
            return true;
        }
        else if (id == R.id.action_server) {
            setMusicPlayerToServer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRepeatButton() {
        repeatButton = findViewById(R.id.repeatButton);
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumText.setText("repeat");
                musicPlayer.repeat();
            }
        });
    }

    private void initShuffleButton() {
        shuffleButton = findViewById(R.id.shuffleButton);
        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumText.setText("shuffle");
                musicPlayer.shuffle();
            }
        });
    }

    private void initNextButton() {
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumText.setText("next");
                musicPlayer.next();
            }
        });
    }

    private void initStopButton() {
        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumText.setText("stop");
                musicPlayer.stop();
            }
        });
    }

    private void initPlayButton() {
        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumText.setText("play");
                musicPlayer.playOrPause();
            }
        });
    }

    private void initPreviousButton() {
        previousButton = findViewById(R.id.previousButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumText.setText("previous");
                musicPlayer.previous();
            }
        });
    }

    private void setMusicPlayerToClient(){
        musicPlayer = new ClientMusicPlayer();
        musicPlayer.initialise(this);
    }

    private void setMusicPlayerToLocal(){
        musicPlayer = new LocalMusicPlayer();
        musicPlayer.initialise(this);
    }

    private void setMusicPlayerToServer(){
        musicPlayer = new ServerMusicPlayer();
        musicPlayer.initialise(this);
    }
}

package com.example.plcus.multimedia;

import android.media.AudioManager;
import android.media.MediaPlayer;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        songText = (TextView)findViewById(R.id.songText);
        artistText = (TextView)findViewById(R.id.artistText);
        albumText = (TextView)findViewById(R.id.albumText);

        initPreviousButton();
        initPlayButton();
        initStopButton();
        initNextButton();
        initShuffleButton();
        initRepeatButton();

        musicPlayer = new MusicPlayer();

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

        return super.onOptionsItemSelected(item);
    }

    private void initRepeatButton() {
        repeatButton = (ImageButton)findViewById(R.id.repeatButton);
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(this.getClass().getName(), "repeatButtonClick");
                albumText.setText("repeat");
            }
        });
    }

    private void initShuffleButton() {
        shuffleButton = (ImageButton)findViewById(R.id.shuffleButton);
        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(this.getClass().getName(), "shuffleButtonClick");
                albumText.setText("shuffle");
            }
        });
    }

    private void initNextButton() {
        nextButton = (ImageButton)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(this.getClass().getName(), "nextButtonClick");
                albumText.setText("next");
            }
        });
    }

    private void initStopButton() {
        stopButton = (ImageButton)findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(this.getClass().getName(), "stopButtonClick");
                albumText.setText("stop");
            }
        });
    }

    private void initPlayButton() {
        playButton = (ImageButton)findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(this.getClass().getName(), "playButtonClick");
                albumText.setText("play");
                musicPlayer.play();
                playTest();
            }
        });
    }

    private void initPreviousButton() {
        previousButton = (ImageButton)findViewById(R.id.previousButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(this.getClass().getName(), "previousButtonClick");
                albumText.setText("previous");
            }
        });
    }

    private void playTest() {
//        //                MediaPlayer mediaPlayer = new MediaPlayer();
//
////                MediaPlayer mediaPlayer;
//
//        MediaPlayer mediaPlayer = new MediaPlayer();
//
//        try {
//
//            String filename = "android.resource://" + getPackageName() + "/raw/test0";
//
//            URI uri = URI.create("C:/Users/plcus/Music/Good Life.mp3");
//            int id = getResources().getIdentifier("test0", "raw", getPackageName());
//
//            mediaPlayer.setDataSource(getApplicationContext(), uri);
//
////                    mediaPlayer = MediaPlayer.create();
//
////                    mediaPlayer = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier("Good Life.mp3","raw", "C:\\Users\\plcus\\Music"));
////                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
////                    mediaPlayer.setDataSource("C:\\Users\\plcus\\Music\\Good Life.mp3");
//            mediaPlayer.prepare();
//            mediaPlayer.setVolume(1,1);
//            mediaPlayer.start();
//            albumText.setText("AAAAAAAAAAA");
//        } catch (IOException e) {
//            e.printStackTrace();
//            albumText.setText("crash");
//        }
    }
}

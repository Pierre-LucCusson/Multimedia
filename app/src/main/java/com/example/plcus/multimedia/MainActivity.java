package com.example.plcus.multimedia;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

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
    private TextView progressionTimeText;
    private TextView totalTimeText;

    private SeekBar timeSeekBar;
    private Handler timeSeekBarHandler = new Handler();

    private BackgroundPlayerService playerService;
    private MusicPlayer musicPlayer;


    public ServiceConnection myConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder) {
            playerService = ((BackgroundPlayerService.MyBinder) binder).getService();
            Log.d("ServiceConnection","connected");
            playerService.initialize(MainActivity.this, MusicPlayerType.LOCAL);
            musicPlayer = playerService.getMusicPlayer();

            initPreviousButton();
            initPlayButton();
            initStopButton();
            initNextButton();
            initShuffleButton();
            initRepeatButton();
            initTimeSeekBar();
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d("ServiceConnection","disconnected");
            playerService = null;
        }
    };

    public void doBindService() {
        Intent intent = new Intent(this, BackgroundPlayerService.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        songText = findViewById(R.id.songText);
        artistText = findViewById(R.id.artistText);
        albumText = findViewById(R.id.albumText);

        progressionTimeText = findViewById(R.id.progressionTimeText);
        totalTimeText = findViewById(R.id.totalTimeText);

        if (playerService == null) {
            doBindService();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerService != null) {
            unbindService(myConnection);
            playerService = null;
            musicPlayer = null;
        }
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
                musicPlayer.repeat();
            }
        });
    }

    private void initShuffleButton() {
        shuffleButton = findViewById(R.id.shuffleButton);
        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.shuffle();
            }
        });
    }

    private void initNextButton() {
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.next();
            }
        });
    }

    private void initStopButton() {
        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.stop();
                playButton.setImageResource(android.R.drawable.ic_media_play);
            }
        });
    }

    private void initPlayButton() {
        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.playOrPause();
                if(musicPlayer.isPlaying()) {
                    playButton.setImageResource(android.R.drawable.ic_media_pause);
                }
                else {
                    playButton.setImageResource(android.R.drawable.ic_media_play);
                }
            }
        });
    }

    private void initPreviousButton() {
        previousButton = findViewById(R.id.previousButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.previous();
            }
        });
    }

    private void initTimeSeekBar() {

        timeSeekBar = findViewById(R.id.timeSeekBar);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean bool) {
                if (bool)
                {
                    musicPlayer.seekTo(i * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(!musicPlayer.isMediaPlayerNull()){

                    int currentTime = musicPlayer.getMediaPlayerCurrentPosition() / 1000;
                    int totalTime = musicPlayer.getMediaPlayerDuration() / 1000;

                    timeSeekBar.setMax(totalTime);
                    timeSeekBar.setProgress(currentTime);

                    progressionTimeText.setText(String.format("%2d:%2d", currentTime / 60, currentTime % 60));
                    totalTimeText.setText(String.format("%2d:%2d", totalTime / 60, totalTime % 60));
                }
                timeSeekBarHandler.postDelayed(this, 1000);
            }
        });
    }

    private void setMusicPlayerToClient(){
        musicPlayer.releaseMediaPlayer();
        musicPlayer = new ClientMusicPlayer();
        musicPlayer.initialise(this);
    }

    private void setMusicPlayerToLocal(){
        musicPlayer.releaseMediaPlayer();
        musicPlayer = new LocalMusicPlayer();
        musicPlayer.initialise(this);
    }

    private void setMusicPlayerToServer(){
        musicPlayer.releaseMediaPlayer();
        musicPlayer = new ServerMusicPlayer();
        musicPlayer.initialise(this);
    }

    public void updateTextFor(Song song) {
        songText.setText(song.getTitle());
        artistText.setText(song.getArtist());
        albumText.setText(song.getAlbum());
        totalTimeText.setText(song.getLength());
    }
}

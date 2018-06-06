package com.example.plcus.multimedia;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BackgroundPlayerService extends Service {
    MusicPlayer musicPlayer;

    private IBinder mBinder = new MyBinder();

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    public class MyBinder extends Binder {
        BackgroundPlayerService getService() {
            return BackgroundPlayerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void initialize(MainActivity activity, MusicPlayerType playerType)
    {
        switch ( playerType ) {
            case LOCAL:
                musicPlayer = new LocalMusicPlayer();
                break;
            case CLIENT:
                musicPlayer = new ClientMusicPlayer(activity);
                break;
            case SERVER:
                musicPlayer = new ServerMusicPlayer();
                break;
        }

        musicPlayer.initialise(activity);
    }

    @Override
    public void onDestroy() {
        musicPlayer.stop();
        musicPlayer.releaseMediaPlayer();
    }

    @Override
    public void onLowMemory() {

    }
}

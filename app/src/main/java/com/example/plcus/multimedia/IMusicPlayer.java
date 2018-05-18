package com.example.plcus.multimedia;

public interface IMusicPlayer {

    void playOrPause();

    void stop();

    void previous();

    void next();

    void repeat();

    void shuffle();

    void seekTo(int mSec);

}

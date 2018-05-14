package com.example.plcus.multimedia;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private AppCompatActivity activity;
    private List<Song> songs;
    private int songIndex;

    public Playlist(AppCompatActivity activity) {

        this.activity = activity;
        songs = new ArrayList<>();
        //TODO add more songs
        songs.add(new Song(activity, R.raw.good_life));

        songIndex = 0;
    }

    public Song getCurrentSong() {
        return songs.get(songIndex);
    }

    public Song getPreviousSong() {
        if (songIndex == 0) {
            songIndex = songs.size() - 1;
        }
        else {
            songIndex--;
        }
        return songs.get(songIndex);
    }
    public Song getNextSong() {
        if (songIndex == songs.size() - 1) {
            songIndex = 0;
        }
        else {
            songIndex++;
        }
        return songs.get(songIndex);
    }

    public void shuffle() {
        //TODO shuffle list of songs
    }
}

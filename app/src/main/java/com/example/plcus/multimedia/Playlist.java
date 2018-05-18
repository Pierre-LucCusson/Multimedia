package com.example.plcus.multimedia;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {

    private AppCompatActivity activity;
    private List<Song> songs;
    private int indexOfSongPlaying;

    public Playlist(AppCompatActivity activity) {

        this.activity = activity;
        songs = new ArrayList<>();
        //TODO add more songs
        songs.add(new Song(activity, R.raw.good_life));

        indexOfSongPlaying = 0;
    }

    public Song getCurrentSong() {
        return songs.get(indexOfSongPlaying);
    }

    public Song getPreviousSong() {
        if (indexOfSongPlaying == 0) {
            indexOfSongPlaying = songs.size() - 1;
        }
        else {
            indexOfSongPlaying--;
        }
        return songs.get(indexOfSongPlaying);
    }
    public Song getNextSong() {
        if (indexOfSongPlaying == songs.size() - 1) {
            indexOfSongPlaying = 0;
        }
        else {
            indexOfSongPlaying++;
        }
        return songs.get(indexOfSongPlaying);
    }

    public void shuffle() {
        Collections.shuffle(songs);
    }
}

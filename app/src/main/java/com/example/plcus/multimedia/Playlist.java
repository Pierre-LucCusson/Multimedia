package com.example.plcus.multimedia;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private List<Integer> songs;
    public int songIndex;

    public Playlist() {
        songs = new ArrayList<>();
        songs.add(R.raw.good_life);
        //TODO add more songs

        songIndex = 0;
    }

    public int getCurrentSong() {
        return songs.get(songIndex);
    }

    public int getPreviousSong() {
        if (songIndex == 0) {
            songIndex = songs.size() - 1;
        }
        else {
            songIndex--;
        }
        return songs.get(songIndex);
    }
    public int getNextSong() {
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

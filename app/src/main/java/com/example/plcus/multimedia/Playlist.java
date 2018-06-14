package com.example.plcus.multimedia;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Playlist {

    private AppCompatActivity activity;
    private List<Song> songs;
    private int indexOfSongPlaying;
    private boolean isShuffled;

    public Playlist(AppCompatActivity activity) {

        this.activity = activity;
        songs = new ArrayList<>();
        setDefaultSongPlaylist();
    }

//    public Playlist(String jsonPlaylist) {
//        //TODO URI makes Gson crash
//        songs = Arrays.asList(new Gson().fromJson(jsonPlaylist, Song[].class));
//    }

    private void setDefaultSongPlaylist() {
        songs.clear();

        File[] audioFiles = getAudioFiles();
        if(audioFiles.length == 0) {
            songs.add(new Song(activity, R.raw.good_life));
            songs.add(new Song(activity, R.raw.cool_girl));
            Toast.makeText(activity, R.string.music_folder_empty, Toast.LENGTH_LONG).show();
        }
        else {
            for (File audioFile : audioFiles) {
                if(audioFile.getName().toLowerCase().endsWith(".mp3")) {
                    songs.add(new Song(activity, audioFile));
                }
            }
        }

        indexOfSongPlaying = 0;
        isShuffled = false;
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
        if(isShuffled) {
            setDefaultSongPlaylist();
            isShuffled = false;
        }
        else {
            Collections.shuffle(songs);
            indexOfSongPlaying = 0;
            isShuffled = true;
        }
    }

    public void setShuffle(Boolean isShuffled) {
        if (this.isShuffled != isShuffled) {
            shuffle();
        }
    }

    public String toJson() {
        return new Gson().toJson(songs);
    }

    public Boolean isShuffled() {
        return isShuffled;
    }

    private File[] getAudioFiles() {
        File musicFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) +
                File.separator + activity.getResources().getString(R.string.app_name));

        createMusicFolderIfItDoesNotExist(musicFolder);
        return musicFolder.listFiles();
    }

    private void createMusicFolderIfItDoesNotExist(File musicFolder) {
        if (!musicFolder.exists()) {
            if(!musicFolder.mkdir()) {
                Toast.makeText(activity, R.string.music_folder_not_created, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setIpAddressToSongs(String ipAddress) {
        for (Song song : songs) {
            song.setUrl(ipAddress);
        }
    }
}

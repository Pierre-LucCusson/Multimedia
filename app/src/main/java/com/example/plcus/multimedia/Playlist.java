package com.example.plcus.multimedia;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
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
    private void setDefaultSongPlaylist() {
        songs.clear();
        songs.add(new Song(activity, R.raw.good_life));

        File musicFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) +
                File.separator + activity.getResources().getString(R.string.app_name));

        if (!musicFolder.exists()) {
            if(!musicFolder.mkdir()) {
                Toast.makeText(activity, R.string.music_folder_not_created, Toast.LENGTH_LONG).show();
                return;
            }
        }

        //TODO add more songs
        //DOES NOT WORK YET

        File[] audioFiles = musicFolder.listFiles();
        if(audioFiles.length == 0) {
            Toast.makeText(activity, R.string.music_folder_empty, Toast.LENGTH_LONG).show();
        }
        else {
            //get all music
        }



//        File musicDirectory = new File(String.valueOf(activity.getExternalFilesDir(Environment.DIRECTORY_MUSIC)));
//        String name = musicDirectory.getName();
//        String path = musicDirectory.getAbsolutePath();
//        URI dirUri = musicDirectory.toURI();
//        File[] files = musicDirectory.listFiles();

        
//        AssetFileDescriptor afd = getAssets().openFd("AudioFile.mp3");

//        activity.getAssets().

//        ContentResolver contentResolver = activity.getContentResolver();
//        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
//        Cursor songCursor = contentResolver.query(songUri, null, selection, null, null);
//
//        if(songCursor != null && songCursor.moveToFirst())
//        {
//            int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
//            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
//
//            do {
//                long currentId = songCursor.getLong(songId);
//                String currentTitle = songCursor.getString(songTitle);
//                arrayList.add(new Songs(currentId, currentTitle, currentArtist));
//            } while(songCursor.moveToNext());
//        }




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
        }
        else {
            Collections.shuffle(songs);
            indexOfSongPlaying = 0;
        }

    }
}

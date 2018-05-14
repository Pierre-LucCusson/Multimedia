package com.example.plcus.multimedia;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

class Song {

    private int id;
    private String title;
    private String artist;
    private String album;
    private String length;

    public Song(AppCompatActivity activity, int id) {

        this.id = id;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Uri uri = Uri.parse("android.resource://" + activity.getPackageName() + "/" + id);
        retriever.setDataSource(activity, uri);

        title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);
        album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        length = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getLength() {
        return length;
    }
}

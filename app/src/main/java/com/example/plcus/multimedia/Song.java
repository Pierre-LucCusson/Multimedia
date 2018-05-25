package com.example.plcus.multimedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

class Song {

//    private int id;
    private Uri uri;
    private String title;
    private String artist;
    private String album;
    private String length;
    private Bitmap albumImage;

    public Song(AppCompatActivity activity, int id) {
//        this.id = id;
        this(activity, Uri.parse("android.resource://" + activity.getPackageName() + "/" + id));
    }

    public Song(AppCompatActivity activity, File file) {
        this(activity, Uri.fromFile(file));
    }

    public Song(AppCompatActivity activity, Uri uri) {

        this.uri = uri;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(activity, uri);

        title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);
        album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        length = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

        byte[] picture = retriever.getEmbeddedPicture();
        if( picture != null ){
            albumImage = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        }

        retriever.release();
    }

//    public int getId() {
//        return id;
//    }

    public Uri getUri() {
        return uri;
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

    public Bitmap getAlbumImage() {
        return albumImage;
    }
}

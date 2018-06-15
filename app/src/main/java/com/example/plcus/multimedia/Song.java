package com.example.plcus.multimedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

class Song {

//    private int id;
    private transient Uri uri;
    private String title;
    private String artist;
    private String album;
    private String length;
    private byte[] albumImage;
    private String path;
    private File file;
    private String url;

    public Song(AppCompatActivity activity, int id) {
//        this.id = id;
        this(activity, Uri.parse("android.resource://" + activity.getPackageName() + "/" + id));
        file =  new File(uri.getPath());
    }

    public Song(AppCompatActivity activity, File file) {
        this(activity, Uri.fromFile(file));
        this.file = file;
    }

    public Song(AppCompatActivity activity, Uri uri) {

        this.uri = uri;
        path = uri.getPath();

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(activity, uri);

        title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);
        album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        length = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        albumImage = retriever.getEmbeddedPicture();

        retriever.release();
    }

    public String toJson() {
        return new Gson().toJson(this);
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

    public Bitmap getAlbumImageInBitMap() {
        if(albumImage != null) {
            return BitmapFactory.decodeByteArray(albumImage, 0, albumImage.length);
        }
        return null;
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public InputStream getInputStream(){

        try {
            return new FileInputStream(file);
        }catch (Exception e) {

        }
        return null;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String serverIpAddress) {
        url = "http://" + serverIpAddress + ":8080" + ServerCommand.INPUT_STREAM;
    }
}

package com.example.plcus.multimedia;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class Song {

//    private int id;
    private transient Uri uri; //TODO this field is ignored when converted toJson because is currently crash
    private String title;
    private String artist;
    private String album;
    private String length;
    private Bitmap albumImage; //TODO
    private String path;
    private String mime;
    private File file;

    public Song(AppCompatActivity activity, int id) {
//        this.id = id;
        this(activity, Uri.parse("android.resource://" + activity.getPackageName() + "/" + id));
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
        mime = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);

        byte[] picture = retriever.getEmbeddedPicture();
        if( picture != null ){
            albumImage = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        }

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

    public Bitmap getAlbumImage() {
        return albumImage;
    }

    public String getPath() {
        return path;
    }

    public String getMime() {
        return mime;
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

    public FileDescriptor getFileDescriptor() {
        try {
            return new FileOutputStream(file).getFD();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

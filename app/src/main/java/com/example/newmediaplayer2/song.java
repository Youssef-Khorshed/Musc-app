package com.example.newmediaplayer2;

import java.io.Serializable;

public class song implements Serializable {
    String title, artist, data,albums;

    public song(String title, String artist, String data, String albums) {
        this.title = title;
        this.artist = artist;
        this.data = data;
        this.albums = albums;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAlbums() {
        return albums;
    }

    public void setAlbums(String albums) {
        this.albums = albums;
    }
}

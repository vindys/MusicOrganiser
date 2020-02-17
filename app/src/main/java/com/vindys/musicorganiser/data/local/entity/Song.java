package com.vindys.musicorganiser.data.local.entity;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "song")
public class Song {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Artist")
    private String artist;
    @SerializedName("Album")
    private String album;

    public Song(int id, String name, String artist, String album) {
        this.name = name;
        this.artist = artist;
        this.album = album;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}

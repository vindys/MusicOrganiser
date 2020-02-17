package com.vindys.musicorganiser.data.local.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "music_list")
public class MusicList {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("data")
    private List<Song> songs;

    public List<Song> getSongs() {
        return songs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public MusicList(int id, List<Song> songs) {
        this.id = id;
        this.songs = songs;
    }
}

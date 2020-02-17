package com.vindys.musicorganiser.data.model;

import com.vindys.musicorganiser.data.local.entity.Song;

import java.util.List;

public class SortedSongs {
    private String name;
    private List<Song> songName;

    public SortedSongs(String name, List<Song> songName) {
        this.name = name;
        this.songName = songName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongName() {
        return songName;
    }

    public void setSongName(List<Song> songName) {
        this.songName = songName;
    }
}

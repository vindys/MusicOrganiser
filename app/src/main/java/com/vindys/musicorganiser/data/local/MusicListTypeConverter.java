package com.vindys.musicorganiser.data.local;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vindys.musicorganiser.data.local.entity.Song;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class MusicListTypeConverter {

    @TypeConverter // note this annotation
    public String fromWeatherResponseList(List<Song> songs) {
        if (songs == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Song>>() {
        }.getType();
        String json = gson.toJson(songs, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Song> toSongsList(String songString) {
        if (songString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Song>>() {
        }.getType();
        List<Song> songs = gson.fromJson(songString, type);
        return songs;
    }
}

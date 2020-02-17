package com.vindys.musicorganiser.data.local;

import com.vindys.musicorganiser.data.local.dao.SongsDao;
import com.vindys.musicorganiser.data.local.entity.MusicList;
import com.vindys.musicorganiser.data.local.entity.Song;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Song.class, MusicList.class},version = 1,exportSchema = false)
@TypeConverters(MusicListTypeConverter.class)
public abstract class SongsDatabase extends RoomDatabase {
    public abstract SongsDao songDao();
}

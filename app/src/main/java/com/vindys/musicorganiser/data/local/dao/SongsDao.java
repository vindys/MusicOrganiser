package com.vindys.musicorganiser.data.local.dao;

import com.vindys.musicorganiser.data.local.entity.MusicList;
import com.vindys.musicorganiser.data.local.entity.Song;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

/**
 *
 */
@Dao
public abstract class SongsDao {

    @Transaction
    @Query("SELECT * FROM music_list")
    abstract public LiveData<MusicList> getMusicList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract public void insertSong(Song song);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract public void insertMusicList(MusicList musicList);

    @Query("Select count(id) from song")
    abstract Integer getSongCount();

    @Delete
    abstract public void deleteSong(Song... items);

    @Query("Delete from music_list")
    abstract public void deleteAllSongs();

    @Transaction
    public long updateData(Song song) {
        deleteAllSongs();
        insertSong(song);
        return getSongCount();
    }
}

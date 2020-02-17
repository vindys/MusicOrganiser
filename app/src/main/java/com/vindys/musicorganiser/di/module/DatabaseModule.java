package com.vindys.musicorganiser.di.module;

import android.content.Context;

import com.vindys.musicorganiser.data.local.SongsDatabase;
import com.vindys.musicorganiser.data.local.dao.SongsDao;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;


/**
 *
 */
@Module
public class DatabaseModule {

    @Provides
    @Singleton
    SongsDatabase provideAppDatabase(@NonNull Context context) {
        return Room.databaseBuilder(context, SongsDatabase.class, "weather_db")
                .build();
    }

    @Provides
    @Singleton
    static SongsDao provideSongDao(@NonNull SongsDatabase appDatabase) {
        return appDatabase.songDao();
    }
}

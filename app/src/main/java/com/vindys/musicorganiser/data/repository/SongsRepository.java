package com.vindys.musicorganiser.data.repository;


import com.vindys.musicorganiser.data.NetworkBoundResource;
import com.vindys.musicorganiser.data.Resource;
import com.vindys.musicorganiser.data.local.dao.SongsDao;
import com.vindys.musicorganiser.data.local.entity.MusicList;
import com.vindys.musicorganiser.data.local.entity.Song;
import com.vindys.musicorganiser.data.remote.SongsAppService;
import com.vindys.musicorganiser.utils.AppExecutors;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/**
 * Created by vindys
 */

public class SongsRepository {

    private static final String TAG = "SongsRepository";
    private final AppExecutors executors;
    private SongsDao songsDao;
    private SongsAppService songsAppService;

    @Inject
    public SongsRepository(AppExecutors executors, SongsDao songsDao, SongsAppService songsAppService) {
        this.executors = executors;
        this.songsDao = songsDao;

        this.songsAppService = songsAppService;
        this.songsDao = songsDao;

    }

    public LiveData<Resource<MusicList>> getSongs() {
        return new NetworkBoundResource<MusicList, MusicList>(executors) {

            @Override
            protected void saveCallResult(@NonNull MusicList items) {
                songsDao.deleteAllSongs();
                songsDao.insertMusicList(items);
            }

            @Override
            protected boolean shouldFetch(@Nullable MusicList data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<MusicList> loadFromDb() {
                return songsDao.getMusicList();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MusicList>> createCall() {
                return songsAppService.getSongs();
            }
        }.asLiveData();
    }
}

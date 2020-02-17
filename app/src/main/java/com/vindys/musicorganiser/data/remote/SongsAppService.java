package com.vindys.musicorganiser.data.remote;

import com.vindys.musicorganiser.data.local.entity.MusicList;
import com.vindys.musicorganiser.data.repository.ApiResponse;

import androidx.lifecycle.LiveData;
import retrofit2.http.GET;

public interface SongsAppService {
    String HTTPS_API_ALBUM_URL = "https://api.myjson.com/bins/";

    @GET("rov51/")
    LiveData<ApiResponse<MusicList>> getSongs();
}

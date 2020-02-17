package com.vindys.musicorganiser.di.module;

import com.vindys.musicorganiser.data.remote.LiveDataCallAdapterFactory;
import com.vindys.musicorganiser.data.remote.SongsAppService;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

@Module()
public class AppModule {


    @Singleton
    @Provides
    @EverythingIsNonNull
    static SongsAppService provideSampleAppService() {
        return new Retrofit.Builder()
                .baseUrl(SongsAppService.HTTPS_API_ALBUM_URL)
                .client(
                        new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                HttpUrl url = request.url().newBuilder()

                                        .build();
                                request = request.newBuilder().url(url).build();
                                return chain.proceed(request);
                            }
                        }).build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(SongsAppService.class);
    }


}

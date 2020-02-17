package com.vindys.musicorganiser.di.module;

import com.vindys.musicorganiser.data.repository.SongsRepository;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DataSourceModule {
    @Binds
    abstract SongsRepository provideRepoImpl(SongsRepository repo);
}

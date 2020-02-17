package com.vindys.musicorganiser.di.module;


import com.vindys.musicorganiser.factory.ViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);

}

package com.vindys.musicorganiser.di.module;

import android.content.Context;

import com.vindys.musicorganiser.di.ViewModelKey;
import com.vindys.musicorganiser.di.scope.ActivityContext;
import com.vindys.musicorganiser.main.MainActivity;
import com.vindys.musicorganiser.main.viewmodel.SongsViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainActivityModule {



    @ContributesAndroidInjector()
    abstract MainActivity providesMainActivity();

    @Binds
    @ActivityContext
    public abstract Context bindContext(Context mainActivity) ;


    @Binds
    @IntoMap
    @ViewModelKey(SongsViewModel.class)
    public abstract ViewModel bindMainViewModel(SongsViewModel viewModel);
}

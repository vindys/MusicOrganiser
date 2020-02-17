package com.vindys.musicorganiser;

import com.vindys.musicorganiser.di.component.AppComponent;
import com.vindys.musicorganiser.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class MainApplication extends DaggerApplication{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).context(this).build();
        appComponent.inject(this);
        return appComponent;
    }

}

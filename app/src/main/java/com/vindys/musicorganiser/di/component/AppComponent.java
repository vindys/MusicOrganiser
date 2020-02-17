package com.vindys.musicorganiser.di.component;


import android.app.Application;
import android.content.Context;

import com.vindys.musicorganiser.MainApplication;
import com.vindys.musicorganiser.di.module.AppModule;
import com.vindys.musicorganiser.di.module.DatabaseModule;
import com.vindys.musicorganiser.di.module.MainActivityModule;
import com.vindys.musicorganiser.di.module.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        AppModule.class,
        MainActivityModule.class,
        DatabaseModule.class,
        ViewModelFactoryModule.class})
public interface AppComponent extends AndroidInjector<MainApplication> {
    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder context(Context context);
    }
}
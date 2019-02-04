package es.jescuderv.unex.facetrackernearbytfg;

import android.app.Service;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasServiceInjector;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.DaggerAppComponent;

public class App extends DaggerApplication implements HasServiceInjector{

    private static App sInstance;

    @Inject DispatchingAndroidInjector<Service> dispatchingServiceInjector;

    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @Override
    public DispatchingAndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }

    public static synchronized App getInstance() {
        return sInstance;
    }
}

package es.jescuderv.unex.facetrackernearbytfg.utils.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import es.jescuderv.unex.facetrackernearbytfg.ui.activity.AdvertiserActivity;
import es.jescuderv.unex.facetrackernearbytfg.ui.activity.DiscovererActivity;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = AdvertiserModule.class)
    abstract AdvertiserActivity advertiserActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = DiscovererModule.class)
    abstract DiscovererActivity discovererActivity();
}

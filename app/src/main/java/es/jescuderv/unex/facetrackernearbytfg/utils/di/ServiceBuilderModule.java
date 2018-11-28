package es.jescuderv.unex.facetrackernearbytfg.utils.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import es.jescuderv.unex.facetrackernearbytfg.service.AdvertiseService;
import es.jescuderv.unex.facetrackernearbytfg.service.DiscoverService;

@Module
abstract class ServiceBuilderModule {

    @ContributesAndroidInjector
    abstract DiscoverService injectDiscoverService();

    @ContributesAndroidInjector
    abstract AdvertiseService injectAdvertiserService();
}

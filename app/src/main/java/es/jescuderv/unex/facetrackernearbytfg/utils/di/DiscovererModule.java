package es.jescuderv.unex.facetrackernearbytfg.utils.di;

import dagger.Binds;
import dagger.Module;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.DiscovererContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.DiscoverPresenter;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;

@Module
abstract class DiscovererModule {

    @Binds
    @ActivityScoped
    abstract DiscovererContract.Presenter provideDiscvererPresenter(DiscoverPresenter discoverPresenter);
}

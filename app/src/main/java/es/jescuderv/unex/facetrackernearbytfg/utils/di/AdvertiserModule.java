package es.jescuderv.unex.facetrackernearbytfg.utils.di;

import dagger.Binds;
import dagger.Module;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.AdvertiserPresenter;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;

@Module
abstract class AdvertiserModule {

    @Binds
    @ActivityScoped
    abstract AdvertiserContract.Presenter presenter(AdvertiserPresenter advertiserPresenter);
}

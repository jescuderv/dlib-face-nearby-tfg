package es.jescuderv.unex.facetrackernearbytfg.utils.di;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserMainInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.fragment.AdvertiserMainInfoFragment;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.AdvertiserMainInfoPresenter;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.AdvertiserPresenter;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.FragmentScoped;

@Module
abstract class AdvertiserModule {

    @Binds
    @ActivityScoped
    abstract AdvertiserContract.Presenter presenter(AdvertiserPresenter advertiserPresenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AdvertiserMainInfoFragment advertiserMainInfoFragment();

    @Binds
    @ActivityScoped
    abstract AdvertiserMainInfoContract.Presenter mainInfoPresenter(AdvertiserMainInfoPresenter presenter);
}

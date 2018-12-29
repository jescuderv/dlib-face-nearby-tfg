package es.jescuderv.unex.facetrackernearbytfg.utils.di;

import dagger.Binds;
import dagger.Module;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.SettingsContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.SettingsPresenter;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;

@Module
abstract class SettingsModule {

    @Binds
    @ActivityScoped
    abstract SettingsContract.Presenter provideSettingsPresenter(SettingsPresenter presenter);

}

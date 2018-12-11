package es.jescuderv.unex.facetrackernearbytfg.utils.di;

import dagger.Binds;
import dagger.Module;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.PersonalInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.PersonalInfoPresenter;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;

@Module
abstract class PersonalInfoModule {

    @Binds
    @ActivityScoped
    abstract PersonalInfoContract.Presenter providePersonalInfoPresenter(PersonalInfoPresenter personalInfoPresenter);
}

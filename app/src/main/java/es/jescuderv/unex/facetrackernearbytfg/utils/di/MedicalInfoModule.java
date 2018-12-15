package es.jescuderv.unex.facetrackernearbytfg.utils.di;

import dagger.Binds;
import dagger.Module;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.MedicalInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.MedicalInfoPresenter;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;

@Module
abstract class MedicalInfoModule {

    @Binds
    @ActivityScoped
    abstract MedicalInfoContract.Presenter medicalInfoPresenter(MedicalInfoPresenter presenter);
}

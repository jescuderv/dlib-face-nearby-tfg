package es.jescuderv.unex.facetrackernearbytfg.utils.di;

import dagger.Binds;
import dagger.Module;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.MedicationContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.MedicationPresenter;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;

@Module
abstract class MedicationModule {

    @Binds
    @ActivityScoped
    abstract MedicationContract.Presenter provideMedicationPresenter(MedicationPresenter presenter);

}

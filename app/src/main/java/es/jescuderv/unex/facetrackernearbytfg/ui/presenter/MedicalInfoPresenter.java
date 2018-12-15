package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.ui.contract.MedicalInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;

@ActivityScoped
public class MedicalInfoPresenter implements MedicalInfoContract.Presenter {

    private MedicalInfoContract.View mView;

    @Inject
    public MedicalInfoPresenter() {
    }

    @Override
    public void attachView(MedicalInfoContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}

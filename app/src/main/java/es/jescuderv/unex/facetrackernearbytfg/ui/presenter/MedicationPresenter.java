package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SetUserData;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.MedicationContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.mapper.UserMapper;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;
import io.reactivex.observers.DisposableCompletableObserver;

@ActivityScoped
public class MedicationPresenter implements MedicationContract.Presenter {

    private MedicationContract.View mView;
    private SetUserData mSetUserData;


    @Inject
    MedicationPresenter(SetUserData setUserData) {
        mSetUserData = setUserData;
    }


    @Override
    public void setUserMedicationData(UserViewModel userMedicationData) {
        mSetUserData.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mView.showSuccessUpdateMedicationMessage();
            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorUpdateMedicationMessage(e.getMessage());
            }
        }, new SetUserData.Params(UserMapper.transform(userMedicationData)));
    }


    @Override
    public void attachView(MedicationContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mSetUserData.dispose();
        mView = null;
    }

}

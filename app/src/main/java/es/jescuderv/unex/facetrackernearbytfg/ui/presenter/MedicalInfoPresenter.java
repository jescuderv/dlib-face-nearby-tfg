package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SetUserData;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.MedicalInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.mapper.UserMapper;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;
import io.reactivex.observers.DisposableCompletableObserver;

@ActivityScoped
public class MedicalInfoPresenter implements MedicalInfoContract.Presenter {

    private MedicalInfoContract.View mView;

    private SetUserData mSetUserData;


    @Inject
    MedicalInfoPresenter(SetUserData setUserData) {
        mSetUserData = setUserData;
    }


    @Override
    public void setUserMedicalData(UserViewModel userMedicalInfoViewModel) {
        mSetUserData.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mView.showSuccessUpdateMedicalInfoMessage();
            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorUpdateMedicalInfoMessage(e.getMessage());
            }
        }, new SetUserData.Params(UserMapper.transform(userMedicalInfoViewModel)));
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

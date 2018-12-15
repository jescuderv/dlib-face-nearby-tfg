package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.GetUserData;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserMedicalInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;
import io.reactivex.observers.DisposableObserver;

@ActivityScoped
public class AdvertiserMedicalInfoPresenter implements AdvertiserMedicalInfoContract.Presenter {

    private AdvertiserMedicalInfoContract.View mView;

    private GetUserData mGetUserData;


    @Inject
    AdvertiserMedicalInfoPresenter(GetUserData getUserData) {
        mGetUserData = getUserData;
    }

    private void getMedicalData() {
        mGetUserData.execute(new DisposableObserver<User>() {
            @Override
            public void onNext(User user) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, null);
    }

    @Override
    public void attachView(AdvertiserMedicalInfoContract.View view) {
        mView = view;
        getMedicalData();
    }


    @Override
    public void dropView() {
        mGetUserData.dispose();
        mView = null;
    }
}

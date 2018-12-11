package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.GetUserData;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserMainInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.mapper.UserMapper;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;
import io.reactivex.observers.DisposableObserver;

@ActivityScoped
public class AdvertiserMainInfoPresenter implements AdvertiserMainInfoContract.Presenter {

    private AdvertiserMainInfoContract.View mView;

    private GetUserData mGetUserData;


    @Inject
    AdvertiserMainInfoPresenter(GetUserData getUserData) {
        mGetUserData = getUserData;
    }


    private void getUserPersonalData() {
        mGetUserData.execute(new DisposableObserver<User>() {
            @Override
            public void onNext(User user) {
                mView.showUserPersonalData(UserMapper.transform(user));
            }

            @Override
            public void onError(Throwable e) {
                mView.showAddPersonalDataButton();
            }

            @Override
            public void onComplete() {

            }
        }, null);
    }

    @Override
    public void attachView(AdvertiserMainInfoContract.View view) {
        mView = view;
        getUserPersonalData();
    }

    @Override
    public void dropView() {
        mGetUserData.dispose();
        mView = null;
    }
}

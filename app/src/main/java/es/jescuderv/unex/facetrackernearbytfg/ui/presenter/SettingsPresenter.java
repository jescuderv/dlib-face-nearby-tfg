package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SignOutSession;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.SettingsContract;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;
import io.reactivex.observers.DisposableCompletableObserver;

@ActivityScoped
public class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View mView;
    private SignOutSession mSignOutSession;


    @Inject
    SettingsPresenter(SignOutSession signOutSession) {
        mSignOutSession = signOutSession;
    }


    @Override
    public void signOut() {
        mSignOutSession.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mView.showLoginScreen();
            }

            @Override
            public void onError(Throwable e) {
                // nothing to do
            }
        }, null);
    }


    @Override
    public void attachView(SettingsContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mSignOutSession.dispose();
        mView = null;
    }
}

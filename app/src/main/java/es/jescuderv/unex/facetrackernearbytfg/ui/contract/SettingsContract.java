package es.jescuderv.unex.facetrackernearbytfg.ui.contract;

import es.jescuderv.unex.facetrackernearbytfg.ui.BasePresenter;

public interface SettingsContract {

    interface View {
        void showLoginScreen();
    }

    interface Presenter extends BasePresenter<View> {
        void signOut();
    }
}

package es.jescuderv.unex.facetrackernearbytfg.ui.contract;

import es.jescuderv.unex.facetrackernearbytfg.ui.BasePresenter;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserPersonalInfoViewModel;

public interface AdvertiserMainInfoContract {

    interface View {

        void showUserPersonalData(UserPersonalInfoViewModel transform);

        void showAddPersonalDataButton();
    }

    interface Presenter extends BasePresenter<View> {

    }

    interface OnExpandMainInfoListener {

        void onAddPersonalInfo();

        void onEditPersonalInfo(UserPersonalInfoViewModel personalInfoViewModel);

        void onCheckPersonalInfo();
    }
}

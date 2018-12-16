package es.jescuderv.unex.facetrackernearbytfg.ui.contract;

import es.jescuderv.unex.facetrackernearbytfg.ui.BasePresenter;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

public interface MedicalInfoContract {

    interface View {

        void showUserMedicalData(UserViewModel userMedicalInfoViewModel);

        void showSuccessUpdateMedicalInfoMessage();

        void showErrorUpdateMedicalInfoMessage(String message);
    }

    interface Presenter extends BasePresenter<View> {

        void setUserMedicalData(UserViewModel userMedicalInfoViewModel);
    }
}

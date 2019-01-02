package es.jescuderv.unex.facetrackernearbytfg.ui.contract;

import es.jescuderv.unex.facetrackernearbytfg.ui.BasePresenter;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

public interface PersonalInfoContract {

    interface View {

        void showEmptyFieldsMessage();

        void showSuccessUpdatePersonalInfoMessage();

        void showErrorUpdatePersonalInfoMessage(String message);
    }

    interface Presenter extends BasePresenter<View> {

        void setUserPersonalData(UserViewModel userViewModel, String name, String lastName, String birthDate, String phoneNumber,
                                 String address, String description);
    }
}

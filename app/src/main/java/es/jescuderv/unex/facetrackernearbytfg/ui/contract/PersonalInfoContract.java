package es.jescuderv.unex.facetrackernearbytfg.ui.contract;

import es.jescuderv.unex.facetrackernearbytfg.ui.BasePresenter;

public interface PersonalInfoContract {

    interface View {

        void showEmptyFieldsMessage();

        void showSuccessUpdatePersonalInfoMessage();

        void showErrorUpdatePersonalInfoMessage(String message);
    }

    interface Presenter extends BasePresenter<View> {

        void setUserPersonalData(Integer userId, String name, String lastName, String birthDate, String phoneNumber,
                                 String address, String description);
    }
}

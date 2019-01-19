package es.jescuderv.unex.facetrackernearbytfg.ui.contract;

import es.jescuderv.unex.facetrackernearbytfg.ui.BasePresenter;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

public interface MedicationContract {

    interface View {

        void showSuccessUpdateMedicationMessage();

        void showErrorUpdateMedicationMessage(String message);
    }

    interface Presenter extends BasePresenter<View> {

        void setUserMedicationData(UserViewModel userMedicationData);
    }
}

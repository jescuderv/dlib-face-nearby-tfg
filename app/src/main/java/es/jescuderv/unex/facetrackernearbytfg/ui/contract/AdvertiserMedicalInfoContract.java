package es.jescuderv.unex.facetrackernearbytfg.ui.contract;

import es.jescuderv.unex.facetrackernearbytfg.ui.BasePresenter;

public interface AdvertiserMedicalInfoContract {

    interface View {

    }

    interface Presenter extends BasePresenter<View> {

    }

    interface OnExpandMedicalInfoListener {

        void onAddMedicalInfo();

        void onEditMedicalInfo();

        void onCheckMedicalInfo();
    }
}

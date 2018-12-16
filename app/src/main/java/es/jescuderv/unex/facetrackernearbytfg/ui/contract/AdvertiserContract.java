package es.jescuderv.unex.facetrackernearbytfg.ui.contract;

import android.graphics.Bitmap;

import es.jescuderv.unex.facetrackernearbytfg.ui.BasePresenter;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

public interface AdvertiserContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showFaceDetectSuccessMessage(Bitmap faceBitmap);

        void showFaceDetectFailureMessage(String message);

        void onUserData(UserViewModel userViewModel);

        void onUserDataEmpty();
    }

    interface Presenter extends BasePresenter<View> {

        void detectFace(Bitmap faceBitmap);

    }
}

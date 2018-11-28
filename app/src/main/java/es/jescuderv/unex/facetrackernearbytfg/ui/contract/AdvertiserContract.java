package es.jescuderv.unex.facetrackernearbytfg.ui.contract;

import android.graphics.Bitmap;

import es.jescuderv.unex.facetrackernearbytfg.ui.BasePresenter;

public interface AdvertiserContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showFaceDetectSuccessMessage(Bitmap faceBitmap);

        void showFaceDetectFailureMessage(String message);
    }

    interface Presenter extends BasePresenter<View> {

        void detectFace(Bitmap faceBitmap);

        void prueba();
    }
}

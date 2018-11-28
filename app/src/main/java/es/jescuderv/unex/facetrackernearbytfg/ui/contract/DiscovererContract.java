package es.jescuderv.unex.facetrackernearbytfg.ui.contract;

import com.google.android.gms.vision.CameraSource;

import es.jescuderv.unex.facetrackernearbytfg.ui.BasePresenter;

public interface DiscovererContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showAnalyzingMessage();

        void showFaceDetectSuccessMessage();

        void showDiscovererFailureMessage(String message);
    }

    interface Presenter extends BasePresenter<View> {

        void takePicture(CameraSource cameraSource);
    }
}

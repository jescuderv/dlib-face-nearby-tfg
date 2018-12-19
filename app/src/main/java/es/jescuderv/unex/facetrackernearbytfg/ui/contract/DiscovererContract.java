package es.jescuderv.unex.facetrackernearbytfg.ui.contract;

import com.google.android.gms.vision.CameraSource;

import es.jescuderv.unex.facetrackernearbytfg.ui.BasePresenter;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

public interface DiscovererContract {

    interface View {

        void showProgress();

        void hideProgress();

        void restartCamera();

        void stopCamera();

        void showAnalyzingMessage();

        void showFaceDetectSuccessMessage();

        void showDiscovererFailureMessage(String message);

        void showStateMessage(String message);

        void showAdvertiserInfo(UserViewModel user);
    }

    interface Presenter extends BasePresenter<View> {

        void takePicture(CameraSource cameraSource);
    }
}

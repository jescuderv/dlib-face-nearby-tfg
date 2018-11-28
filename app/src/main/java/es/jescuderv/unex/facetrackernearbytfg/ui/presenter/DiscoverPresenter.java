package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.vision.CameraSource;
import com.tzutalin.dlib.VisionDetRet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.DetectFace;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.GetUserEndpointList;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SaveFace;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SendNearbyPayload;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.TakePicture;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.DiscovererContract;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;

@ActivityScoped
public class DiscoverPresenter implements DiscovererContract.Presenter {

    private final static int TAKE_PICTURE_DELAY_TIME = 1000;
    private final static String FACE_TEMPORAL_NAME = "tempFace";

    private DiscovererContract.View mView;

    private TakePicture mTakePicture;
    private DetectFace mDetectFace;
    private SaveFace mSaveFace;
    private SendNearbyPayload mSendNearbyPayload;
    private GetUserEndpointList mGetUserEndpointList;

    private boolean isBusy;


    @Inject
    DiscoverPresenter(DetectFace detectFace, TakePicture takePicture, SaveFace saveFace, SendNearbyPayload
            sendNearbyPayload, GetUserEndpointList getUserEndpointList) {
        mDetectFace = detectFace;
        mTakePicture = takePicture;
        mSaveFace = saveFace;
        mSendNearbyPayload = sendNearbyPayload;
        mGetUserEndpointList = getUserEndpointList;
    }

    @Override
    public void takePicture(CameraSource cameraSource) {
        if (!isBusy) {
            isBusy = true;
            // Execute use case after 1000 ms
            new Handler(Looper.getMainLooper())
                    .postDelayed(() -> {
                        mView.showAnalyzingMessage();
                        mView.showProgress();

                        mTakePicture.execute(new DisposableObserver<Bitmap>() {
                            @Override
                            public void onNext(Bitmap bitmap) {
                                detectFace(bitmap);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.hideProgress();
                                mView.showDiscovererFailureMessage(e.getMessage());
                                isBusy = false;
                            }

                            @Override
                            public void onComplete() {
                            }
                        }, new TakePicture.Params(cameraSource));

                    }, TAKE_PICTURE_DELAY_TIME);
        }

    }

    private void detectFace(Bitmap faceBitmap) {
        mDetectFace.execute(new DisposableObserver<List<VisionDetRet>>() {
            @Override
            public void onNext(List<VisionDetRet> result) {
                mView.showFaceDetectSuccessMessage();
            }

            @Override
            public void onError(Throwable e) {
                mView.hideProgress();
                mView.showDiscovererFailureMessage(e.getMessage());
                isBusy = false;
            }

            @Override
            public void onComplete() {
                mView.hideProgress();
                saveFace(faceBitmap);
                isBusy = false;
            }
        }, new DetectFace.Params(faceBitmap));
    }

    private void saveFace(Bitmap faceBitmap) {
        mSaveFace.execute(new DisposableObserver<File>() {
            @Override
            public void onNext(File file) {
                sendFaceDetectedNearby(file);
            }

            @Override
            public void onError(Throwable e) {
                mView.showDiscovererFailureMessage(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, new SaveFace.Params(FACE_TEMPORAL_NAME, faceBitmap));
    }

    // Saved to get path
    private void sendFaceDetectedNearby(File faceFile) {
        mGetUserEndpointList.execute(new DisposableObserver<List<String>>() {
            @Override
            public void onNext(List<String> endpointList) {
                for (String endpoint : endpointList) {
                    try {
                        Payload payload = Payload.fromFile(faceFile);
                        sendPayload(endpoint, payload);
                    } catch (FileNotFoundException e) {
                        mView.showDiscovererFailureMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.showDiscovererFailureMessage(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, null);

    }

    private void sendPayload(String destination, Payload payload) {
        mSendNearbyPayload.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
//TODO MOSTRAR OTRA PANTALLA O SIMILAR
            }

            @Override
            public void onError(Throwable e) {
                mView.showDiscovererFailureMessage(e.getMessage());
            }
        }, new SendNearbyPayload.Params(payload, destination));
    }


    @Override
    public void attachView(DiscovererContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;

        mTakePicture.dispose();
        mDetectFace.dispose();
        mSaveFace.dispose();
        mGetUserEndpointList.dispose();
        mSendNearbyPayload.dispose();
    }

}

package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.vision.CameraSource;
import com.tzutalin.dlib.VisionDetRet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.DetectFace;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.GetNearbyData;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.GetUserEndpointList;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SaveFace;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SendNearbyPayload;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.TakePicture;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.DiscovererContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.mapper.UserMapper;
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
    private GetNearbyData mGetNearbyData;

    private boolean isBusy;


    @Inject
    DiscoverPresenter(DetectFace detectFace, TakePicture takePicture, SaveFace saveFace, SendNearbyPayload
            sendNearbyPayload, GetUserEndpointList getUserEndpointList, GetNearbyData getNearbyData) {
        mDetectFace = detectFace;
        mTakePicture = takePicture;
        mSaveFace = saveFace;
        mSendNearbyPayload = sendNearbyPayload;
        mGetUserEndpointList = getUserEndpointList;
        mGetNearbyData = getNearbyData;
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
                mView.stopCamera();
                mView.showStateMessage("Cara detectada");
//                mView.showFaceDetectSuccessMessage();
            }

            @Override
            public void onError(Throwable e) {
                mView.showStateMessage(e.getMessage());
//                mView.showDiscovererFailureMessage(e.getMessage());
                mView.hideProgress();
                isBusy = false;
                mView.restartCamera();
            }

            @Override
            public void onComplete() {
//                mView.hideProgress();
                saveFace(faceBitmap);
                isBusy = false;
            }
        }, new DetectFace.Params(faceBitmap));
    }

    private void saveFace(Bitmap faceBitmap) {
        mView.showStateMessage("Almacenando imagen tomada...");
        mSaveFace.execute(new DisposableObserver<File>() {
            @Override
            public void onNext(File file) {
                sendFaceDetectedNearby(file);
            }

            @Override
            public void onError(Throwable e) {
//                mView.showDiscovererFailureMessage(e.getMessage());
                mView.showStateMessage(e.getMessage());
                mView.hideProgress();
                mView.restartCamera();
            }

            @Override
            public void onComplete() {

            }
        }, new SaveFace.Params(FACE_TEMPORAL_NAME, faceBitmap));
    }

    // Saved to get path
    private void sendFaceDetectedNearby(File faceFile) {
        mView.showStateMessage("Conectando con el usuario adecuado...");
        mGetUserEndpointList.execute(new DisposableObserver<List<String>>() {
            @Override
            public void onNext(List<String> endpointList) {
                if (endpointList.size() < 1) {
                    mView.showStateMessage("No se ha encontrando ningún usuario para conectar");
                    mView.hideProgress();
                    mView.restartCamera();
                    return;
                }

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
                // mView.showDiscovererFailureMessage(e.getMessage());
                mView.showStateMessage(e.getMessage());
                mView.hideProgress();
                mView.restartCamera();
            }

            @Override
            public void onComplete() {
                mView.showStateMessage("¡Conexión realizada!");
            }
        }, null);

    }

    private void sendPayload(String destination, Payload payload) {
        mView.showStateMessage("Realizando petición de datos...");
        mSendNearbyPayload.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mView.showStateMessage("Petición completada, esperando respuesta...");
                receivePayload();
            }

            @Override
            public void onError(Throwable e) {
                //mView.showDiscovererFailureMessage(e.getMessage());
                mView.showStateMessage(e.getMessage());
                mView.hideProgress();
                mView.restartCamera();
            }
        }, new SendNearbyPayload.Params(payload, destination));
    }

    private void receivePayload() {
        mGetNearbyData.execute(new DisposableObserver<User>() {
            @Override
            public void onNext(User user) {
                mView.showAdvertiserInfo(UserMapper.transform(user));
                Log.i("a", "todo funciona");
            }
//TODO
            @Override
            public void onError(Throwable e) {
                Log.i("a", "erorres hehe");
            }

            @Override
            public void onComplete() {
                Log.i("a", "todo completo");
            }
        }, null);
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
        mGetNearbyData.dispose();
    }

}

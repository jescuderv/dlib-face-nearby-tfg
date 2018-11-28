package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tzutalin.dlib.Constants;
import com.tzutalin.dlib.VisionDetRet;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.DetectFace;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.RecognizeFace;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SaveFace;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserContract;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;
import io.reactivex.observers.DisposableObserver;

@ActivityScoped
public class AdvertiserPresenter implements AdvertiserContract.Presenter {

    private final static String FACE_PERSONAL_NAME = "personalFace";

    private AdvertiserContract.View mView;
    private DetectFace mDetectFace;
    private SaveFace mSaveFace;

    private RecognizeFace mRecognize;

    @Inject
    AdvertiserPresenter(DetectFace detectFace, SaveFace saveFace, RecognizeFace recognizeFace) {
        mDetectFace = detectFace;
        mSaveFace = saveFace;
        mRecognize = recognizeFace;
    }


    @Override
    public void detectFace(final Bitmap faceBitmap) {
        mView.showProgress();
        mDetectFace.execute(new DisposableObserver<List<VisionDetRet>>() {
            @Override
            public void onNext(List<VisionDetRet> results) {
                saveFaceDetected(faceBitmap);
            }

            @Override
            public void onError(Throwable e) {
                mView.hideProgress();
                mView.showFaceDetectFailureMessage(e.getMessage());
            }

            @Override
            public void onComplete() {
                mView.hideProgress();
            }

        }, new DetectFace.Params(faceBitmap));
    }

    @Override
    public void prueba() {
        File detectedFile = new File(Constants.getDLibDirectoryPath() + "/faceReceived.jpg");
        Bitmap bitmap = BitmapFactory.decodeFile(detectedFile.getPath());
        mRecognize.execute(new DisposableObserver<List<VisionDetRet>>() {
            @Override
            public void onNext(List<VisionDetRet> results) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, new RecognizeFace.Params(bitmap));
    }

    private void saveFaceDetected(Bitmap faceBitmap) {
        mSaveFace.execute(new DisposableObserver<File>() {
            @Override
            public void onNext(File file) {
                // Nothing to do
            }

            @Override
            public void onError(Throwable e) {
                mView.showFaceDetectFailureMessage(e.getMessage());
            }

            @Override
            public void onComplete() {
                mView.showFaceDetectSuccessMessage(faceBitmap);
            }

        }, new SaveFace.Params(FACE_PERSONAL_NAME, faceBitmap));
    }


    @Override
    public void attachView(AdvertiserContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        mDetectFace.dispose();
        mSaveFace.dispose();
    }

}

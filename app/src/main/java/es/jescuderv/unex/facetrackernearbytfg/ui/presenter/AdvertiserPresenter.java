package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import android.graphics.Bitmap;

import com.tzutalin.dlib.VisionDetRet;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.DetectFace;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.GetUserData;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SaveFace;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.mapper.UserMapper;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;
import io.reactivex.observers.DisposableObserver;

@ActivityScoped
public class AdvertiserPresenter implements AdvertiserContract.Presenter {

    private final static String FACE_PERSONAL_NAME = "personalFace";

    private AdvertiserContract.View mView;
    private DetectFace mDetectFace;
    private SaveFace mSaveFace;

    private GetUserData mGetUserData;


    @Inject
    AdvertiserPresenter(DetectFace detectFace, SaveFace saveFace, GetUserData getUserData) {
        mDetectFace = detectFace;
        mSaveFace = saveFace;
        mGetUserData = getUserData;
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


    private void getMedicalData() {
        mGetUserData.execute(new DisposableObserver<User>() {
            @Override
            public void onNext(User user) {
                mView.onUserData(UserMapper.transform(user));
            }

            @Override
            public void onError(Throwable e) {
                mView.onUserDataEmpty();
            }

            @Override
            public void onComplete() {

            }
        }, null);
    }


    @Override
    public void attachView(AdvertiserContract.View view) {
        mView = view;
        getMedicalData();
    }

    @Override
    public void dropView() {
        mView = null;
        mGetUserData.dispose();
        mDetectFace.dispose();
        mSaveFace.dispose();
    }

}

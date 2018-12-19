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
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SetUserData;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.mapper.UserMapper;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;

@ActivityScoped
public class AdvertiserPresenter implements AdvertiserContract.Presenter {

    private final static String FACE_PERSONAL_NAME = "personalFace";

    private AdvertiserContract.View mView;
    private DetectFace mDetectFace;
    private SaveFace mSaveFace;

    private GetUserData mGetUserData;
    private SetUserData mSetUserData;


    @Inject
    AdvertiserPresenter(DetectFace detectFace, SaveFace saveFace, GetUserData getUserData, SetUserData setUserData) {
        mDetectFace = detectFace;
        mSaveFace = saveFace;
        mGetUserData = getUserData;
        mSetUserData = setUserData;
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
    public void setUserData(UserViewModel userViewModel) {
        mSetUserData.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
               // Nothing to do
            }

            @Override
            public void onError(Throwable e) {
               // Nothing to do
            }
        }, new SetUserData.Params(UserMapper.transform(userViewModel)));
    }

    private void saveFaceDetected(Bitmap faceBitmap) {
        mSaveFace.execute(new DisposableObserver<File>() {
            @Override
            public void onNext(File file) {
                mView.showFaceDetectSuccessMessage(faceBitmap, file.getAbsolutePath());
            }

            @Override
            public void onError(Throwable e) {
                mView.showFaceDetectFailureMessage(e.getMessage());
            }

            @Override
            public void onComplete() {
            }

        }, new SaveFace.Params(FACE_PERSONAL_NAME, faceBitmap));
    }


    @Override
    public void getUserData() {
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
    }

    @Override
    public void dropView() {
        mView = null;
        mSetUserData.dispose();
        mGetUserData.dispose();
        mDetectFace.dispose();
        mSaveFace.dispose();
    }

}

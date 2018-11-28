package es.jescuderv.unex.facetrackernearbytfg.data;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jescuderv.unex.facetrackernearbytfg.data.cache.UserCacheDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.local.UserLocalDataSource;
import io.reactivex.Observable;

@Singleton
public class UserRepository implements UserDataSource {

    private UserDataSource mCacheDataSource;
    private UserDataSource mLocalDataSource;

    @Inject
    public UserRepository(UserCacheDataSource userCacheDataSource, UserLocalDataSource userLocalDataSource) {
        mCacheDataSource = userCacheDataSource;
        mLocalDataSource = userLocalDataSource;
    }

    @Override
    public Observable<File> saveFace(String faceName, Bitmap faceBitmap) {
        return mLocalDataSource.saveFace(faceName, faceBitmap);
    }

    @Override
    public void addUserEndpoint(String endpoint) {
        mCacheDataSource.addUserEndpoint(endpoint);
    }

    @Override
    public Observable<List<String>> getUserEndpointList() {
        return mCacheDataSource.getUserEndpointList();
    }

}

package es.jescuderv.unex.facetrackernearbytfg.data;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jescuderv.unex.facetrackernearbytfg.data.cache.ConnectionsCacheDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.local.ConnectionsLocalDataSource;
import io.reactivex.Observable;

@Singleton
public class ConnectionsRepository implements ConnectionsDataSource {

    private ConnectionsDataSource mCacheDataSource;
    private ConnectionsDataSource mLocalDataSource;

    @Inject
    ConnectionsRepository(ConnectionsCacheDataSource userCacheDataSource, ConnectionsLocalDataSource userLocalDataSource) {
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

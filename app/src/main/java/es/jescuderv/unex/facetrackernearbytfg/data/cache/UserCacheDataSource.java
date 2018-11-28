package es.jescuderv.unex.facetrackernearbytfg.data.cache;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jescuderv.unex.facetrackernearbytfg.data.UserDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.exception.CacheErrorException;
import io.reactivex.Observable;

@Singleton
public class UserCacheDataSource implements UserDataSource {

    private List<String> mdEndpointUserList;

    @Inject
    UserCacheDataSource() {
        mdEndpointUserList = new ArrayList<>();
    }

    @Override
    public Observable<File> saveFace(String faceName, Bitmap faceBitmap) {
        return Observable.empty();
    }

    @Override
    public void addUserEndpoint(String endpoint) {
        mdEndpointUserList.add(endpoint);
    }

    @Override
    public Observable<List<String>> getUserEndpointList() {
        return Observable.create(emitter -> {
            if (mdEndpointUserList != null) emitter.onNext(mdEndpointUserList);
            else emitter.onError(new CacheErrorException());
        });
    }

}

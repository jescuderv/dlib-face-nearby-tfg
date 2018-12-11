package es.jescuderv.unex.facetrackernearbytfg.data.local;

import android.graphics.Bitmap;

import com.tzutalin.dlib.Constants;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jescuderv.unex.facetrackernearbytfg.data.ConnectionsDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.exception.DatabaseErrorException;
import es.jescuderv.unex.facetrackernearbytfg.data.local.dao.UserDao;
import es.jescuderv.unex.facetrackernearbytfg.utils.AppExecutors;
import es.jescuderv.unex.facetrackernearbytfg.utils.ImageUtil;
import io.reactivex.Observable;

@Singleton
public class ConnectionsLocalDataSource implements ConnectionsDataSource {

    private UserDao mUserDao;
    private AppExecutors mAppExecutors;


    @Inject
    ConnectionsLocalDataSource(LocalDataBase localDataBase) {
        this.mUserDao = localDataBase.userDao();
        this.mAppExecutors = new AppExecutors();
    }

    @Override
    public Observable<File> saveFace(String faceName, Bitmap faceBitmap) {
        return Observable.create(emitter -> {

            Bitmap compressImageBitmap = ImageUtil.compressImageBitmap(faceBitmap);
            File file = ImageUtil.saveImageBitmap(Constants.getDLibImageDirectoryPath(), faceName + ".jpg", compressImageBitmap);
            if (file.exists()) {
                emitter.onNext(file);
                emitter.onComplete();

            } else emitter.onError(new DatabaseErrorException("Can't save face"));
        });
    }

    @Override
    public void addUserEndpoint(String endpoint) {
        // Not need implementation because addUserEndpoint is task for CacheDataSource
    }

    @Override
    public Observable<List<String>> getUserEndpointList() {
        // Not need implementation because getUserEndpointList is task for CacheDataSource
        return Observable.empty();
    }


}

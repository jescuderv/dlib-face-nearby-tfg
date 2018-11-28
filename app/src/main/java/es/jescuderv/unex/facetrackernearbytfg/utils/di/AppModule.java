package es.jescuderv.unex.facetrackernearbytfg.utils.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.tzutalin.dlib.Constants;
import com.tzutalin.dlib.FaceRec;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import es.jescuderv.unex.facetrackernearbytfg.data.UserDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.UserRepository;
import es.jescuderv.unex.facetrackernearbytfg.data.local.LocalDataBase;
import es.jescuderv.unex.facetrackernearbytfg.data.local.UserLocalDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.cache.UserCacheDataSource;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.AddUserEndpoint;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.RecognizeFace;
import es.jescuderv.unex.facetrackernearbytfg.service.NearbyConnections;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract Context provideContext(Application application);

    @Provides
    @Singleton
    static NearbyConnections nearbyConnections(Context context, AddUserEndpoint addUserEndpoint, RecognizeFace recognizeFace) {
        return new NearbyConnections(context, addUserEndpoint, recognizeFace);
    }

    @Provides
    @Singleton
    static FaceRec provideFaceRecognition() {
        return new FaceRec(Constants.getDLibDirectoryPath());
    }

    @Provides
    @Singleton
    @Named("executor_thread")
    static Scheduler provideIOScheduler() {
        return Schedulers.computation();
    }

    @Provides
    @Singleton
    @Named("main_thread")
    static Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Singleton
    @Provides
    static LocalDataBase provideDataBase(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                LocalDataBase.class, "LocalDataBase.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @Binds
    @Singleton
    abstract UserDataSource provideUserCacheDataSource(UserCacheDataSource userCacheDataSource);

    @Binds
    @Singleton
    abstract UserDataSource provideUserLocalDataSource(UserLocalDataSource userLocalDataSource);

    @Binds
    @Singleton
    abstract UserDataSource provideUserRepository(UserRepository userRepository);

}

package es.jescuderv.unex.facetrackernearbytfg.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jescuderv.unex.facetrackernearbytfg.data.local.UserLocalDataSource;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class UserRepository implements UserDataSource {

    private UserDataSource mUserLocalDataSource;


    @Inject
    UserRepository(UserLocalDataSource userLocalDataSource) {
        mUserLocalDataSource = userLocalDataSource;
    }


    @Override
    public Completable setUserData(User user) {
        return mUserLocalDataSource.setUserData(user);
    }

    @Override
    public Observable<User> getUserData() {
        return mUserLocalDataSource.getUserData();
    }
}

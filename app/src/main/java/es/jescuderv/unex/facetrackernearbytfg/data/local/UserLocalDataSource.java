package es.jescuderv.unex.facetrackernearbytfg.data.local;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jescuderv.unex.facetrackernearbytfg.data.UserDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.exception.DatabaseErrorException;
import es.jescuderv.unex.facetrackernearbytfg.data.local.dao.UserDao;
import es.jescuderv.unex.facetrackernearbytfg.data.mapper.UserMapper;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class UserLocalDataSource implements UserDataSource {

    private LocalDataBase mDataBase;
    private UserDao mUserDao;


    @Inject
    UserLocalDataSource(LocalDataBase localDataBase) {
        mDataBase = localDataBase;
        mUserDao = localDataBase.userDao();
    }


    @Override
    public Completable setUserData(User user) {
        if (mUserDao.addUser(UserMapper.transform(user)) != null)
            return Completable.complete();

        return Completable.error(new DatabaseErrorException("No se pudo insertar el usuario"));
    }

    @Override
    public Observable<User> getUserData() {
        return mUserDao.getUser().toObservable().map(UserMapper::transform);
    }
}

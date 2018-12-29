package es.jescuderv.unex.facetrackernearbytfg.data;


import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import io.reactivex.Completable;
import io.reactivex.Observable;

public interface UserDataSource {

    Completable setUserData(User user);

    Observable<User> getUserData();

    Completable clearUserData();
}

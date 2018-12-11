package es.jescuderv.unex.facetrackernearbytfg.domain.usecase;

import javax.inject.Inject;
import javax.inject.Named;

import es.jescuderv.unex.facetrackernearbytfg.data.UserDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.UserRepository;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import es.jescuderv.unex.facetrackernearbytfg.utils.UseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GetUserData extends UseCase<User, Void> {

    private UserDataSource mRepository;

    @Inject
    GetUserData(@Named("executor_thread") Scheduler threadExecutor, @Named("main_thread") Scheduler
            mainThread, UserRepository userRepository) {
        super(threadExecutor, mainThread);
        mRepository = userRepository;
    }

    @Override
    protected Observable<User> createUseCaseObservable(Void unused) {
        return mRepository.getUserData();
    }
}

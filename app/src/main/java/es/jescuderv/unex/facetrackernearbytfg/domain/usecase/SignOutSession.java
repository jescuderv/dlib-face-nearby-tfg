package es.jescuderv.unex.facetrackernearbytfg.domain.usecase;

import javax.inject.Inject;
import javax.inject.Named;

import es.jescuderv.unex.facetrackernearbytfg.data.UserDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.UserRepository;
import es.jescuderv.unex.facetrackernearbytfg.utils.CompletableUseCase;
import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class SignOutSession extends CompletableUseCase<Void> {

    private UserDataSource mRepository;

    @Inject
    SignOutSession(@Named("executor_thread") Scheduler threadExecutor, @Named("main_thread") Scheduler
            mainThread, UserRepository userRepository) {
        super(threadExecutor, mainThread);
        mRepository = userRepository;
    }

    @Override
    protected Completable createUseCaseCompletable(Void aVoid) {
        return mRepository.clearUserData();
    }
}

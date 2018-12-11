package es.jescuderv.unex.facetrackernearbytfg.domain.usecase;

import javax.inject.Inject;
import javax.inject.Named;

import es.jescuderv.unex.facetrackernearbytfg.data.UserDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.UserRepository;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import es.jescuderv.unex.facetrackernearbytfg.utils.CompletableUseCase;
import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class SetUserData extends CompletableUseCase<SetUserData.Params> {

    private UserDataSource mRepository;

    @Inject
    SetUserData(@Named("executor_thread") Scheduler threadExecutor, @Named("main_thread") Scheduler
            mainThread, UserRepository userRepository) {
        super(threadExecutor, mainThread);
        mRepository = userRepository;
    }

    @Override
    protected Completable createUseCaseCompletable(Params params) {
        return mRepository.setUserData(params.getUser());
    }

    public static class Params {
        User user;

        public Params(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }
}

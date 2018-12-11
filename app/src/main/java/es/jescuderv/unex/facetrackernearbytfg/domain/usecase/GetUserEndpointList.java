package es.jescuderv.unex.facetrackernearbytfg.domain.usecase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import es.jescuderv.unex.facetrackernearbytfg.data.ConnectionsRepository;
import es.jescuderv.unex.facetrackernearbytfg.utils.UseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GetUserEndpointList extends UseCase<List<String>, Void> {

    private ConnectionsRepository mRepository;

    @Inject
    GetUserEndpointList(@Named("executor_thread") Scheduler threadExecutor, @Named("main_thread") Scheduler
            mainThread, ConnectionsRepository userRepository) {
        super(threadExecutor, mainThread);
        mRepository = userRepository;
    }

    @Override
    protected Observable<List<String>> createUseCaseObservable(Void unused) {
        return mRepository.getUserEndpointList();
    }
}

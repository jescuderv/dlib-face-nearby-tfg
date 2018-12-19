package es.jescuderv.unex.facetrackernearbytfg.domain.usecase;

import javax.inject.Inject;
import javax.inject.Named;

import es.jescuderv.unex.facetrackernearbytfg.domain.exception.FaceRecognitionException;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import es.jescuderv.unex.facetrackernearbytfg.service.NearbyConnections;
import es.jescuderv.unex.facetrackernearbytfg.utils.UseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GetNearbyData extends UseCase<User, Void> {

    private NearbyConnections mNearbyConnections;

    @Inject
    GetNearbyData(@Named("executor_thread") Scheduler threadExecutor, @Named("main_thread") Scheduler
            mainThread, NearbyConnections nearbyConnections) {
        super(threadExecutor, mainThread);
        mNearbyConnections = nearbyConnections;
    }


    @Override
    protected Observable<User> createUseCaseObservable(Void unused) {
        return Observable.create(emitter ->
                mNearbyConnections.setListener(user -> {
                    if (user != null) {
                        emitter.onNext(user);
                        emitter.onComplete();

                    } else emitter.onError(new FaceRecognitionException("Error"));
                }));
    }
}

package es.jescuderv.unex.facetrackernearbytfg.domain.usecase;

import com.google.android.gms.nearby.connection.Payload;

import javax.inject.Inject;
import javax.inject.Named;

import es.jescuderv.unex.facetrackernearbytfg.utils.CompletableUseCase;
import es.jescuderv.unex.facetrackernearbytfg.service.NearbyConnections;
import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class SendNearbyPayload extends CompletableUseCase<SendNearbyPayload.Params> {

    private NearbyConnections mNearbyConnections;

    @Inject
    SendNearbyPayload(@Named("executor_thread") Scheduler threadExecutor, @Named("main_thread") Scheduler
            mainThread, NearbyConnections nearbyConnections) {
        super(threadExecutor, mainThread);
        mNearbyConnections = nearbyConnections;
    }


    @Override
    protected Completable createUseCaseCompletable(Params params) {
        return mNearbyConnections.sendNearbyPayload(params.getEndpoint(), params.getPayload());
    }


    public static final class Params {
        private Payload payload;
        private String endpoint;

        public Params(Payload payload, String endpoint) {
            this.payload = payload;
            this.endpoint = endpoint;
        }

        public Payload getPayload() {
            return payload;
        }

        public String getEndpoint() {
            return endpoint;
        }
    }

}

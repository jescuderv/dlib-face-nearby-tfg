package es.jescuderv.unex.facetrackernearbytfg.domain.usecase;

import android.graphics.Bitmap;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import es.jescuderv.unex.facetrackernearbytfg.data.UserRepository;
import es.jescuderv.unex.facetrackernearbytfg.utils.UseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

@Singleton
public class SaveFace extends UseCase<File, SaveFace.Params> {

    private UserRepository mRepository;

    @Inject
    SaveFace(@Named("executor_thread") Scheduler threadExecutor, @Named("main_thread") Scheduler
            mainThread, UserRepository repository) {
        super(threadExecutor, mainThread);
        this.mRepository = repository;
    }


    @Override
    protected Observable<File> createUseCaseObservable(Params params) {
        return mRepository.saveFace(params.getFaceName(), params.getFace());
    }


    public static final class Params {
        private String faceName;
        private Bitmap face;

        public Params(String faceName, Bitmap face) {
            this.faceName = faceName;
            this.face = face;
        }

        public String getFaceName() {
            return faceName;
        }

        public Bitmap getFace() {
            return face;
        }
    }
}

package es.jescuderv.unex.facetrackernearbytfg.domain.usecase;

import android.graphics.Bitmap;

import com.tzutalin.dlib.FaceRec;
import com.tzutalin.dlib.VisionDetRet;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import es.jescuderv.unex.facetrackernearbytfg.domain.exception.FaceRecognitionException;
import es.jescuderv.unex.facetrackernearbytfg.utils.UseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class DetectFace extends UseCase<List<VisionDetRet>, DetectFace.Params> {

    private FaceRec mFaceRec;

    @Inject
    DetectFace(@Named("executor_thread") Scheduler threadExecutor, @Named("main_thread") Scheduler
            mainThread, FaceRec faceRec) {
        super(threadExecutor, mainThread);
        mFaceRec = faceRec;
    }

    @Override
    protected Observable<List<VisionDetRet>> createUseCaseObservable(Params params) {
        return Observable.create(emitter -> {
            List<VisionDetRet> results = mFaceRec.detect(params.getFaceBitmap());
            if (results == null) {
                emitter.onError(new FaceRecognitionException("Error detectando caras"));
                return;
            }

            if (results.size() == 0) {
                emitter.onError(new FaceRecognitionException("ERROR: No se ha detectado ninguna cara o es demasiado pequeña"));

            } else if (results.size() > 1) {
                emitter.onError(new FaceRecognitionException("ERROR: Más de una cara detectada"));

            } else {
                emitter.onNext(results);
                emitter.onComplete();
            }
        });
    }


    public static final class Params {

        private Bitmap faceBitmap;

        public Params(Bitmap faceBitmap) {
            this.faceBitmap = faceBitmap;
        }

        public Bitmap getFaceBitmap() {
            return faceBitmap;
        }
    }

}

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

public class RecognizeFace extends UseCase<List<VisionDetRet>, RecognizeFace.Params> {

    private FaceRec mFaceRec;

    @Inject
    RecognizeFace(@Named("executor_thread") Scheduler threadExecutor, @Named("main_thread") Scheduler
            mainThread, FaceRec faceRec) {
        super(threadExecutor, mainThread);
        mFaceRec = faceRec;
    }

    @Override
    protected Observable<List<VisionDetRet>> createUseCaseObservable(Params params) {
        return Observable.create(emitter -> {
                    mFaceRec.train();
                    List<VisionDetRet> results = mFaceRec.recognize(params.getFaceBitmap());

                    if (results == null) {
                        emitter.onError(new FaceRecognitionException("Error recognizing faces"));
                        return;
                    }

                    if (results.size() > 0) {
                        emitter.onNext(results);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new FaceRecognitionException("Face does not match with any stored face"));
                    }
                }
        );
    }

    public final static class Params {
        private Bitmap faceBitmap;

        public Params(Bitmap faceBitmap) {
            this.faceBitmap = faceBitmap;
        }

        public Bitmap getFaceBitmap() {
            return faceBitmap;
        }
    }
}

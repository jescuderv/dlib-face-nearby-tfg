package es.jescuderv.unex.facetrackernearbytfg.domain.usecase;

import android.graphics.Bitmap;

import com.google.android.gms.vision.CameraSource;

import javax.inject.Inject;
import javax.inject.Named;

import es.jescuderv.unex.facetrackernearbytfg.utils.ImageUtil;
import es.jescuderv.unex.facetrackernearbytfg.utils.UseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class TakePicture extends UseCase<Bitmap, TakePicture.Params> {


    @Inject
    TakePicture(@Named("executor_thread") Scheduler threadExecutor, @Named("main_thread") Scheduler
            mainThread) {
        super(threadExecutor, mainThread);
    }

    @Override
    protected Observable<Bitmap> createUseCaseObservable(Params params) {
        return Observable
                .create(emitter ->
                        params.getCameraSource()
                                .takePicture(() -> {
                                    // Nothing to do
                                }, bytes -> {
                                    Bitmap bitmap = ImageUtil.bytesToBitmap(bytes);
                                    if (bitmap != null) {
                                        Bitmap compressed = ImageUtil.compressImageBitmap(bitmap);
                                        emitter.onNext(compressed);

                                    } else emitter.onError(new Throwable("Error taking picture"));

                                }));
    }


    public static final class Params {
        private CameraSource cameraSource;

        public Params(CameraSource cameraSource) {
            this.cameraSource = cameraSource;
        }

        public CameraSource getCameraSource() {
            return cameraSource;
        }
    }
}

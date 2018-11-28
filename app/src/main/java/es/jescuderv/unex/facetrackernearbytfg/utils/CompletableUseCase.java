package es.jescuderv.unex.facetrackernearbytfg.utils;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;

public abstract class CompletableUseCase<Params> {

    private Scheduler threadExecutor;
    private Scheduler mainThread;
    private CompositeDisposable compositeDisposable;

    public CompletableUseCase(Scheduler threadExecutor, Scheduler mainThread) {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.compositeDisposable = new CompositeDisposable();
    }


    public void execute(DisposableCompletableObserver disposableObserver, Params params) {

        if (disposableObserver == null) {
            throw new IllegalArgumentException("disposableObserver must not be null");
        }

        final Completable completable = this.createUseCaseCompletable(params)
                .subscribeOn(threadExecutor)
                .observeOn(mainThread);
        compositeDisposable.add(completable.subscribeWith(disposableObserver));
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    protected abstract Completable createUseCaseCompletable(Params params);

}

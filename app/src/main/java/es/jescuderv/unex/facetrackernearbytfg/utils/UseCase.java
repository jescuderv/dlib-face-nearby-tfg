package es.jescuderv.unex.facetrackernearbytfg.utils;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<T, Params> {

    private Scheduler threadExecutor;
    private Scheduler mainThread;
    private CompositeDisposable compositeDisposable;

    public UseCase(Scheduler threadExecutor, Scheduler mainThread) {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
        this.compositeDisposable = new CompositeDisposable();
    }


    public void execute(DisposableObserver<T> disposableObserver, Params params) {

        if (disposableObserver == null) {
            throw new IllegalArgumentException("disposableObserver must not be null");
        }

        final Observable<T> observable = this.createUseCaseObservable(params)
                .subscribeOn(threadExecutor)
                .observeOn(mainThread);
        compositeDisposable.add(observable.subscribeWith(disposableObserver));
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    protected abstract Observable<T> createUseCaseObservable(Params params);
}

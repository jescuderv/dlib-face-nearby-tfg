package es.jescuderv.unex.facetrackernearbytfg.ui;

public interface BasePresenter<T> {

    void attachView(T view);

    void dropView();
}

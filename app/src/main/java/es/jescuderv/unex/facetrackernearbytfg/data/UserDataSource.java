package es.jescuderv.unex.facetrackernearbytfg.data;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

public interface UserDataSource {

    Observable<File> saveFace(String faceName, Bitmap faceBitmap);

    void addUserEndpoint(String endpoint);

    Observable<List<String>> getUserEndpointList();

}

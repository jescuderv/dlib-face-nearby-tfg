package es.jescuderv.unex.facetrackernearbytfg.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dagger.android.DaggerService;

import static com.google.android.gms.signin.internal.SignInClientImpl.ACTION_START_SERVICE;

public class AdvertiseService extends DaggerService {

    public static boolean isServiceRunning = false;

    @Inject
    NearbyConnections nearbyConnections;

    @Override
    public void onCreate() {
        super.onCreate();
        advertise();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(ACTION_START_SERVICE))
                advertise();

        } else stopMyService();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        isServiceRunning = false;
        super.onDestroy();
    }

    void stopMyService() {
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
        nearbyConnections.stopAdvertise();
    }


    public void advertise() {
        if (isServiceRunning) return;
        isServiceRunning = true;

        nearbyConnections.advertise();
    }


}

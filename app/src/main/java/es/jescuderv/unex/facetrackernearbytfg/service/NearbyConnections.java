package es.jescuderv.unex.facetrackernearbytfg.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.tzutalin.dlib.VisionDetRet;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.AddUserEndpoint;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.GetUserData;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.RecognizeFace;
import es.jescuderv.unex.facetrackernearbytfg.utils.RecognitionUtils;
import io.reactivex.Completable;
import io.reactivex.observers.DisposableObserver;

@Singleton
public class NearbyConnections {

    public interface OnPayloadReceivedListener {
        void onDataReceived(User user);
    }


    private Context mContext;
    private ConnectionsClient mClient;
    private String mCodeUser;

    private RecognizeFace mRecognizeFace;
    private AddUserEndpoint mAddUserEndpoint;
    private GetUserData mGetUserData;

    private String mAdvertiserEndpoint = "";
    private OnPayloadReceivedListener mListener;

    private final static String PACKAGE_NAME = "es.jescuderv.unex.facetrackernearbytfg";


    @Inject
    public NearbyConnections(Context context, AddUserEndpoint addUserEndpoint, RecognizeFace recognizeFace,
                             GetUserData getUserData) {
        mContext = context;
        mClient = Nearby.getConnectionsClient(context);
        // Code to identify advertisers
        mCodeUser = UUID.randomUUID().toString();
        mRecognizeFace = recognizeFace;
        mAddUserEndpoint = addUserEndpoint;
        mGetUserData = getUserData;
    }


    public void setListener(OnPayloadReceivedListener listener) {
        mListener = listener;
    }

    public void discover() {
        DiscoveryOptions.Builder discoverOptions = new DiscoveryOptions.Builder();
        discoverOptions.setStrategy(Strategy.P2P_CLUSTER);
        mClient.startDiscovery(PACKAGE_NAME, mEndpointDiscoveryCallback, discoverOptions.build())
                .addOnSuccessListener(unusedResult -> {
                    // We're discovering!
                    Toast.makeText(mContext, "Discover: on discover start", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    // We were unable to start discovering.
                    Toast.makeText(mContext, "Discover: on discover failure", Toast.LENGTH_LONG).show();
                });
    }

    public void advertise() {
        AdvertisingOptions.Builder advertisingOptions = new AdvertisingOptions.Builder();
        advertisingOptions.setStrategy(Strategy.P2P_CLUSTER);
        mClient.startAdvertising(mCodeUser, PACKAGE_NAME, mConnectionLifecycleCallback, advertisingOptions.build())
                .addOnSuccessListener(unusedResult -> {
                    // We're advertising!
                    Toast.makeText(mContext, "Advertise: on start advertise", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    // We were unable to start advertising.
                    Toast.makeText(mContext, "Advertise: on failure advertise", Toast.LENGTH_LONG).show();
                });
    }


    public Completable sendNearbyPayload(String destination, Payload payload) {
        return Completable.create(emitter -> {
            Task<Void> voidTask = mClient.sendPayload(destination, payload);

            if (voidTask != null) emitter.onComplete();
            else emitter.onError(new Exception("error")); //TODO
        });
    }

    private void receivePayload(Payload payload) {
        if (payload.getType() == Payload.Type.FILE) {
            Bitmap faceBitmap = RecognitionUtils.decodeReceivedPayloadFile(payload);
            recognizeFace(faceBitmap);

        } else {
            String payloadString = RecognitionUtils.decodeReceivedPayloadString(payload);
            Toast.makeText(mContext, "usuario: " + payloadString, Toast.LENGTH_LONG).show();

            Gson gson = new Gson();
            User user = gson.fromJson(payloadString, User.class);
            mListener.onDataReceived(user);
        }
    }

    private void recognizeFace(Bitmap faceBitmap) {//TODO POSIBLES CASOS
        mRecognizeFace.execute(new DisposableObserver<List<VisionDetRet>>() {
            @Override
            public void onNext(List<VisionDetRet> results) {
                Toast.makeText(mContext, "cara reconocida, enviar datos personales", Toast.LENGTH_LONG).show();
                getPersonalData();

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(mContext, "cara no reconocida:" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {

            }
        }, new RecognizeFace.Params(faceBitmap));
    }

    private void getPersonalData() {
        mGetUserData.execute(new DisposableObserver<User>() {
            @Override
            public void onNext(User user) {
                String userData = user.getUserJson(); // TODO
                mClient.sendPayload(mAdvertiserEndpoint, Payload.fromBytes(userData.getBytes()));
                Toast.makeText(mContext, "Datos personales enviados: " + userData, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {
            }
        }, null);
    }


    //==============================================================================================
    // Callbacks
    //==============================================================================================

    private final EndpointDiscoveryCallback mEndpointDiscoveryCallback =
            new EndpointDiscoveryCallback() {
                @Override
                public void onEndpointFound(@NonNull String endpointId, @NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {
                    Toast.makeText(mContext, "Discover: on endpoint found", Toast.LENGTH_LONG).show();
                    // An endpoint was found! Request connection with it
                    mClient.requestConnection(PACKAGE_NAME, endpointId, mConnectionLifecycleCallback)
                            .addOnSuccessListener(unusedResult -> {
                                // We successfully requested a connection. Now both sides must accept before the connection is established.
                                Toast.makeText(mContext, "Discover: on endpoint connected", Toast.LENGTH_LONG).show();
                            })
                            .addOnFailureListener(e -> {
                                // Nearby Connections failed to request the connection.
                                Toast.makeText(mContext, "Discover: on endpoint failed", Toast.LENGTH_LONG).show();
                            });
                }

                @Override
                public void onEndpointLost(@NonNull String endpointId) {
                    // A previously discovered endpoint has gone away.
                    Toast.makeText(mContext, "Discover: on endpoint lost", Toast.LENGTH_LONG).show();
                }
            };


    private final ConnectionLifecycleCallback mConnectionLifecycleCallback =
            new ConnectionLifecycleCallback() {

                @Override
                public void onConnectionInitiated(@NonNull String endpointId, @NonNull ConnectionInfo connectionInfo) {
                    mAdvertiserEndpoint = endpointId;
                    // Automatically accept the connection on both sides.
                    mClient.acceptConnection(endpointId, mPayloadCallback);
                    Toast.makeText(mContext, "Advertise: on connection initiated", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onConnectionResult(@NonNull String endpointId, ConnectionResolution result) {
                    Toast.makeText(mContext, "Advertise: on connection result", Toast.LENGTH_LONG).show();
                    if (result.getStatus().getStatusCode() == ConnectionsStatusCodes.STATUS_OK) {
                        // We're connected! Can now start sending and receiving data.
                        mAddUserEndpoint.execute(endpointId);
                        Toast.makeText(mContext, "Advertise: on connection disconnected: " + endpointId, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onDisconnected(@NonNull String endpointId) {
                    // We've been disconnected from this endpoint. No more data can be sent or received.
                    Toast.makeText(mContext, "Advertise: on connection disconnected", Toast.LENGTH_LONG).show();
                }
            };


    // Callbacks for receiving payloads
    private PayloadCallback mPayloadCallback =
            new PayloadCallback() {
                @Override
                public void onPayloadReceived(@NonNull String endpointId, @NonNull Payload payload) {
                    Toast.makeText(mContext, "Advertise: on payload received", Toast.LENGTH_LONG).show();
                    receivePayload(payload);
                }

                @Override
                public void onPayloadTransferUpdate(@NonNull String endpointId, @NonNull PayloadTransferUpdate update) {
                    Toast.makeText(mContext, "Advertise: on payload transfer update", Toast.LENGTH_LONG).show();
                }
            };

}

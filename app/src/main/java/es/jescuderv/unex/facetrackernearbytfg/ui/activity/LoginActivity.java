package es.jescuderv.unex.facetrackernearbytfg.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.data.preferences.SessionPreferences;
import es.jescuderv.unex.facetrackernearbytfg.utils.RecognitionUtils;

public class LoginActivity extends AppCompatActivity {

    private static final String[] REQUIRED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int REQUEST_CODE_REQUIRED_PERMISSIONS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        checkSession();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (!hasPermissions(this, REQUIRED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_REQUIRED_PERMISSIONS);
        }
    }

    private void checkSession() {
        if (SessionPreferences.getSession() == 1) onLoginAdvertiserButtonClick();
        else if (SessionPreferences.getSession() == 2) onLoginDiscoverButtonClick();
    }

    @OnClick(R.id.login_discoverer_button)
    public void onLoginDiscoverButtonClick() {
        startActivity(new Intent(this, WelcomeDiscovererActivity.class));
        finish();
        SessionPreferences.setSession(2);
    }

    @OnClick(R.id.login_advertiser_button)
    public void onLoginAdvertiserButtonClick() {
        startActivity(new Intent(this, AdvertiserActivity.class));
        finish();
        SessionPreferences.setSession(1);
    }


    /**
     * Returns {@code true} if the app was granted all the permissions. Otherwise, returns {@code
     * false}.
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * Called when the user has accepted (or denied) our permission request.
     */
    @CallSuper
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_REQUIRED_PERMISSIONS) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, getString(R.string.login_not_permissions), Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
            }
            RecognitionUtils.createRecognitionResources(this);
            recreate();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

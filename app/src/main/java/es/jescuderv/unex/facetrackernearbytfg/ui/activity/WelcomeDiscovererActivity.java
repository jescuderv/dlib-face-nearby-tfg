package es.jescuderv.unex.facetrackernearbytfg.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.data.preferences.SessionPreferences;
import es.jescuderv.unex.facetrackernearbytfg.service.DiscoverService;

import static com.google.android.gms.signin.internal.SignInClientImpl.ACTION_START_SERVICE;

public class WelcomeDiscovererActivity extends DaggerAppCompatActivity {

    private final static String USER_TYPE = "USER_TYPE";
    private final static String DISCOVERER = "DISCOVERER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_discoverer);
        ButterKnife.bind(this);

        // Start service when start screen.
        if (SessionPreferences.getVisibility() == 1) {
            Intent startIntent = new Intent(getApplicationContext(), DiscoverService.class);
            startIntent.setAction(ACTION_START_SERVICE);
            startService(startIntent);
        }
    }

    @OnClick(R.id.welcome_discoverer_start)
    public void startDiscover() {
        startActivity(new Intent(this, DiscovererActivity.class));
    }

    @OnClick(R.id.welcome_discoverer_settings_button)
    public void onDiscovererOptionsClick() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(USER_TYPE, DISCOVERER);
        startActivity(intent);
    }
}

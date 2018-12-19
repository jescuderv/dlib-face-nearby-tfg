package es.jescuderv.unex.facetrackernearbytfg.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import es.jescuderv.unex.facetrackernearbytfg.R;

public class WelcomeDiscovererActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_discoverer);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.welcome_discoverer_start)
    public void startDiscover() {
        startActivity(new Intent(this, DiscovererActivity.class));
    }

    @OnClick(R.id.welcome_discoverer_settings_button)
    public void onDiscovererOptionsClick() {
        // TODO
    }
}

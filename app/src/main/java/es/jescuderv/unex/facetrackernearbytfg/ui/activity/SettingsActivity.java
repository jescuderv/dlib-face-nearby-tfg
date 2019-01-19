package es.jescuderv.unex.facetrackernearbytfg.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.data.preferences.SessionPreferences;
import es.jescuderv.unex.facetrackernearbytfg.service.AdvertiseService;
import es.jescuderv.unex.facetrackernearbytfg.service.DiscoverService;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.SettingsContract;

import static com.google.android.gms.signin.internal.SignInClientImpl.ACTION_START_SERVICE;

public class SettingsActivity extends DaggerAppCompatActivity implements SettingsContract.View {

    private final static String USER_TYPE = "USER_TYPE";
    private final static String ADVERTISER = "ADVERTISER";
    private final static String DISCOVERER = "DISCOVERER";

    @BindView(R.id.settings_visibility_switch)
    Switch mVisibilitySwitch;


    @Inject
    SettingsContract.Presenter mPresenter;

    private String mTypeUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        mTypeUser = getIntent().getExtras().getString(USER_TYPE);

        if (SessionPreferences.getVisibility() == 1) mVisibilitySwitch.setChecked(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }


    @OnClick(R.id.settings_close_button)
    public void onCloseClick() {
        super.onBackPressed();
    }

    @OnClick(R.id.settings_change_user)
    public void onChangeUserClick() {
        // Create a new custom input dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_input);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Set up dialog
        TextView dialogTitle = dialog.findViewById(R.id.dialog_custom_input_title);
        TextView dialogDescription = dialog.findViewById(R.id.dialog_custom_input_description);
        EditText dialogInput = dialog.findViewById(R.id.dialog_custom_input_email);
        Button dialogButtonOk = dialog.findViewById(R.id.dialog_custom_input_ok_button);
        Button dialogButtonCancel = dialog.findViewById(R.id.dialog_custom_input_cancel_button);

        dialogTitle.setText("Cerrar sesión");
        dialogInput.setVisibility(View.GONE);
        dialogDescription.setVisibility(View.VISIBLE);
        dialogDescription.setText("Se borrarán todos los datos almacenados, ¿estás de acuerdo?");
        dialogButtonOk.setText("Aceptar");


        dialogButtonOk.setOnClickListener(view -> {
            mPresenter.signOut();
            dialog.dismiss();
        });
        dialogButtonCancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    @OnClick(R.id.settings_visibility_switch)
    public void onChangeVisibilityClick() {
        if (!mVisibilitySwitch.isChecked()) { // Start services if active visibility

            mVisibilitySwitch.setChecked(false);
            SessionPreferences.setVisibility(0);

            if (mTypeUser.equals(ADVERTISER)) {
                Intent stopIntent = new Intent(getApplicationContext(), AdvertiseService.class);
                stopService(stopIntent);
            } else if (mTypeUser.equals(DISCOVERER)) {
                Intent stopIntent = new Intent(getApplicationContext(), DiscoverService.class);
                stopService(stopIntent);
            }


        } else { // Stop services if deactivate visibility
            mVisibilitySwitch.setChecked(true);
            SessionPreferences.setVisibility(1);

            if (mTypeUser.equals(ADVERTISER)) {
                Intent startIntent = new Intent(getApplicationContext(), AdvertiseService.class);
                startIntent.setAction(ACTION_START_SERVICE);
                startService(startIntent);
            } else if (mTypeUser.equals(DISCOVERER)) {
                Intent startIntent = new Intent(getApplicationContext(), DiscoverService.class);
                startIntent.setAction(ACTION_START_SERVICE);
                startService(startIntent);
            }
        }
    }


    @Override
    public void showLoginScreen() {
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

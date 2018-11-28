package es.jescuderv.unex.facetrackernearbytfg.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.activity.AdvertiserActivity;
import es.jescuderv.unex.facetrackernearbytfg.ui.activity.DiscovererActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.login_button)
    public void onLoginDiscoverClickButton() {
        startActivity(new Intent(getActivity(), DiscovererActivity.class));
    }

    @OnClick(R.id.login_register_button)
    public void onLoginDiscoverButtonClick() {
        startActivity(new Intent(getActivity(), AdvertiserActivity.class));
    }
}

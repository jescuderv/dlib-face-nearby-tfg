package es.jescuderv.unex.facetrackernearbytfg.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import es.jescuderv.unex.facetrackernearbytfg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertiserMainInfoFragment extends Fragment {


    public AdvertiserMainInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advertiser_main_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}

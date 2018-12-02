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
import es.jescuderv.unex.facetrackernearbytfg.ui.activity.MedicalInfoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertiserMedicalInfoFragment extends Fragment {


    public AdvertiserMedicalInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advertiser_medical_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.medical_info_add_button)
    public void onAddMedicalInfoClick() {
        getActivity().startActivity(new Intent(getActivity(), MedicalInfoActivity.class));
    }

}

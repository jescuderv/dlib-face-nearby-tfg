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
import es.jescuderv.unex.facetrackernearbytfg.ui.activity.MedicationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertiserMedicationFragment extends Fragment {


    public AdvertiserMedicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advertiser_medication, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.advertiser_medication_add_button)
    public void onAddMedicationClick() {
        getActivity().startActivity(new Intent(getActivity(), MedicationActivity.class));
    }

}

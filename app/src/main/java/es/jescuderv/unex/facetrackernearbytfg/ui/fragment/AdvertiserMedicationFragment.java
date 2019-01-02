package es.jescuderv.unex.facetrackernearbytfg.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.jescuderv.unex.facetrackernearbytfg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertiserMedicationFragment extends Fragment {

    public interface OnExpandMedicationListener {

        void onCheckMedicationInfo();
    }

    private OnExpandMedicationListener mListener;


    public AdvertiserMedicationFragment() {
        // Required empty public constructor
    }

    public static AdvertiserMedicationFragment newInstance() {
        return new AdvertiserMedicationFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (AdvertiserMedicationFragment.OnExpandMedicationListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advertiser_medication, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.advertiser_medication_edit_button)
    public void onAddMedicationClick() {
        mListener.onCheckMedicationInfo();
    }

}

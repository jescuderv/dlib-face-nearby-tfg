package es.jescuderv.unex.facetrackernearbytfg.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.activity.MedicalInfoActivity;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserMedicalInfoContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertiserMedicalInfoFragment extends DaggerFragment implements AdvertiserMedicalInfoContract.View {


    private AdvertiserMedicalInfoContract.OnExpandMedicalInfoListener mListener;

    @Inject AdvertiserMedicalInfoContract.Presenter mPresenter;

    @Inject
    public AdvertiserMedicalInfoFragment() {
        // Required empty public constructor
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
////        mListener = (AdvertiserMedicalInfoContract.OnExpandMedicalInfoListener) context;
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advertiser_medical_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.advertiser_medical_info_add_button)
    public void onAddMedicalInfoClick() {
//        mListener.onAddMedicalInfo();
        getActivity().startActivity(new Intent(getActivity(), MedicalInfoActivity.class));
    }

}

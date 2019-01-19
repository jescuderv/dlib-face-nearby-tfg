package es.jescuderv.unex.facetrackernearbytfg.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertiserMedicationFragment extends Fragment {

    private final static String USER_VIEW_MODEL = "USER_VIEW_MODEL";
    private final static String USER_FROM_DETECTED = "USER_FROM_DETECTED";

    public interface OnExpandMedicationListener {

        void onCheckMedicationInfo(UserViewModel personalInfoViewModel, boolean isFromDetected);
    }

    @BindView(R.id.advertiser_medication_diabetes_treatment)
    TextView mDiabetesTreatment;

    @BindView(R.id.advertiser_medication_heartbeat_treatment)
    TextView mHeartBeatTreatment;

    @BindView(R.id.advertiser_medication_edit_button)
    ImageButton mEditButton;


    private UserViewModel mPersonalInfoViewModel = new UserViewModel();
    private boolean mIsFromDetected;

    private OnExpandMedicationListener mListener;


    public static AdvertiserMedicationFragment newInstance(UserViewModel userViewModel, boolean isFromDetected) {

        Bundle args = new Bundle();
        args.putSerializable(USER_VIEW_MODEL, userViewModel);
        args.putBoolean(USER_FROM_DETECTED, isFromDetected);

        AdvertiserMedicationFragment fragment = new AdvertiserMedicationFragment();
        fragment.setArguments(args);
        return fragment;
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

        checkUserPersonalInfoData();

        return view;
    }

    private void checkUserPersonalInfoData() {
        Bundle args = getArguments();
        mIsFromDetected = args.getBoolean(USER_FROM_DETECTED);

        try {
            UserViewModel userViewModel = (UserViewModel) args.get(USER_VIEW_MODEL);

            mPersonalInfoViewModel = userViewModel;
            if (!userViewModel.getDiabetesMedication().isEmpty())
                mDiabetesTreatment.setText(userViewModel.getDiabetesMedication());

            if (!userViewModel.getHearthBeatMedication().isEmpty())
                mHeartBeatTreatment.setText(userViewModel.getHearthBeatMedication());

            if (mIsFromDetected)
                mEditButton.setImageDrawable(getResources().getDrawable(R.drawable.icon_expand));

        } catch (NullPointerException ignored) {}

    }

    @OnClick(R.id.advertiser_medication_edit_button)
    public void onAddMedicationClick() {
        mListener.onCheckMedicationInfo(mPersonalInfoViewModel, mIsFromDetected);
    }

}

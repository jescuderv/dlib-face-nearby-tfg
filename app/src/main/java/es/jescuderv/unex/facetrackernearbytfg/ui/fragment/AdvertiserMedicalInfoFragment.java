package es.jescuderv.unex.facetrackernearbytfg.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.adapter.AllergyAdapter;
import es.jescuderv.unex.facetrackernearbytfg.ui.adapter.SurgeryAdapter;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertiserMedicalInfoFragment extends Fragment {

    public interface OnExpandMedicalInfoListener {

        void onEditMedicalInfo(UserViewModel userMedicalInfoViewModel);

        void onCheckMedicalInfo();
    }

    private final static String USER_VIEW_MODEL = "USER_VIEW_MODEL";

    private final static int STAGGERED_SPAN_COUNT = 2;


    @BindView(R.id.advertiser_medical_info_edit_button)
    ImageButton mEditButton;

    @BindView(R.id.advertiser_medical_info_add_button)
    ImageButton mAddButton;

    @BindView(R.id.advertiser_medical_info_main_layout)
    RelativeLayout mMainLayout;

    @BindView(R.id.advertiser_medical_info_blood_type_value)
    TextView mBloodType;

    @BindView(R.id.advertiser_medical_info_blood_type)
    TextView mBloodTypeText;


    @BindView(R.id.advertiser_medical_info_allergy_recycler_view)
    RecyclerView mAllergyRecyclerView;

    @BindView(R.id.advertiser_medical_info_surgery_recycler_view)
    RecyclerView mSurgeryRecyclerView;

    @BindView(R.id.advertiser_medical_info_allergy)
    TextView mAllergyText;

    @BindView(R.id.advertiser_medical_info_surgery)
    TextView mSurgeryText;


    private OnExpandMedicalInfoListener mListener;

    private UserViewModel mMedicalInfoViewModel;


    public static AdvertiserMedicalInfoFragment newInstance(UserViewModel userViewModel) {

        Bundle args = new Bundle();

        args.putSerializable(USER_VIEW_MODEL, userViewModel);
        AdvertiserMedicalInfoFragment fragment = new AdvertiserMedicalInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnExpandMedicalInfoListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advertiser_medical_info, container, false);
        ButterKnife.bind(this, view);

        checkUserMedicalInfoData();

        return view;
    }

    private void checkUserMedicalInfoData() {
        Bundle args = getArguments();

        try {
            UserViewModel userViewModel = (UserViewModel) args.get(USER_VIEW_MODEL);
            mMedicalInfoViewModel = userViewModel;

            if (!userViewModel.getBloodType().isEmpty()) {
                showMedicalData(userViewModel);
                mMedicalInfoViewModel = userViewModel;

            } else showAddMedicalDataButton();

        } catch (NullPointerException e) {
            showAddMedicalDataButton();
        }
    }


    @OnClick(R.id.advertiser_medical_info_add_button)
    public void onAddMedicalInfoClick() {
        mListener.onEditMedicalInfo(mMedicalInfoViewModel);
    }

    @OnClick(R.id.advertiser_medical_info_edit_button)
    public void onEditMedicalInfoClick() {
        mListener.onEditMedicalInfo(mMedicalInfoViewModel);
    }


    private void showMedicalData(UserViewModel userMedicalInfoViewModel) {
        mAddButton.setVisibility(View.GONE);
        mEditButton.setVisibility(View.VISIBLE);
        mMainLayout.setVisibility(View.VISIBLE);

        if (userMedicalInfoViewModel.getBloodType() != null) {
            mBloodType.setText(userMedicalInfoViewModel.getBloodType());
            mBloodType.setVisibility(View.VISIBLE);
            mBloodTypeText.setVisibility(View.VISIBLE);
        }

        StaggeredGridLayoutManager staggeredGridLayoutManager2 = new StaggeredGridLayoutManager(STAGGERED_SPAN_COUNT, StaggeredGridLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager staggeredGridLayoutManager3 = new StaggeredGridLayoutManager(STAGGERED_SPAN_COUNT, StaggeredGridLayoutManager.HORIZONTAL);

        if (userMedicalInfoViewModel.getSurgeryList().size() > 0) {
            mSurgeryText.setVisibility(View.VISIBLE);
            mSurgeryRecyclerView.setVisibility(View.VISIBLE);
            SurgeryAdapter surgeryAdapter = new SurgeryAdapter(userMedicalInfoViewModel.getSurgeryList());
            mSurgeryRecyclerView.setLayoutManager(staggeredGridLayoutManager2);
            mSurgeryRecyclerView.setAdapter(surgeryAdapter);
        }

        if (userMedicalInfoViewModel.getAllergyList().size() > 0) {
            mAllergyText.setVisibility(View.VISIBLE);
            mAllergyRecyclerView.setVisibility(View.VISIBLE);
            AllergyAdapter allergyAdapter = new AllergyAdapter(userMedicalInfoViewModel.getAllergyList());
            mAllergyRecyclerView.setLayoutManager(staggeredGridLayoutManager3);
            mAllergyRecyclerView.setAdapter(allergyAdapter);
        }

    }

    private void showAddMedicalDataButton() {
        mAddButton.setVisibility(View.VISIBLE);
        mEditButton.setVisibility(View.GONE);
        mMainLayout.setVisibility(View.GONE);
    }
}

package es.jescuderv.unex.facetrackernearbytfg.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertiserMainInfoFragment extends Fragment {

    public interface OnExpandMainInfoListener {

        void onEditPersonalInfo(UserViewModel personalInfoViewModel, boolean isFromDetected);

        void onCheckPersonalInfo();
    }

    private final static String USER_VIEW_MODEL = "USER_VIEW_MODEL";
    private final static String USER_FROM_DETECTED = "USER_FROM_DETECTED";


    @BindView(R.id.advertiser_main_info_data_layout)
    RelativeLayout mMainInfoLayout;

    @BindView(R.id.advertiser_main_info_add_button)
    ImageButton mAddMainInfoButton;

    @BindView(R.id.advertiser_main_info_edit_button)
    ImageButton mEditMainInfoButton;


    @BindView(R.id.advertiser_main_info_full_name_value)
    TextView mFullName;

    @BindView(R.id.advertiser_main_info_birthday_value)
    TextView mBirthday;

    @BindView(R.id.advertiser_main_info_phone_number_value)
    TextView mPhoneNumber;

    @BindView(R.id.advertiser_main_info_address_value)
    TextView mAddress;


    private OnExpandMainInfoListener mListener;

    private UserViewModel mPersonalInfoViewModel;
    private boolean mIsFromDetected;


    public static AdvertiserMainInfoFragment newInstance(UserViewModel userViewModel, boolean isFromDetected) {

        Bundle args = new Bundle();
        args.putSerializable(USER_VIEW_MODEL, userViewModel);
        args.putBoolean(USER_FROM_DETECTED, isFromDetected);

        AdvertiserMainInfoFragment fragment = new AdvertiserMainInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnExpandMainInfoListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advertiser_main_info, container, false);
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
            if (!userViewModel.getUserName().isEmpty()) {
                showUserPersonalData(userViewModel);

            } else showAddPersonalDataButton();

        } catch (NullPointerException e) {
            showAddPersonalDataButton();
        }
    }


    @OnClick(R.id.advertiser_main_info_add_button)
    public void onAddMainInfoClick() {
        if (!mIsFromDetected) mListener.onEditPersonalInfo(mPersonalInfoViewModel, false);
    }

    @OnClick(R.id.advertiser_main_info_edit_button)
    public void onEditMainInfoClick() {
        mListener.onEditPersonalInfo(mPersonalInfoViewModel, mIsFromDetected);
    }


    private void showUserPersonalData(UserViewModel user) {
        mMainInfoLayout.setVisibility(View.VISIBLE);
        mEditMainInfoButton.setVisibility(View.VISIBLE);
        mAddMainInfoButton.setVisibility(View.GONE);

        if (mIsFromDetected)
            mEditMainInfoButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_expand));

        mFullName.setText(String.format("%s %s", user.getUserName(), user.getLastName()));
        mBirthday.setText(user.getBirthday());
        mPhoneNumber.setText(String.valueOf(user.getPhoneNumber()));
        mAddress.setText(user.getAddress());
    }

    private void showAddPersonalDataButton() {
        mAddMainInfoButton.setVisibility(View.VISIBLE);
        mEditMainInfoButton.setVisibility(View.GONE);
        mMainInfoLayout.setVisibility(View.GONE);
    }
}

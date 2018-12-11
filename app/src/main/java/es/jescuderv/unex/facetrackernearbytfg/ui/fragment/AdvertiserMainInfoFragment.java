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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserMainInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserPersonalInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertiserMainInfoFragment extends DaggerFragment implements AdvertiserMainInfoContract.View {

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


    @Inject
    AdvertiserMainInfoContract.Presenter mPresenter;
    private AdvertiserMainInfoContract.OnExpandMainInfoListener mListener;

    private UserPersonalInfoViewModel mPersonalInfoViewModel;


    public AdvertiserMainInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (AdvertiserMainInfoContract.OnExpandMainInfoListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advertiser_main_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }


    @OnClick(R.id.advertiser_main_info_add_button)
    public void onAddMainInfoClick() {
        mListener.onAddPersonalInfo();
    }

    @OnClick(R.id.advertiser_main_info_edit_button)
    public void onEditMainInfoClick() {
        mListener.onEditPersonalInfo(mPersonalInfoViewModel);
    }


    @Override
    public void showUserPersonalData(UserPersonalInfoViewModel user) {
        mMainInfoLayout.setVisibility(View.VISIBLE);
        mEditMainInfoButton.setVisibility(View.VISIBLE);
        mAddMainInfoButton.setVisibility(View.GONE);

        mPersonalInfoViewModel = user;

        mFullName.setText(String.format("%s %s", user.getUserName(), user.getLastName()));
        mBirthday.setText(user.getBirthday());
        mPhoneNumber.setText(String.valueOf(user.getPhoneNumber()));
        mAddress.setText(user.getAddress());
    }

    @Override
    public void showAddPersonalDataButton() {
        mAddMainInfoButton.setVisibility(View.VISIBLE);
        mEditMainInfoButton.setVisibility(View.GONE);
        mMainInfoLayout.setVisibility(View.GONE);
    }
}

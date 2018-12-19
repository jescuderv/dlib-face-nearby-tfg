package es.jescuderv.unex.facetrackernearbytfg.ui.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.adapter.AllergyAdapter;
import es.jescuderv.unex.facetrackernearbytfg.ui.adapter.IntoleranceAdapter;
import es.jescuderv.unex.facetrackernearbytfg.ui.adapter.SurgeryAdapter;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.MedicalInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

public class MedicalInfoActivity extends DaggerAppCompatActivity implements MedicalInfoContract.View {

    private final static String USER_VIEW_MODEL = "USER_VIEW_MODEL";

    private final static int STAGGERED_SPAN_COUNT = 3;

    private final static int INTOLERANCE = 0;
    private final static int SURGERY = 1;
    private final static int ALLERGY = 2;
    private final static int BLOOD_TYPE = 3;


    @BindView(R.id.medical_info_intolerance_list)
    RecyclerView mIntoleranceRecyclerView;

    @BindView(R.id.medical_info_surgery_list)
    RecyclerView mSurgeryRecyclerView;

    @BindView(R.id.medical_info_allergy_list)
    RecyclerView mAllergyRecyclerView;


    @BindView(R.id.medical_info_intolerance_empty_message)
    TextView mIntoleranceEmptyMessage;

    @BindView(R.id.medical_info_allergy_empty_message)
    TextView mAllergyEmptyMessage;

    @BindView(R.id.medical_info_surgery_empty_message)
    TextView mSurgeryEmptyMessage;


    @BindView(R.id.medical_info_blood_type)
    TextView mBloodTypeText;

    @BindView(R.id.medical_info_description_input)
    EditText mMedicalDescription;


    @Inject
    MedicalInfoContract.Presenter mPresenter;

    private UserViewModel mUserMedicalInfoViewModel = new UserViewModel();

    private List<UserViewModel.Intolerance> mIntoleranceList = new ArrayList<>();
    private List<UserViewModel.Surgery> mSurgeryList = new ArrayList<>();
    private List<UserViewModel.Allergy> mAllergyList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_info);
        ButterKnife.bind(this);

        setUpAdapters();

        checkMedicalInfoData();
    }

    private void checkMedicalInfoData() {
        Bundle args = getIntent().getExtras();

        try {
            UserViewModel userViewModel = (UserViewModel) args.get(USER_VIEW_MODEL);
            mUserMedicalInfoViewModel = userViewModel;

            if (userViewModel.getBloodType() != null || userViewModel.getAllergyList().size() > 0
                    || userViewModel.getIntoleranceList().size() > 0 || userViewModel.getSurgeryList().size() > 0)
                showUserMedicalData(userViewModel);

        } catch (NullPointerException ignored) {

        }
    }

    private void setUpAdapters() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(STAGGERED_SPAN_COUNT, StaggeredGridLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager staggeredGridLayoutManager2 = new StaggeredGridLayoutManager(STAGGERED_SPAN_COUNT, StaggeredGridLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager staggeredGridLayoutManager3 = new StaggeredGridLayoutManager(STAGGERED_SPAN_COUNT, StaggeredGridLayoutManager.HORIZONTAL);

        SurgeryAdapter surgeriesAdapter = new SurgeryAdapter(mSurgeryList);
        mSurgeryRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mSurgeryRecyclerView.setAdapter(surgeriesAdapter);

        IntoleranceAdapter intoleranceAdapter = new IntoleranceAdapter(mIntoleranceList);
        mIntoleranceRecyclerView.setLayoutManager(staggeredGridLayoutManager2);
        mIntoleranceRecyclerView.setAdapter(intoleranceAdapter);

        AllergyAdapter allergyAdapter = new AllergyAdapter(mAllergyList);
        mAllergyRecyclerView.setLayoutManager(staggeredGridLayoutManager3);
        mAllergyRecyclerView.setAdapter(allergyAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }


    @OnClick(R.id.medical_info_close_button)
    public void onCloseClick() {
        super.onBackPressed();
    }

    @OnClick(R.id.medical_info_save_button)
    public void onSaveMedicalInfoButtonClick() {
        mUserMedicalInfoViewModel.setBloodType(mBloodTypeText.getText().toString());
        mUserMedicalInfoViewModel.setSurgeryList(mSurgeryList);
        mUserMedicalInfoViewModel.setIntoleranceList(mIntoleranceList);
        mUserMedicalInfoViewModel.setAllergyList(mAllergyList);
        mUserMedicalInfoViewModel.setMedicalDescription(mMedicalDescription.getText().toString());
        mPresenter.setUserMedicalData(mUserMedicalInfoViewModel);
    }

    @OnClick(R.id.medical_info_intolerance_add_button)
    public void onAdIntoleranceClickButton() {
        showAddMedicalInfoDialog(INTOLERANCE);
    }

    @OnClick(R.id.medical_info_surgery_add_button)
    public void onAdSurgeryClickButton() {
        showAddMedicalInfoDialog(SURGERY);
    }

    @OnClick(R.id.medical_info_allergy_add_button)
    public void onAddAllergyClickButton() {
        showAddMedicalInfoDialog(ALLERGY);
    }

    @OnClick(R.id.medical_info_blood_type)
    public void onAddBloodTypeButton() {
        showAddMedicalInfoDialog(BLOOD_TYPE);
    }


    @Override
    public void showUserMedicalData(UserViewModel userMedicalInfoViewModel) {
        mUserMedicalInfoViewModel = userMedicalInfoViewModel;

        if (userMedicalInfoViewModel.getSurgeryList().size() > 0) {
            mSurgeryEmptyMessage.setVisibility(View.GONE);
            mSurgeryRecyclerView.setVisibility(View.VISIBLE);
        }
        if (userMedicalInfoViewModel.getIntoleranceList().size() > 0) {
            mIntoleranceEmptyMessage.setVisibility(View.GONE);
            mIntoleranceRecyclerView.setVisibility(View.VISIBLE);
        }
        if (userMedicalInfoViewModel.getAllergyList().size() > 0) {
            mAllergyEmptyMessage.setVisibility(View.GONE);
            mAllergyRecyclerView.setVisibility(View.VISIBLE);
        }

        mBloodTypeText.setText(userMedicalInfoViewModel.getBloodType());
        mMedicalDescription.setText(userMedicalInfoViewModel.getMedicalDescription());

        mAllergyList.addAll(userMedicalInfoViewModel.getAllergyList());
        mSurgeryList.addAll(userMedicalInfoViewModel.getSurgeryList());
        mIntoleranceList.addAll(userMedicalInfoViewModel.getIntoleranceList());

    }

    @Override
    public void showSuccessUpdateMedicalInfoMessage() {
        Toast.makeText(this, getString(R.string.medical_info_updated), Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void showErrorUpdateMedicalInfoMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private void showAddMedicalInfoDialog(int medicalInfo) {
        // Create a new custom input dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_input);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Set up dialog
        TextView dialogTitle = dialog.findViewById(R.id.dialog_custom_input_title);
        final EditText editText = dialog.findViewById(R.id.dialog_custom_input_email);
        Button dialogButtonOk = dialog.findViewById(R.id.dialog_custom_input_ok_button);
        Button dialogButtonCancel = dialog.findViewById(R.id.dialog_custom_input_cancel_button);

        switch (medicalInfo) {
            case 0:
                dialogTitle.setText(getString(R.string.medical_info_add_intolerance));
                break;
            case 1:
                dialogTitle.setText(getString(R.string.medical_info_add_surgery));
                break;
            case 2:
                dialogTitle.setText(getString(R.string.medical_info_add_allergy));
                break;
            case 3:
                dialogTitle.setText(getString(R.string.medical_info_add_blood_type));
        }

        dialogButtonOk.setOnClickListener(view -> {
            addMedicalInfo(medicalInfo, editText.getText().toString());
            dialog.dismiss();
        });
        dialogButtonCancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    private void addMedicalInfo(int medicalInfo, String name) {
        if (!name.trim().isEmpty()) {
            switch (medicalInfo) {
                case INTOLERANCE:
                    UserViewModel.Intolerance intolerance = new UserViewModel.Intolerance(name);
                    mIntoleranceList.add(intolerance);
                    mIntoleranceEmptyMessage.setVisibility(View.GONE);
                    mIntoleranceRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case SURGERY:
                    UserViewModel.Surgery surgery = new UserViewModel.Surgery(name);
                    mSurgeryList.add(surgery);
                    mSurgeryEmptyMessage.setVisibility(View.GONE);
                    mSurgeryRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case ALLERGY:
                    UserViewModel.Allergy allergy = new UserViewModel.Allergy(name);
                    mAllergyEmptyMessage.setVisibility(View.GONE);
                    mAllergyRecyclerView.setVisibility(View.VISIBLE);
                    mAllergyList.add(allergy);
                    break;
                case BLOOD_TYPE:
                    mBloodTypeText.setText(name);
                    break;
            }
        }

    }


}

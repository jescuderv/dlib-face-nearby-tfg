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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.adapter.StaggeredAdapter;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.MedicalInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserMedicalInfoViewModel;

public class MedicalInfoActivity extends DaggerAppCompatActivity implements MedicalInfoContract.View {

    private final static String MEDICAL_INFO_VIEW_MODEL = "MEDICAL_INFO_VIEW_MODEL";


    @BindView(R.id.medical_info_intolerance_list)
    RecyclerView mIntoleranceRecyclerView;

    @BindView(R.id.medical_info_surgery_list)
    RecyclerView mSurgeryRecyclerView;

    @BindView(R.id.medical_info_allergy_list)
    RecyclerView mAllergyRecyclerView;

    @BindView(R.id.medical_info_intolerance_empty_message)
    TextView mIntoleranceEmptyMessage;


    @BindView(R.id.medical_info_blood_type)
    TextView mBloodTypeText;


    @Inject
    MedicalInfoContract.Presenter mPresenter;

    StaggeredAdapter mIntolerancesAdapter;
    StaggeredAdapter mSurgeriesAdapter;
    StaggeredAdapter mAllergiesAdapter;
    List<String> intoleranceList;
    List<String> surgeryList;
    List<String> allergyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_info);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
//        if (extras != null) showUserPersonalData((UserMedicalInfoViewModel)
//                Objects.requireNonNull(extras.get(MEDICAL_INFO_VIEW_MODEL)));

        intoleranceList = new ArrayList<>();
        surgeryList = new ArrayList<>();
        allergyList = new ArrayList<>();

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager staggeredGridLayoutManager2 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager staggeredGridLayoutManager3 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);


        mSurgeriesAdapter = new StaggeredAdapter(surgeryList);
        mSurgeryRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mSurgeryRecyclerView.setAdapter(mSurgeriesAdapter);

        mIntolerancesAdapter = new StaggeredAdapter(intoleranceList);
        mIntoleranceRecyclerView.setLayoutManager(staggeredGridLayoutManager2);
        mIntoleranceRecyclerView.setAdapter(mIntolerancesAdapter);

        mAllergiesAdapter = new StaggeredAdapter(allergyList);
        mAllergyRecyclerView.setLayoutManager(staggeredGridLayoutManager3);
        mAllergyRecyclerView.setAdapter(mAllergiesAdapter);
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

    }

    @OnClick(R.id.medical_info_intolerance_add_button)
    public void onAdIntoleranceClickButton() {
        showAddMedicalInfoDialog(0);
    }

    @OnClick(R.id.medical_info_surgery_add_button)
    public void onAdSurgeryClickButton() {
        showAddMedicalInfoDialog(1);
    }

    @OnClick(R.id.medical_info_allergy_add_button)
    public void onAddAllergyClickButton() {
        showAddMedicalInfoDialog(2);
    }

    @OnClick(R.id.medical_info_blood_type)
    public void onAddBloodTypeButton(){showAddMedicalInfoDialog(3);}

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
                case 0:
                    UserMedicalInfoViewModel.Allergy intolerance = new UserMedicalInfoViewModel.Allergy(1, name);
                    intoleranceList.add(intolerance.getName());
                    mIntoleranceEmptyMessage.setVisibility(View.GONE);
                    mIntoleranceRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    UserMedicalInfoViewModel.Allergy surgery = new UserMedicalInfoViewModel.Allergy(1, name);
                    surgeryList.add(surgery.getName());
                    break;
                case 2:
                    UserMedicalInfoViewModel.Allergy allergy = new UserMedicalInfoViewModel.Allergy(2, name);
                    allergyList.add(allergy.getName());
                    break;
                case 3:
                    mBloodTypeText.setText(name);
                    break;
            }
        }

    }


}

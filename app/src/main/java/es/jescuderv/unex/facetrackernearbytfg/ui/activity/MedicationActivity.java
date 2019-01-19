package es.jescuderv.unex.facetrackernearbytfg.ui.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.MedicationContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;
import es.jescuderv.unex.facetrackernearbytfg.ui.views.LineChartView;

public class MedicationActivity extends DaggerAppCompatActivity implements MedicationContract.View {

    private final static int DIABETES = 0;
    private final static int HEARTH_BEAT = 1;

    private final static String USER_VIEW_MODEL = "USER_VIEW_MODEL";
    private final static String USER_FROM_DETECTED = "USER_FROM_DETECTED";

    @BindView(R.id.medication_diabetes_treatment)
    TextView mDiabetesTreatment;

    @BindView(R.id.medication_heartbeat_treatment)
    TextView mHearthBeatTreatment;


    @BindView(R.id.medication_diabetes_add_button)
    Button mAddDiabetesTreatmentButton;

    @BindView(R.id.medication_heartbeat_add_button)
    Button mAddHearthBeatTreatmentButton;

    @BindView(R.id.medication_diabetes_add_chart_button)
    Button mAddDiabetesChartButton;

    @BindView(R.id.medication_heartbeat_add_chart_button)
    Button mAddHearthBeatChartButton;


    @BindView(R.id.medication_diabetes_chart)
    LineChart mDiabetesChart;

    @BindView(R.id.medication_heartbeat_chart)
    LineChart mHeartbeatChart;

    @BindView(R.id.medication_save_button)
    Button mSaveButton;


    @Inject
    MedicationContract.Presenter mPresenter;


    private UserViewModel mUserMedicationViewModel = new UserViewModel();
    private ArrayList<UserViewModel.Medication> mDiabetesMedication = new ArrayList<>();
    private ArrayList<UserViewModel.Medication> mHearthBeatMedication = new ArrayList<>();

    private LineChartView mHeartBeatLineChartView;
    private LineChartView mDiabetesLineChartView;
    private ArrayList<Entry> mHearthBeatEntries = new ArrayList<>();
    private ArrayList<Entry> mDiabetesEntries = new ArrayList<>();

    private boolean mIsFromDetected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        ButterKnife.bind(this);

        Bundle args = getIntent().getExtras();
        mIsFromDetected = args.getBoolean(USER_FROM_DETECTED);
        mUserMedicationViewModel = (UserViewModel) args.get(USER_VIEW_MODEL);

        setUpDiabetesChart();
        setUpHeartbeatChart();

        checkUserFromDetected();

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


    @OnClick(R.id.medication_close_button)
    public void onCloseClick() {
        super.onBackPressed();
    }

    @OnClick(R.id.medication_diabetes_add_button)
    public void addDiabetesTreatment() {
        showMedicationDialog(DIABETES);
    }

    @OnClick(R.id.medication_heartbeat_add_button)
    public void addHearthBeatTreatment() {
        showMedicationDialog(HEARTH_BEAT);
    }

    @OnClick(R.id.medication_diabetes_add_chart_button)
    public void addDiabetesChart() {
        showTimePickerDialog(DIABETES);
    }

    @OnClick(R.id.medication_heartbeat_add_chart_button)
    public void addHearthBeatChart() {
        showTimePickerDialog(HEARTH_BEAT);
    }

    @OnClick(R.id.medication_save_button)
    public void onSaveMedication() {
        mUserMedicationViewModel.setDiabetesMedication(mDiabetesTreatment.getText().toString());
        mUserMedicationViewModel.setHearthBeatMedication(mHearthBeatTreatment.getText().toString());
        mUserMedicationViewModel.setDiabetesList(mDiabetesMedication);
        mUserMedicationViewModel.setHearthBeatList(mHearthBeatMedication);
        mPresenter.setUserMedicationData(mUserMedicationViewModel);
    }

    private void setUpDiabetesChart() {
        mDiabetesLineChartView = new LineChartView(mDiabetesChart, this);
        mDiabetesLineChartView.buildChart();
        mDiabetesLineChartView.setValueFormatter(new IAxisValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

        mDiabetesTreatment.setText(mUserMedicationViewModel.getDiabetesMedication());
        for (UserViewModel.Medication medication : mUserMedicationViewModel.getDiabetesList()) {
            mDiabetesEntries.add(new Entry(medication.getDate(), medication.getValue()));
            mDiabetesMedication.add(medication);
        }

        mDiabetesLineChartView.setDataSet(mDiabetesEntries, getString(R.string.medication_glucosa_level));
    }

    private void setUpHeartbeatChart() {
        mHeartBeatLineChartView = new LineChartView(mHeartbeatChart, this);
        mHeartBeatLineChartView.buildChart();
        mHeartBeatLineChartView.setValueFormatter(new IAxisValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });


        mHearthBeatTreatment.setText(mUserMedicationViewModel.getHearthBeatMedication());
        for (UserViewModel.Medication medication : mUserMedicationViewModel.getHearthBeatList()) {
            mHearthBeatEntries.add(new Entry(medication.getDate(), medication.getValue()));
            mHearthBeatMedication.add(medication);
        }

        mHeartBeatLineChartView.setDataSet(mHearthBeatEntries, getString(R.string.medication_hearth_beat_level));
    }

    private void checkUserFromDetected() {
        if (mIsFromDetected) {
            mAddDiabetesChartButton.setVisibility(View.GONE);
            mAddDiabetesTreatmentButton.setVisibility(View.GONE);
            mAddHearthBeatChartButton.setVisibility(View.GONE);
            mAddHearthBeatTreatmentButton.setVisibility(View.GONE);
            mSaveButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSuccessUpdateMedicationMessage() {
        Toast.makeText(this, getString(R.string.medication_updated), Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void showErrorUpdateMedicationMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void showTimePickerDialog(int medication) {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = (view, hourOfDay, minute1) -> {
            if (view.isShown()) {
                myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalender.set(Calendar.MINUTE, minute1);
                Long time = TimeUnit.MILLISECONDS.toHours(myCalender.getTimeInMillis());

                switch (medication) {
                    case DIABETES:
                        if (mDiabetesMedication.size() > 0) {
                            if (mDiabetesMedication.get(mDiabetesMedication.size() - 1).getDate() > time) {
                                Toast.makeText(this, getString(R.string.medication_error_time), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        showMedicationChartDialog(medication, time);
                        break;
                    case HEARTH_BEAT:
                        if (mHearthBeatMedication.size() > 0) {
                            if (mHearthBeatMedication.get(mHearthBeatMedication.size() - 1).getDate() > time) {
                                Toast.makeText(this, getString(R.string.medication_error_time), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        showMedicationChartDialog(medication, time);
                        break;
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.TimePickerDialogTheme,
                myTimeListener, hour, minute, true);
        timePickerDialog.show();
    }

    private void showMedicationChartDialog(int medication, long time) {
        // Create a new custom input dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_input);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Set up dialog
        TextView dialogTitle = dialog.findViewById(R.id.dialog_custom_input_title);
        final EditText editText = dialog.findViewById(R.id.dialog_custom_input_email);
        Button dialogButtonOk = dialog.findViewById(R.id.dialog_custom_input_ok_button);
        Button dialogButtonCancel = dialog.findViewById(R.id.dialog_custom_input_cancel_button);

        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        switch (medication) {
            case DIABETES:
                dialogTitle.setText(getString(R.string.medication_add_diabetes));
                break;
            case HEARTH_BEAT:
                dialogTitle.setText(getString(R.string.medication_add_hearth_beat));
                break;
        }

        dialogButtonOk.setOnClickListener(view -> {
            dialog.dismiss();
            addMedicationChart(medication, editText.getText().toString(), time);
        });
        dialogButtonCancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    private void addMedicationChart(int medication, String value, long time) {
        switch (medication) {
            case DIABETES:
                mDiabetesEntries.add(new Entry(time, Float.parseFloat(value)));
                mDiabetesMedication.add(new UserViewModel.Medication(Float.parseFloat(value), time));
                mDiabetesLineChartView.setDataSet(mDiabetesEntries, getString(R.string.medication_glucosa_level));
                mDiabetesChart.invalidate();
                break;
            case HEARTH_BEAT:
                mHearthBeatEntries.add(new Entry(time, Float.parseFloat(value)));
                mHearthBeatMedication.add(new UserViewModel.Medication(Float.parseFloat(value), time));
                mHeartBeatLineChartView.setDataSet(mHearthBeatEntries, getString(R.string.medication_hearth_beat_level));
                mHeartbeatChart.invalidate();
                break;
        }
    }

    private void showMedicationDialog(int medication) {
        // Create a new custom input dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_input);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Set up dialog
        TextView dialogTitle = dialog.findViewById(R.id.dialog_custom_input_title);
        final EditText editText = dialog.findViewById(R.id.dialog_custom_input_email);
        Button dialogButtonOk = dialog.findViewById(R.id.dialog_custom_input_ok_button);
        Button dialogButtonCancel = dialog.findViewById(R.id.dialog_custom_input_cancel_button);

        switch (medication) {
            case DIABETES:
                dialogTitle.setText(getString(R.string.medication_add_diabetes));
                break;
            case HEARTH_BEAT:
                dialogTitle.setText(getString(R.string.medication_add_hearth_beat));
                break;
        }

        dialogButtonOk.setOnClickListener(view -> {
            addMedication(medication, editText.getText().toString());
            dialog.dismiss();
        });
        dialogButtonCancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    private void addMedication(int medication, String text) {
        if (!text.trim().isEmpty()) {
            switch (medication) {
                case DIABETES:
                    String diabetesText = mDiabetesTreatment.getText().toString() + "\n";
                    mDiabetesTreatment.setText(String.format("%s- %s", diabetesText, text));
                    break;
                case HEARTH_BEAT:
                    String hearthBeatText = mHearthBeatTreatment.getText().toString() + "\n";
                    mHearthBeatTreatment.setText(String.format("%s- %s", hearthBeatText, text));
                    break;
            }
        }
    }
}

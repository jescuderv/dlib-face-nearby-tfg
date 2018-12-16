package es.jescuderv.unex.facetrackernearbytfg.ui.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.PersonalInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.PersonalInfoPresenter;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

public class PersonalInfoActivity extends DaggerAppCompatActivity implements PersonalInfoContract.View {

    private final static String USER_VIEW_MODEL = "USER_VIEW_MODEL";


    @BindView(R.id.personal_info_name_input)
    EditText mName;

    @BindView(R.id.personal_info_last_name_input)
    EditText mLastName;

    @BindView(R.id.personal_info_birthday)
    TextView mBirthday;

    @BindView(R.id.personal_info_phone_number)
    EditText mPhoneNumber;

    @BindView(R.id.personal_info_address)
    EditText mAddress;

    @BindView(R.id.personal_info_description_input)
    EditText mDescription;


    @Inject
    PersonalInfoPresenter mPresenter;

    private Integer mUserId;


    /* Calendar picker */
    private Calendar mCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener mDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

        private void updateLabel() {
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

            mBirthday.setText(sdf.format(mCalendar.getTime()));
        }

    };


    @Inject
    public PersonalInfoActivity() {
        // Requires empty constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);

        checkUserPersonalInfoData();
    }

    private void checkUserPersonalInfoData() {
        Bundle args = getIntent().getExtras();

        try {
            UserViewModel userViewModel = (UserViewModel) args.get(USER_VIEW_MODEL);
            if (!userViewModel.getUserName().isEmpty()){
                showUserPersonalData(userViewModel);
            }

        } catch (NullPointerException ignored) {

        }
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


    @OnClick(R.id.personal_info_close_button)
    public void onCloseClick() {
        this.onBackPressed();
    }

    @OnClick(R.id.personal_info_birthday)
    public void onBirthdayClick() {
        new DatePickerDialog(this, R.style.DatePickerDialogTheme, mDatePickerListener, mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.personal_info_save_button)
    public void savePersonalData() {
        mPresenter.setUserPersonalData(mUserId, mName.getText().toString(), mLastName.getText().toString(),
                mBirthday.getText().toString(), mPhoneNumber.getText().toString(), mAddress.getText().toString(),
                mDescription.getText().toString());
    }


    @Override
    public void showEmptyFieldsMessage() {
        Toast.makeText(this, getString(R.string.personal_info_empty_fields), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessUpdatePersonalInfoMessage() {
        Toast.makeText(this, getString(R.string.personal_info_updated), Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void showErrorUpdatePersonalInfoMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private void showUserPersonalData(UserViewModel user) {
        mUserId = user.getId();
        mName.setText(user.getUserName());
        mLastName.setText(user.getLastName());
        mBirthday.setText(user.getBirthday());
        mPhoneNumber.setText(String.valueOf(user.getPhoneNumber()));
        mAddress.setText(user.getAddress());
        mDescription.setText(user.getDescription());
    }
}

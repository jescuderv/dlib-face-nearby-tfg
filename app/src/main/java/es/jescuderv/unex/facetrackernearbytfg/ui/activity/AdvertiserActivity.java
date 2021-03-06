package es.jescuderv.unex.facetrackernearbytfg.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tzutalin.dlib.Constants;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.data.preferences.SessionPreferences;
import es.jescuderv.unex.facetrackernearbytfg.service.AdvertiseService;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.fragment.AdvertiserMainInfoFragment;
import es.jescuderv.unex.facetrackernearbytfg.ui.fragment.AdvertiserMedicalInfoFragment;
import es.jescuderv.unex.facetrackernearbytfg.ui.fragment.AdvertiserMedicationFragment;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;
import es.jescuderv.unex.facetrackernearbytfg.ui.views.ExpandingViewPagerTransformer;
import es.jescuderv.unex.facetrackernearbytfg.utils.ImageUtil;

import static com.google.android.gms.signin.internal.SignInClientImpl.ACTION_START_SERVICE;

public class AdvertiserActivity extends DaggerAppCompatActivity implements AdvertiserContract.View,
        AdvertiserMainInfoFragment.OnExpandMainInfoListener, AdvertiserMedicalInfoFragment.OnExpandMedicalInfoListener,
        AdvertiserMedicationFragment.OnExpandMedicationListener {

    private final static int PICK_IMAGE = 1;

    private final static String USER_VIEW_MODEL = "USER_VIEW_MODEL";
    private final static String USER_FROM_DETECTED = "USER_FROM_DETECTED";

    private final static String USER_TYPE = "USER_TYPE";
    private final static String ADVERTISER = "ADVERTISER";

    private final static String FACE_TEMPORAL_NAME = "tempFace";


    @BindView(R.id.advertiser_view_pager)
    ViewPager mViewPager;

    @BindView(R.id.advertiser_profile_image)
    ImageView mProfileImage;

    @Inject
    AdvertiserContract.Presenter mPresenter;

    private UserViewModel mUserViewModel;

    private boolean mIsFromDetected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser);
        ButterKnife.bind(this);

        // Start service when start screen
        if (SessionPreferences.getVisibility() == 1) {
            Intent startIntent = new Intent(getApplicationContext(), AdvertiseService.class);
            startIntent.setAction(ACTION_START_SERVICE);
            startService(startIntent);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserViewModel userViewModel = (UserViewModel) extras.get(USER_VIEW_MODEL);
            mIsFromDetected = true;
            onUserData(userViewModel);
            Bitmap bitmap = BitmapFactory.decodeFile(
                    Constants.getDLibImageDirectoryPath() + File.separator + FACE_TEMPORAL_NAME + ".jpg");
            mProfileImage.setImageBitmap(bitmap);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
        if (!mIsFromDetected) mPresenter.getUserData();
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }


    @OnClick(R.id.advertiser_settings_button)
    public void onSettingsClick() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(USER_TYPE, ADVERTISER);
        startActivity(intent);
    }

    @OnClick(R.id.advertiser_profile_image)
    public void setProfileImage() {
        if (!mIsFromDetected) {
            Intent gallIntent = new Intent(Intent.ACTION_GET_CONTENT);
            gallIntent.setType("image/*");
            startActivityForResult(Intent.createChooser(gallIntent, "Select Picture"), PICK_IMAGE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mProfileImage.setVisibility(View.INVISIBLE);
                mPresenter.detectFace(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showProgress() {
//        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
//        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showFaceDetectSuccessMessage(Bitmap faceBitmap, String path) {
        mProfileImage.setVisibility(View.VISIBLE);
        mProfileImage.setImageBitmap(faceBitmap);
        mUserViewModel.setFacePath(path);
        mPresenter.setUserData(mUserViewModel);
        Toast.makeText(this, getString(R.string.advertiser_face_detected), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFaceDetectFailureMessage(String message) {
        mProfileImage.setVisibility(View.VISIBLE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUserData(UserViewModel userViewModel) {
        mUserViewModel = userViewModel;

        if (userViewModel.getFacePath() != null) {
            Bitmap bitmap = ImageUtil.bitmapFromPath(userViewModel.getFacePath());
            if (bitmap != null) mProfileImage.setImageBitmap(bitmap);
        }

        setUpViewPager();
    }

    @Override
    public void onUserDataEmpty() {
        mUserViewModel = new UserViewModel();
        setUpViewPager();
    }


    private void setUpViewPager() {
        FragmentStatePagerAdapter pagerAdapter = new ProfileAdapter(getSupportFragmentManager());
        mViewPager.setPageTransformer(true, new ExpandingViewPagerTransformer());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setClipToPadding(false);
        mViewPager.setPadding(48, 0, 48, 0);
        mViewPager.setPageMargin(-100);
    }


    private class ProfileAdapter extends FragmentStatePagerAdapter {

        ProfileAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AdvertiserMainInfoFragment.newInstance(mUserViewModel, mIsFromDetected);
                case 1:
                    return AdvertiserMedicalInfoFragment.newInstance(mUserViewModel, mIsFromDetected);
                case 2:
                    return AdvertiserMedicationFragment.newInstance(mUserViewModel, mIsFromDetected);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    //==============================================================================================
    // Fragments callbacks
    //==============================================================================================

    @Override
    public void onEditPersonalInfo(UserViewModel personalInfoViewModel, boolean isFromDetected) {
        Intent intent = new Intent(this, PersonalInfoActivity.class);
        intent.putExtra(USER_VIEW_MODEL, personalInfoViewModel);
        intent.putExtra(USER_FROM_DETECTED, isFromDetected);
        startActivity(intent);
    }


    @Override
    public void onEditMedicalInfo(UserViewModel userMedicalInfoViewModel, boolean isFromDetected) {
        Intent intent = new Intent(this, MedicalInfoActivity.class);
        intent.putExtra(USER_VIEW_MODEL, userMedicalInfoViewModel);
        intent.putExtra(USER_FROM_DETECTED, isFromDetected);
        startActivity(intent);
    }

    @Override
    public void onCheckMedicationInfo(UserViewModel userMedicalInfoViewModel, boolean isFromDetected) {
        Intent intent = new Intent(this, MedicationActivity.class);
        intent.putExtra(USER_VIEW_MODEL, userMedicalInfoViewModel);
        intent.putExtra(USER_FROM_DETECTED, isFromDetected);
        startActivity(intent);
    }


}

package es.jescuderv.unex.facetrackernearbytfg.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.fragment.AdvertiserMainInfoFragment;
import es.jescuderv.unex.facetrackernearbytfg.ui.fragment.AdvertiserMedicalInfoFragment;
import es.jescuderv.unex.facetrackernearbytfg.ui.fragment.AdvertiserMedicationFragment;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.AdvertiserPresenter;

public class AdvertiserActivity extends DaggerAppCompatActivity implements AdvertiserContract.View {

    private final int PICK_IMAGE = 1;

    @BindView(R.id.advertiser_view_pager)
    ViewPager mViewPager;

    @BindView(R.id.advertiser_profile_image)
    ImageView mProfileImage;

    @Inject
    AdvertiserPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser);
        ButterKnife.bind(this);

//        // Start service when start screen. TODO: start service in splash or something like that
//        Intent startIntent = new Intent(getApplicationContext(), AdvertiseService.class);
//        startIntent.setAction(ACTION_START_SERVICE);
//        startService(startIntent);

        FragmentStatePagerAdapter pagerAdapter = new ProfileAdapter(getSupportFragmentManager());
        mViewPager.setPageTransformer(true, new ExpandingViewPagerTransformer());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setClipToPadding(false);
        mViewPager.setPadding(48, 0, 48, 0);
        mViewPager.setPageMargin(-100);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(AdvertiserActivity.this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }

    @OnClick(R.id.advertiser_settings_button)
    public void onSettingsClick() {
        startActivity(new Intent(this, AdvertiserSettingsActivity.class));
    }

    @OnClick(R.id.advertiser_profile_image)
    public void setProfileImage() {
        Intent gallIntent = new Intent(Intent.ACTION_GET_CONTENT);
        gallIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(gallIntent, "Select Picture"), PICK_IMAGE);
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
    public void showFaceDetectSuccessMessage(Bitmap faceBitmap) {
        mProfileImage.setVisibility(View.VISIBLE);
        mProfileImage.setImageBitmap(faceBitmap);
        Toast.makeText(this, "Face detected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFaceDetectFailureMessage(String message) {
        mProfileImage.setVisibility(View.VISIBLE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private class ProfileAdapter extends FragmentStatePagerAdapter {

        public ProfileAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AdvertiserMainInfoFragment();
                case 1:
                    return new AdvertiserMedicalInfoFragment();
                case 2:
                    return new AdvertiserMedicationFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}

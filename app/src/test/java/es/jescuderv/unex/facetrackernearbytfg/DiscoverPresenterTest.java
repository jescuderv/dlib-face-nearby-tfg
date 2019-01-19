package es.jescuderv.unex.facetrackernearbytfg;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.DetectFace;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.GetNearbyData;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.GetUserEndpointList;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SaveFace;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SendNearbyPayload;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.TakePicture;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.DiscovererContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.DiscoverPresenter;

public class DiscoverPresenterTest {

    @Mock
    private DiscovererContract.View mView;

    @Mock
    private TakePicture mTakePicture;
    @Mock
    private DetectFace mDetectFace;
    @Mock
    private SaveFace mSaveFace;
    @Mock
    private SendNearbyPayload mSendNearbyPayload;
    @Mock
    private GetUserEndpointList mGetUserEndpointList;
    @Mock
    private GetNearbyData mGetNearbyData;


    private DiscoverPresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new DiscoverPresenter(mDetectFace, mTakePicture, mSaveFace, mSendNearbyPayload,
                mGetUserEndpointList, mGetNearbyData);
        mPresenter.attachView(mView);
    }


    @Test
    public void testDisposeUseCase() {
        mPresenter.dropView();

        Mockito.verify(mTakePicture).dispose();
        Mockito.verify(mDetectFace).dispose();
        Mockito.verify(mSaveFace).dispose();
        Mockito.verify(mSendNearbyPayload).dispose();
        Mockito.verify(mGetUserEndpointList).dispose();
        Mockito.verify(mGetNearbyData).dispose();
    }

}

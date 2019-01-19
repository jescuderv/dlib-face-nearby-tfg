package es.jescuderv.unex.facetrackernearbytfg;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.DetectFace;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.GetUserData;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SaveFace;
import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SetUserData;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.AdvertiserContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.presenter.AdvertiserPresenter;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

import static org.mockito.Matchers.any;

public class AdvertiserPresenterTest {

    @Mock
    private AdvertiserContract.View mView;

    @Mock
    private DetectFace mDetectFace;
    @Mock
    private SaveFace mSaveFace;
    @Mock
    private GetUserData mGetUserData;
    @Mock
    private SetUserData mSetUserData;


    private AdvertiserPresenter mPresenter;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new AdvertiserPresenter(mDetectFace, mSaveFace,
                mGetUserData, mSetUserData);
        mPresenter.attachView(mView);
    }


    @Test
    public void testDisposeUseCase() {
        mPresenter.dropView();

        Mockito.verify(mDetectFace).dispose();
        Mockito.verify(mSaveFace).dispose();
        Mockito.verify(mGetUserData).dispose();
        Mockito.verify(mSetUserData).dispose();
    }

    @Test
    public void testSetUserData() {
        mPresenter.setUserData(new UserViewModel());
        Mockito.verify(mSetUserData).execute(any(), any());
    }

    @Test
    public void testGetUserData() {
        mPresenter.getUserData();
        Mockito.verify(mGetUserData).execute(any(), any());
    }

}

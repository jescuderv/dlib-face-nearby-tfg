package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SetUserData;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.PersonalInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.mapper.UserMapper;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserPersonalInfoViewModel;
import es.jescuderv.unex.facetrackernearbytfg.utils.di.scopes.ActivityScoped;
import io.reactivex.observers.DisposableCompletableObserver;

@ActivityScoped
public class PersonalInfoPresenter implements PersonalInfoContract.Presenter {

    private PersonalInfoContract.View mView;


    private SetUserData mSetUserData;

    @Inject
    PersonalInfoPresenter(SetUserData setUserData) {

        mSetUserData = setUserData;
    }


    @Override
    public void setUserPersonalData(Integer userId, String name, String lastName, String birthDate, String phoneNumber,
                                    String address, String description) {

        if (checkEmptyFields(name, lastName, birthDate, phoneNumber, address, description)) {
            mView.showEmptyFieldsMessage();
            return;
        }

        UserPersonalInfoViewModel user = setUserInfo(userId, name, lastName, birthDate, phoneNumber,
                address, description);

        mSetUserData.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mView.showSuccessUpdatePersonalInfoMessage();
            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorUpdatePersonalInfoMessage(e.getMessage());
            }
        }, new SetUserData.Params(UserMapper.transform(user)));
    }


    private boolean checkEmptyFields(String name, String lastName, String birthDate, String phoneNumber,
                                     String address, String description) {
        return (name.trim().isEmpty() && lastName.trim().isEmpty() && birthDate.trim().isEmpty() &&
                phoneNumber.trim().isEmpty() && address.trim().isEmpty() && description.trim().isEmpty());
    }

    private UserPersonalInfoViewModel setUserInfo(Integer userId, String name, String lastName, String birthDate,
                                                  String phoneNumber, String address, String description) {
        UserPersonalInfoViewModel user = new UserPersonalInfoViewModel();
        if (userId != null) user.setId(userId);
        user.setUserName(name);
        user.setLastName(lastName);
        user.setBirthday(birthDate);
        user.setPhoneNumber(Integer.valueOf(phoneNumber));
        user.setAddress(address);
        user.setDescription(description);
        return user;
    }


    @Override
    public void attachView(PersonalInfoContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mSetUserData.dispose();
        mView = null;
    }
}

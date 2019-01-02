package es.jescuderv.unex.facetrackernearbytfg.ui.presenter;

import javax.inject.Inject;

import es.jescuderv.unex.facetrackernearbytfg.domain.usecase.SetUserData;
import es.jescuderv.unex.facetrackernearbytfg.ui.contract.PersonalInfoContract;
import es.jescuderv.unex.facetrackernearbytfg.ui.mapper.UserMapper;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;
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
    public void setUserPersonalData(UserViewModel userViewModel, String name, String lastName, String birthDate, String phoneNumber,
                                    String address, String description) {

        if (checkEmptyFields(name, lastName, birthDate, phoneNumber, address, description)) {
            mView.showEmptyFieldsMessage();
            return;
        }

        setUserInfo(userViewModel, name, lastName, birthDate, phoneNumber, address, description);

        mSetUserData.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mView.showSuccessUpdatePersonalInfoMessage();
            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorUpdatePersonalInfoMessage(e.getMessage());
            }
        }, new SetUserData.Params(UserMapper.transform(userViewModel)));
    }


    private boolean checkEmptyFields(String name, String lastName, String birthDate, String phoneNumber,
                                     String address, String description) {
        return (name.trim().isEmpty() && lastName.trim().isEmpty() && birthDate.trim().isEmpty() &&
                phoneNumber.trim().isEmpty() && address.trim().isEmpty() && description.trim().isEmpty());
    }

    private void setUserInfo(UserViewModel userViewModel, String name, String lastName, String birthDate,
                             String phoneNumber, String address, String description) {
        userViewModel.setUserName(name);
        userViewModel.setLastName(lastName);
        userViewModel.setBirthday(birthDate);
        userViewModel.setPhoneNumber(Integer.valueOf(phoneNumber));
        userViewModel.setAddress(address);
        userViewModel.setDescription(description);
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

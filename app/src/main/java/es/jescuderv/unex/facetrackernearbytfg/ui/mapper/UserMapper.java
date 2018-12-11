package es.jescuderv.unex.facetrackernearbytfg.ui.mapper;

import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserPersonalInfoViewModel;

public class UserMapper {

    public static UserPersonalInfoViewModel transform(User user) {
        UserPersonalInfoViewModel userViewModel = new UserPersonalInfoViewModel();

        userViewModel.setId(user.getId());
        userViewModel.setUserName(user.getUserName());
        userViewModel.setLastName(user.getLastName());
        userViewModel.setBirthday(user.getBirthday());
        userViewModel.setPhoneNumber(user.getPhoneNumber());
        userViewModel.setAddress(user.getAddress());
        userViewModel.setDescription(user.getDescription());

        return userViewModel;
    }

    public static User transform(UserPersonalInfoViewModel viewModel) {
        User user = new User();

        user.setId(viewModel.getId());
        user.setUserName(viewModel.getUserName());
        user.setLastName(viewModel.getLastName());
        user.setBirthday(viewModel.getBirthday());
        user.setPhoneNumber(viewModel.getPhoneNumber());
        user.setAddress(viewModel.getAddress());
        user.setDescription(viewModel.getDescription());

        return user;
    }
}

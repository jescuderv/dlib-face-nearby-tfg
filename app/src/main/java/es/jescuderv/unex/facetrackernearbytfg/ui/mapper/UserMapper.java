package es.jescuderv.unex.facetrackernearbytfg.ui.mapper;

import java.util.ArrayList;
import java.util.List;

import es.jescuderv.unex.facetrackernearbytfg.domain.model.Allergy;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.Intolerance;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.Surgery;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

public class UserMapper {

    public static UserViewModel transform(User user) {
        UserViewModel userViewModel = new UserViewModel();

        userViewModel.setId(user.getId());
        userViewModel.setUserName(user.getUserName());
        userViewModel.setLastName(user.getLastName());
        userViewModel.setBirthday(user.getBirthday());
        userViewModel.setPhoneNumber(user.getPhoneNumber());
        userViewModel.setAddress(user.getAddress());
        userViewModel.setDescription(user.getDescription());

        userViewModel.setBloodType(user.getBloodType());
        userViewModel.setMedicalDescription(user.getMedicalDescription());

        List<UserViewModel.Allergy> allergies = new ArrayList<>();
        for (Allergy allergy : user.getAllergyList()) {
            allergies.add(new UserViewModel.Allergy(allergy.getId(), allergy.getName()));
        }
        userViewModel.setAllergyList(allergies);

        List<UserViewModel.Intolerance> intolerances = new ArrayList<>();
        for (Intolerance intolerance : user.getIntoleranceList()) {
            intolerances.add(new UserViewModel.Intolerance(intolerance.getId(),
                    intolerance.getName()));
        }
        userViewModel.setIntoleranceList(intolerances);

        List<UserViewModel.Surgery> surgeries = new ArrayList<>();
        for (Surgery surgery : user.getSurgeryList()) {
            surgeries.add(new UserViewModel.Surgery(surgery.getId(), surgery.getName()));
        }
        userViewModel.setSurgeryList(surgeries);

        return userViewModel;
    }

    public static User transform(UserViewModel userViewModel) {
        User user = new User();

        user.setId(userViewModel.getId());
        user.setUserName(userViewModel.getUserName());
        user.setLastName(userViewModel.getLastName());
        user.setBirthday(userViewModel.getBirthday());
        user.setPhoneNumber(userViewModel.getPhoneNumber());
        user.setAddress(userViewModel.getAddress());
        user.setDescription(userViewModel.getDescription());

        user.setBloodType(userViewModel.getBloodType());
        user.setMedicalDescription(userViewModel.getMedicalDescription());

        List<Allergy> allergies = new ArrayList<>();
        for (UserViewModel.Allergy allergy : userViewModel.getAllergyList()) {
            allergies.add(new Allergy(allergy.getId(), allergy.getName()));
        }
        user.setAllergyList(allergies);

        List<Intolerance> intolerances = new ArrayList<>();
        for (UserViewModel.Intolerance intolerance : userViewModel.getIntoleranceList()) {
            intolerances.add(new Intolerance(intolerance.getId(), intolerance.getName()));
        }
        user.setIntoleranceList(intolerances);

        List<Surgery> surgeries = new ArrayList<>();
        for (UserViewModel.Surgery surgery : userViewModel.getSurgeryList()) {
            surgeries.add(new Surgery(surgery.getId(), surgery.getName()));
        }
        user.setSurgeryList(surgeries);

        return user;
    }

}


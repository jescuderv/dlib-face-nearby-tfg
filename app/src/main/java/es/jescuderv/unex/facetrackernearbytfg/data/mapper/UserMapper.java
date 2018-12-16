package es.jescuderv.unex.facetrackernearbytfg.data.mapper;

import java.util.ArrayList;
import java.util.List;

import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.AllergyEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.IntoleranceEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.SurgeryEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.UserEntity;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.Allergy;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.Intolerance;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.Surgery;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;

public class UserMapper {

    public static UserEntity transform(User user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(user.getId());
        userEntity.setUserName(user.getUserName());
        userEntity.setLastName(user.getLastName());
        userEntity.setBirthday(user.getBirthday());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setAddress(user.getAddress());
        userEntity.setDescription(user.getDescription());

        userEntity.setBloodType(user.getBloodType());
        userEntity.setMedicalDescription(user.getMedicalDescription());

        return userEntity;
    }

    public static AllergyEntity transform(Allergy allergy, Long userId) {
        AllergyEntity allergyEntity = new AllergyEntity();

        allergyEntity.setId(allergy.getId());
        allergyEntity.setName(allergy.getName());
        allergyEntity.setUserId(userId);

        return allergyEntity;
    }

    public static SurgeryEntity transform(Surgery surgery, Long userId) {
        SurgeryEntity surgeryEntity = new SurgeryEntity();

        surgeryEntity.setId(surgery.getId());
        surgeryEntity.setName(surgery.getName());
        surgeryEntity.setUserId(userId);

        return surgeryEntity;
    }

    public static IntoleranceEntity transform(Intolerance intolerance, Long userId) {
        IntoleranceEntity intoleranceEntity = new IntoleranceEntity();

        intoleranceEntity.setId(intolerance.getId());
        intoleranceEntity.setName(intolerance.getName());
        intoleranceEntity.setUserId(userId);

        return intoleranceEntity;
    }


    public static User transform(UserEntity entity, List<AllergyEntity> allergyEntityList, List<SurgeryEntity>
            surgeryEntityList, List<IntoleranceEntity> intoleranceEntityList) {

        User user = new User();

        user.setId(entity.getId());
        user.setUserName(entity.getUserName());
        user.setLastName(entity.getLastName());
        user.setBirthday(entity.getBirthday());
        user.setPhoneNumber(entity.getPhoneNumber());
        user.setAddress(entity.getAddress());
        user.setDescription(entity.getDescription());


        user.setBloodType(entity.getBloodType());
        user.setMedicalDescription(entity.getMedicalDescription());

        List<Allergy> allergies = new ArrayList<>();
        for (AllergyEntity allergyEntity : allergyEntityList) {
            allergies.add(transform(allergyEntity));
        }
        user.setAllergyList(allergies);

        List<Surgery> surgeries = new ArrayList<>();
        for (SurgeryEntity surgeryEntity : surgeryEntityList) {
            surgeries.add(transform(surgeryEntity));
        }
        user.setSurgeryList(surgeries);

        List<Intolerance> intolerances = new ArrayList<>();
        for (IntoleranceEntity intoleranceEntity : intoleranceEntityList) {
            intolerances.add(transform(intoleranceEntity));
        }
        user.setIntoleranceList(intolerances);

        return user;
    }

    private static Allergy transform(AllergyEntity entity) {
        return new Allergy(entity.getId(), entity.getName());
    }

    private static Surgery transform(SurgeryEntity entity) {
        return new Surgery(entity.getId(), entity.getName());
    }

    private static Intolerance transform(IntoleranceEntity entity) {
        return new Intolerance(entity.getId(), entity.getName());
    }

}

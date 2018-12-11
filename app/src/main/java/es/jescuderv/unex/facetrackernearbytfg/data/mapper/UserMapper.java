package es.jescuderv.unex.facetrackernearbytfg.data.mapper;

import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.UserEntity;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;

public class UserMapper {

    public static User transform(UserEntity entity) {
        User user = new User();

        user.setId(entity.getId());
        user.setUserName(entity.getUserName());
        user.setLastName(entity.getLastName());
        user.setBirthday(entity.getBirthday());
        user.setPhoneNumber(entity.getPhoneNumber());
        user.setAddress(entity.getAddress());
        user.setDescription(entity.getDescription());

        return user;
    }

    public static UserEntity transform(User user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(user.getId());
        userEntity.setUserName(user.getUserName());
        userEntity.setLastName(user.getLastName());
        userEntity.setBirthday(user.getBirthday());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setAddress(user.getAddress());
        userEntity.setDescription(user.getDescription());

        return userEntity;
    }

}

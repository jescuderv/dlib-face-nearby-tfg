package es.jescuderv.unex.facetrackernearbytfg.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.UserEntity;
import io.reactivex.Single;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long addUser(UserEntity userEntity);

    @Query("SELECT * FROM UserEntity")
    Single<UserEntity> getUser();

}

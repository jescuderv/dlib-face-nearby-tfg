package es.jescuderv.unex.facetrackernearbytfg.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.AllergyEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.HearthBeatMedicationEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.IntoleranceEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.DiabetesMedicationEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.SurgeryEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.UserEntity;
import io.reactivex.Single;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long addUser(UserEntity userEntity);

    @Query("SELECT * FROM UserEntity")
    Single<UserEntity> getUser();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long addAllergy(AllergyEntity allergyEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long addSurgery(SurgeryEntity surgeryEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long addIntolerance(IntoleranceEntity intoleranceEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long addDiabetesMedication(DiabetesMedicationEntity diabetesEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long addHearthBeatMedication(HearthBeatMedicationEntity hearthBeatMedicationEntity);


    @Query("SELECT * FROM AllergyEntity WHERE user_id = :userId")
    List<AllergyEntity> getAllergies(Integer userId);

    @Query("SELECT * FROM SurgeryEntity WHERE user_id = :userId")
    List<SurgeryEntity> getSurgeries(Integer userId);

    @Query("SELECT * FROM IntoleranceEntity WHERE user_id = :userId")
    List<IntoleranceEntity> getIntolerances(Integer userId);

    @Query("SELECT * FROM DiabetesMedicationEntity WHERE user_id = :userId")
    List<DiabetesMedicationEntity> getDiabetesMedication(Integer userId);

    @Query("SELECT * FROM HearthBeatMedicationEntity WHERE user_id = :userId")
    List<HearthBeatMedicationEntity> getHearthBeatMedication(Integer userId);
}

package es.jescuderv.unex.facetrackernearbytfg.data.local;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.jescuderv.unex.facetrackernearbytfg.data.UserDataSource;
import es.jescuderv.unex.facetrackernearbytfg.data.exception.DatabaseErrorException;
import es.jescuderv.unex.facetrackernearbytfg.data.local.dao.UserDao;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.AllergyEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.DiabetesMedicationEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.HearthBeatMedicationEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.IntoleranceEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.SurgeryEntity;
import es.jescuderv.unex.facetrackernearbytfg.data.mapper.UserMapper;
import es.jescuderv.unex.facetrackernearbytfg.data.preferences.SessionPreferences;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.Allergy;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.Intolerance;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.Medication;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.Surgery;
import es.jescuderv.unex.facetrackernearbytfg.domain.model.User;
import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class UserLocalDataSource implements UserDataSource {

    private LocalDataBase mDataBase;
    private UserDao mUserDao;


    @Inject
    UserLocalDataSource(LocalDataBase localDataBase) {
        mDataBase = localDataBase;
        mUserDao = localDataBase.userDao();
    }


    @Override
    public Completable setUserData(User user) {
        Long userId = mUserDao.addUser(UserMapper.transform(user));
        if (userId != null) {

            for (Allergy allergy : user.getAllergyList()) {
                mUserDao.addAllergy(UserMapper.transform(allergy, userId));
            }

            for (Surgery surgery : user.getSurgeryList()) {
                mUserDao.addSurgery(UserMapper.transform(surgery, userId));
            }

            for (Intolerance intolerance : user.getIntoleranceList()) {
                mUserDao.addIntolerance(UserMapper.transform(intolerance, userId));
            }

            for (Medication diabetes : user.getDiabetesList()){
                mUserDao.addDiabetesMedication(UserMapper.transform(diabetes, userId));
            }

            for (Medication hearthBeat : user.getHearthBeatList()){
                mUserDao.addHearthBeatMedication(UserMapper.transform_(hearthBeat, userId));
            }

            return Completable.complete();
        }

        return Completable.error(new DatabaseErrorException("No se pudo insertar el usuario"));
    }

    @Override
    public Observable<User> getUserData() {
        List<AllergyEntity> allergies = new ArrayList<>();
        List<SurgeryEntity> surgeries = new ArrayList<>();
        List<IntoleranceEntity> intolerances = new ArrayList<>();
        List<DiabetesMedicationEntity> diabetes = new ArrayList<>();
        List<HearthBeatMedicationEntity> heartBeat = new ArrayList<>();
        return mUserDao.getUser()
                .toObservable()
                .doOnNext(userEntity -> {
                    allergies.addAll(mUserDao.getAllergies(userEntity.getId()));
                    surgeries.addAll(mUserDao.getSurgeries(userEntity.getId()));
                    intolerances.addAll(mUserDao.getIntolerances(userEntity.getId()));
                    diabetes.addAll(mUserDao.getDiabetesMedication(userEntity.getId()));
                    heartBeat.addAll(mUserDao.getHearthBeatMedication(userEntity.getId()));

                }).map(entity -> UserMapper.transform(entity, allergies, surgeries, intolerances, diabetes, heartBeat));
    }

    @Override
    public Completable clearUserData() {
        return Completable.create(
                emitter -> {
                    mDataBase.clearAllTables();
                    SessionPreferences.setSession(0);
                    SessionPreferences.setVisibility(0);
                    emitter.onComplete();
                }
        );
    }

}

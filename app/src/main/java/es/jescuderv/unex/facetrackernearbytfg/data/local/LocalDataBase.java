package es.jescuderv.unex.facetrackernearbytfg.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import es.jescuderv.unex.facetrackernearbytfg.data.local.dao.UserDao;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.UserEntity;


/**
 * The Room Database that contains the FaceDTO table.
 */
@Database(entities = {UserEntity.class}, version = 3, exportSchema = false)
public abstract class LocalDataBase extends RoomDatabase {

    public abstract UserDao userDao();

}
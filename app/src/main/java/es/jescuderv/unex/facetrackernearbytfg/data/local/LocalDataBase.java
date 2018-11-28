package es.jescuderv.unex.facetrackernearbytfg.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import es.jescuderv.unex.facetrackernearbytfg.data.local.dao.UserDao;
import es.jescuderv.unex.facetrackernearbytfg.data.local.entity.User;


/**
 * The Room Database that contains the FaceDTO table.
 */
@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class LocalDataBase extends RoomDatabase {

    public abstract UserDao userDao();

}
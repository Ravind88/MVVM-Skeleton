package skeleton.maurya.com.mvvmskeleton.model.repository.local;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import skeleton.maurya.com.mvvmskeleton.model.repository.local.dao.IContactDao;
import skeleton.maurya.com.mvvmskeleton.model.repository.local.entity.Contact;

/**
 * Main database, containing entities(tables) and version of database
 * created using annotation provided by RoomDatabase library
 */
@Database(entities = {Contact.class}, version = 01)
public abstract class AppDb extends RoomDatabase {


    abstract public IContactDao contactDao();

}

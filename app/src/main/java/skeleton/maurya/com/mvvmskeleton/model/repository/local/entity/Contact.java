package skeleton.maurya.com.mvvmskeleton.model.repository.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * for demo purpose , created table name with contact
 */
@Entity
public class Contact  {

    @PrimaryKey
    private long ctId;

    public long getCtId() {
        return ctId;
    }

    public void setCtId(long ctId) {
        this.ctId = ctId;
    }


}

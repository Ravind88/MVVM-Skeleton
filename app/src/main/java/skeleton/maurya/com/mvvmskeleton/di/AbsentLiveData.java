package skeleton.maurya.com.mvvmskeleton.di;

import android.arch.lifecycle.LiveData;

/**
 * Empty LiveData
 */

public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(null);
    }

    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}

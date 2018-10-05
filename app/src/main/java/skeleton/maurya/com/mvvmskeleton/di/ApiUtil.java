package skeleton.maurya.com.mvvmskeleton.di;

/**
 * convert normal data to live data
 */
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

public class ApiUtil {
    public static <T> LiveData<T> successCall(T data) {
        return createCall(data);
    }
    public static <T> LiveData<T> createCall(T response) {
        MutableLiveData<T> data = new MutableLiveData<>();
        data.setValue(response);
        return data;
    }
}

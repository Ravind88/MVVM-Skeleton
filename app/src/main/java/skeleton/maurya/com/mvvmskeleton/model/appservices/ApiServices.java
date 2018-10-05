package skeleton.maurya.com.mvvmskeleton.model.appservices;

import android.arch.lifecycle.LiveData;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import skeleton.maurya.com.mvvmskeleton.model.repository.modal.CheckUserType;

/**
 * declare all the api method and with their method type, this interface will generate the api call.
 */
public interface ApiServices {

    @Headers(ApiConstant.CONTENT_TYPE)

    @GET(ApiConstant.CHECKUSERTYPE)
    LiveData<ApiResponse<CheckUserType>> getUserTypeResponse(@Path("userId") String input);



}


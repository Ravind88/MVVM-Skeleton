package skeleton.maurya.com.mvvmskeleton.model.appservices;

import android.arch.lifecycle.LiveData;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import skeleton.maurya.com.mvvmskeleton.model.repository.modal.CheckUserType;

public interface ApiServices {

    @Headers(ApiConstant.CONTENT_TYPE)

    @GET(ApiConstant.CHECKUSERTYPE)
    LiveData<ApiResponse<CheckUserType>> getUserTypeResponse(@Path("userId") String input);



}


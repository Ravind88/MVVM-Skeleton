package skeleton.maurya.com.mvvmskeleton.model.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import skeleton.maurya.com.mvvmskeleton.di.AppExecutors;
import skeleton.maurya.com.mvvmskeleton.di.NetworkBoundResource;
import skeleton.maurya.com.mvvmskeleton.model.appservices.ApiResponse;
import skeleton.maurya.com.mvvmskeleton.model.appservices.ApiServices;
import skeleton.maurya.com.mvvmskeleton.model.appservices.Resource;
import skeleton.maurya.com.mvvmskeleton.model.repository.modal.CheckUserType;

/**
 *
 */
public class SplashRepository {

    private final ApiServices apiService;
    private final AppExecutors appExecutors;

    @Inject
    SplashRepository(AppExecutors appExecutors, ApiServices appService) {
        this.apiService = appService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<CheckUserType>> getUserTypeResponse(final String input) {
        return new NetworkBoundResource<CheckUserType, CheckUserType>(appExecutors) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<CheckUserType>> createCall() {
                return apiService.getUserTypeResponse(input);
            }

            @Override
            protected void saveCallResult(@NonNull CheckUserType item) {
// save data into db
            }
        }.asLiveData();
    }

}

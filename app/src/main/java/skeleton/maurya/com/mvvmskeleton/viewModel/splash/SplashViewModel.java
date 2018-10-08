package skeleton.maurya.com.mvvmskeleton.viewModel.splash;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import skeleton.maurya.com.mvvmskeleton.di.AbsentLiveData;
import skeleton.maurya.com.mvvmskeleton.di.Objects;
import skeleton.maurya.com.mvvmskeleton.model.repository.remote.Resource;
import skeleton.maurya.com.mvvmskeleton.model.repository.SplashRepository;
import skeleton.maurya.com.mvvmskeleton.model.repository.Pojo.CheckUserType;

/**
 *
 */
public class SplashViewModel extends ViewModel {
    private SplashRepository splashRepository;
    LiveData<Resource<CheckUserType>> userTypeResponse;
    MutableLiveData<String> reqCheckUser = new MutableLiveData<>();

    @Inject
    SplashViewModel(final SplashRepository splashRepo) {
        this.splashRepository = splashRepo;
        userTypeResponse = Transformations.switchMap(reqCheckUser, new Function<String, LiveData<Resource<CheckUserType>>>() {
            @Override
            public LiveData<Resource<CheckUserType>> apply(String input) {
                if (input == null)
                    return AbsentLiveData.create();
                else
                    return splashRepository.getUserTypeResponse(input);
            }
        });
    }


    public LiveData<Resource<CheckUserType>> getGlobalSettingData() {
        return userTypeResponse;
    }

    public void setGlobalSettingReq(String input) {
        if (Objects.equals(this.reqCheckUser.getValue(), input))
            return;

        reqCheckUser.setValue(input);
    }


}

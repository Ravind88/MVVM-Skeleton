package skeleton.maurya.com.mvvmskeleton.view.splash;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


import javax.inject.Inject;

import skeleton.maurya.com.mvvmskeleton.BaseFragment;
import skeleton.maurya.com.mvvmskeleton.R;
import skeleton.maurya.com.mvvmskeleton.databinding.FrmSplashBinding;
import skeleton.maurya.com.mvvmskeleton.model.appservices.Resource;
import skeleton.maurya.com.mvvmskeleton.model.repository.modal.CheckUserType;
import skeleton.maurya.com.mvvmskeleton.view.common.NavigationController;
import skeleton.maurya.com.mvvmskeleton.viewModel.splash.SplashViewModel;

/**
 *
 */
public class SplashFragment extends BaseFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    SplashViewModel splashViewModel;

    @Inject
    NavigationController navigationController;
    FrmSplashBinding frmSplashBinding;


    public static final String TAG = SplashFragment.class.toString();

    @Override
    protected int getLayoutId() {
        return R.layout.frm_splash;
    }

    //returning instant of current fragment
    public static SplashFragment getInstance(Bundle bundle) {
        SplashFragment splashFragment = new SplashFragment();
        splashFragment.setArguments(bundle);
        return splashFragment;
    }

    @Override
    protected void onViewsInitialized(ViewDataBinding binding, View view) {
        frmSplashBinding= (FrmSplashBinding) binding;
        splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
        if (splashViewModel.getGlobalSettingData().hasActiveObservers()) {
            splashViewModel.getGlobalSettingData().removeObservers(this);
        }
      /*  splashViewModel.setGlobalSettingReq("VEUBK");
        splashViewModel.getGlobalSettingData().observe(this, new Observer<Resource<CheckUserType>>() {
            @Override
            public void onChanged(@Nullable Resource<CheckUserType> checkUserTypeResource) {
                if (checkUserTypeResource != null) {
                    // navigationController.navigateToParticipant();
                }
            }
        });*/

    }


}

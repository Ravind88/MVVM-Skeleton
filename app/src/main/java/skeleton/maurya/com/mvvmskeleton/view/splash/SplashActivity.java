package skeleton.maurya.com.mvvmskeleton.view.splash;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import skeleton.maurya.com.mvvmskeleton.BaseActivity;
import skeleton.maurya.com.mvvmskeleton.R;

public class SplashActivity extends BaseActivity implements HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initUI(ViewDataBinding binding) {
        showFragment(getIntent().getExtras(), SplashFragment.TAG);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void showFragment(Bundle bundle, String tag) {
        if (tag.equalsIgnoreCase(SplashFragment.TAG)) {
            SplashFragment frag = SplashFragment.getInstance(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, frag, tag).commitAllowingStateLoss();
        }
    }
}

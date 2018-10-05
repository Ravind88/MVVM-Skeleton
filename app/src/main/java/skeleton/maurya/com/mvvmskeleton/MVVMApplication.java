package skeleton.maurya.com.mvvmskeleton;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.Service;
import android.content.BroadcastReceiver;


import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.HasServiceInjector;
import skeleton.maurya.com.mvvmskeleton.di.AppInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MVVMApplication extends Application implements HasActivityInjector, HasServiceInjector, HasBroadcastReceiverInjector, HasFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Service> injector;
    @Inject
    DispatchingAndroidInjector<BroadcastReceiver> androidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );

        AppInjector.init(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return broadcastReceiverInjector();
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return injector;
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}

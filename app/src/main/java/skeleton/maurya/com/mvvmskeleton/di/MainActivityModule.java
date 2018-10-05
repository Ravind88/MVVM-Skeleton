package skeleton.maurya.com.mvvmskeleton.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import skeleton.maurya.com.mvvmskeleton.view.splash.SplashActivity;

/**
 *
 */
@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = FragmentSplashBuilderModule.class)
    abstract SplashActivity contributeSplashActivity();




}

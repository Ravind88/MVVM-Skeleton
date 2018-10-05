package skeleton.maurya.com.mvvmskeleton.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import skeleton.maurya.com.mvvmskeleton.view.splash.SplashFragment;

/**
 *
 */
@Module
public abstract class FragmentSplashBuilderModule {
    @ContributesAndroidInjector
    abstract SplashFragment contributeSplashFragment();

}

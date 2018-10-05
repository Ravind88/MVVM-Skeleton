package skeleton.maurya.com.mvvmskeleton.di;


import android.app.Application;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import skeleton.maurya.com.mvvmskeleton.MVVMApplication;

@Singleton
@Component(modules = {
        AppModule.class,
        AndroidSupportInjectionModule.class,
        MainActivityModule.class,


})


public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(MVVMApplication sampleApp);

}

package skeleton.maurya.com.mvvmskeleton.di;


import android.app.Application;
import android.arch.persistence.room.Room;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import skeleton.maurya.com.mvvmskeleton.BuildConfig;
import skeleton.maurya.com.mvvmskeleton.model.repository.local.AppDb;
import skeleton.maurya.com.mvvmskeleton.model.repository.local.DbConstant;
import skeleton.maurya.com.mvvmskeleton.model.repository.local.dao.IContactDao;
import skeleton.maurya.com.mvvmskeleton.model.repository.remote.ApiConstant;
import skeleton.maurya.com.mvvmskeleton.model.repository.remote.ApiServices;
import skeleton.maurya.com.mvvmskeleton.model.repository.remote.LiveDataCallAdapterFactory;
import skeleton.maurya.com.mvvmskeleton.model.repository.remote.ToStringConverter;

/**
 * used for provide the Injection and create an optimal object in th application via dagger
 */
@Module(includes = ViewModelModule.class)
class AppModule {

    @Singleton
    @Provides
    ApiServices provideAppService() {

        // To show the Api Request & Params
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(ApiConstant.API_TIME_OUT, TimeUnit.MILLISECONDS);
        httpClient.addInterceptor(logging).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.header("Content-Type", "application/json");
//                builder.header("User-Agent", ApiConstants.USER_AGENT_VALUE);
//                builder.header("_appid", AppConstants.AppId + "");
                return chain.proceed(builder.build());
            }
        });

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL).addConverterFactory(new ToStringConverter())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(httpClient.build())
                .build()
                .create(ApiServices.class);
    }


    @Singleton
    @Provides
    AppDb provideDb(Application app) {
        return Room.databaseBuilder(app, AppDb.class, DbConstant.DATABASENAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    @Singleton
    @Provides
    IContactDao provideContactDao(AppDb db) {
        return db.contactDao();
    }

}

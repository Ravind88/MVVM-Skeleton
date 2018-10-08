package skeleton.maurya.com.mvvmskeleton.model.repository.remote;

/**
 *
 */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import skeleton.maurya.com.mvvmskeleton.BaseActivity;
import skeleton.maurya.com.mvvmskeleton.R;
import skeleton.maurya.com.mvvmskeleton.di.ApiUtil;
import skeleton.maurya.com.mvvmskeleton.di.AppExecutors;
import skeleton.maurya.com.mvvmskeleton.di.AppInjector;
import skeleton.maurya.com.mvvmskeleton.utils.GlobalUtility;
import skeleton.maurya.com.mvvmskeleton.view.common.DialogType;

/**
 * deal with api library and provide the call backs for api response
 * It also handles internet check and provide live data for calling point.
 *
 * @param <ResultType>  custom bean for response
 * @param <RequestType> custom bean/primitive data source for  request
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    private static final MutableLiveData ABSENT = new MutableLiveData<>();

    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    @MainThread
    protected NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        if (GlobalUtility.isNetworkAvailable(AppInjector.getActivity()))
            fetchFromNetwork();
        else {
            ((BaseActivity) AppInjector.getActivity()).showConnectionDialog("", AppInjector.getActivity().getResources().getString(R.string.dialog_title_message), AppInjector.getActivity().getResources().getString(R.string.error_no_internet), DialogType.DIALOG_OK, null);

            result.addSource(ABSENT, new Observer<ResultType>() {
                @Override
                public void onChanged(@Nullable ResultType resultType) {
                    result.setValue(Resource.error(AppInjector.getActivity().getResources().getString(R.string.con_network_error), resultType, ApiConstant.NETWORK_STATUS_CODE));
                }
            });
        }


    }

    private void fetchFromNetwork() {
        GlobalUtility.showProgress(true, AppInjector.getActivity().getResources().getString(R.string.msg_plz_wait));
        final LiveData<ApiResponse<ResultType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(ABSENT, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType resultType) {
                result.setValue(Resource.loading(resultType, ApiConstant.ZERO_STATUS_CODE));
            }
        });
        result.addSource(apiResponse, new Observer<ApiResponse<ResultType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<ResultType> requestTypeApiResponse) {
                result.removeSource(apiResponse);
                result.removeSource(ABSENT);
                if (requestTypeApiResponse.isSuccessful()) {
                    appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            NetworkBoundResource.this.saveCallResult(NetworkBoundResource.this.processResponse((ApiResponse<RequestType>) requestTypeApiResponse));
                            appExecutors.mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    result.addSource(ApiUtil.successCall(requestTypeApiResponse.body), new Observer<ResultType>() {
                                        @Override
                                        public void onChanged(@Nullable ResultType resultType) {
                                            result.setValue(Resource.success(resultType, requestTypeApiResponse.code));
                                            GlobalUtility.showProgress(false, "");

                                        }
                                    });
                                }
                            });
                        }
                    });
                } else {
                    GlobalUtility.showProgress(false, "");
                    onFetchFailed();
                    result.addSource(ABSENT, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            result.setValue(Resource.error(requestTypeApiResponse.errorMessage, resultType, requestTypeApiResponse.code));
                        }
                    });
                }
            }
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<ResultType>> createCall();

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }


}
/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package skeleton.maurya.com.mvvmskeleton.model.repository.remote;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import skeleton.maurya.com.mvvmskeleton.di.AppExecutors;

/**
 * A generic class that can provide a resource backed by network only.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NetworkBoundWtLocalDBResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundWtLocalDBResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        setNetworkBoundResource();

    }

    private void setNetworkBoundResource() {
        //  result.setValue((Resource<ResultType>) Resource.loading(null, AppConstant.ZERO_STATUS_CODE));
        final LiveData<ResultType> resultTypeLiveData = loadFromDb();
        result.addSource(resultTypeLiveData, data -> {
            result.removeSource(resultTypeLiveData);
            if (shouldFetch(data)) {
                fetchFromNetwork(resultTypeLiveData);
                result.addSource(resultTypeLiveData, new Observer<ResultType>() {
                            @Override
                            public void onChanged(@Nullable ResultType newData) {
                                result.setValue(Resource.success(newData, ApiConstant.ZERO_STATUS_CODE));
                            }
                        }
                );


            } else {
                result.addSource(resultTypeLiveData, new Observer<ResultType>() {
                            @Override
                            public void onChanged(@Nullable ResultType newData) {
                                result.setValue(Resource.success(newData, ApiConstant.ZERO_STATUS_CODE));
                            }
                        }
                );


            }

        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> resultTypeLiveData) {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<RequestType> resultTypeApiResponse) {
                result.removeSource(apiResponse);
                result.removeSource(resultTypeLiveData);
                //noinspection ConstantConditions
                if (resultTypeApiResponse.isSuccessful()) {
                    final LiveData<ResultType> resultTypeData = updateLatestFromServer(resultTypeApiResponse.body);
                    result.addSource(resultTypeData,
                            newData -> {
                                result.removeSource(resultTypeData);
                                result.setValue(Resource.success(newData, resultTypeApiResponse.code));

                            });
                    appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            saveCallResult(processResponse(resultTypeApiResponse));

                        }
                    });


                } else {
                    onFetchFailed();
                    result.addSource(resultTypeLiveData, new Observer<ResultType>() {
                                @Override
                                public void onChanged(@Nullable ResultType resultType) {
                                    result.setValue(Resource.error(resultTypeApiResponse.errorMessage, resultType, resultTypeApiResponse.code));
                                }
                            }
                    );


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
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> updateLatestFromServer(@NonNull RequestType item);

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }


}

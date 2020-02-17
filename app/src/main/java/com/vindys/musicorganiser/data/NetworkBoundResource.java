package com.vindys.musicorganiser.data;

import com.vindys.musicorganiser.data.repository.ApiResponse;
import com.vindys.musicorganiser.utils.AppExecutors;
import com.vindys.musicorganiser.utils.ObjectUtils;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public abstract class NetworkBoundResource <ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        appExecutors.diskIO().execute(() -> {
            if (!preExecute()) {
                result.postValue(Resource.error("Pre Execution Failed", 401, new RuntimeException("Pre Execution Condition Failed")));
                return;
            }
            appExecutors.mainThread().execute(() -> {
                LiveData<ResultType> dbSource = loadFromDb();
                result.addSource(dbSource, data -> {
                    result.removeSource(dbSource);
                    if (shouldFetch(data)) {
                        preNetworkOperations();
                        fetchFromNetwork(dbSource);
                    } else {
                        result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
                        onSuccess();
                    }
                });
            });
        });
    }

    @MainThread
    protected boolean preExecute() {
        return true;
    }

    @MainThread
    protected void preNetworkOperations() {
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!ObjectUtils.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.diskIO().execute(() -> {
                    saveCallResult(processResponse(response));
                    appExecutors.mainThread().execute(() -> {
                        result.addSource(loadFromDb(), newData -> setValue(Resource.success(newData)));
                        onSuccess();
                    });
                });
            } else {
                onFetchFailed();
                if (response.actionError != null)
                    result.postValue(Resource.error(response.errorMessage, response.code, response.actionError));
                else
                    result.addSource(dbSource, newData -> result.postValue(Resource.error(response.errorMessage, response.code, newData)));
            }
        });
    }

    protected void onFetchFailed() {
    }

    protected void onSuccess() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
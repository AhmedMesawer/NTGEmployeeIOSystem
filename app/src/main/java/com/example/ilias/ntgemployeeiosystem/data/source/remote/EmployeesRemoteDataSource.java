package com.example.ilias.ntgemployeeiosystem.data.source.remote;

import android.util.Log;

import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.data.WorkDay;
import com.example.ilias.ntgemployeeiosystem.data.network.APIError;
import com.example.ilias.ntgemployeeiosystem.data.network.EIOSAPI;
import com.example.ilias.ntgemployeeiosystem.data.network.EIOSServiceInterface;
import com.example.ilias.ntgemployeeiosystem.data.source.EmployeesDataSource;

import java.io.IOException;
import java.lang.annotation.Annotation;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Retrofit;

/**
 * Created by ilias on 20/02/2018.
 */

public class EmployeesRemoteDataSource implements EmployeesDataSource {

    private EIOSServiceInterface service;
    private static EmployeesRemoteDataSource INSTANCE;
    private static final String TAG = EmployeesRemoteDataSource.class.getSimpleName();

    private EmployeesRemoteDataSource() {
        this.service = EIOSAPI.getClient().create(EIOSServiceInterface.class);
    }

    public static EmployeesRemoteDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new EmployeesRemoteDataSource();
        return INSTANCE;
    }

    @Override
    public void getEmployee(String email,
                            SuccessfulResponseWithResultCallback<Employee> resultCallback,
                            FailedResponseCallback failedCallback) {
        service.getEmployee(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultCallback::onSuccess,
                        t -> failedCallback.onError(convertToReadableMessage(t)));
    }

    @Override
    public void addEmployee(Employee employee,
                            SuccessfulResponseWithResultCallback<Employee> resultCallback,
                            FailedResponseCallback failedCallback) {
        service.addEmployee(employee)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultCallback::onSuccess,
                        t -> failedCallback.onError(convertToReadableMessage(t)));
    }

    @Override
    public void addWorkDay(String email, WorkDay workDay,
                           SuccessfulResponseWithResultCallback<WorkDay> resultCallback,
                           FailedResponseCallback failedCallback) {
        service.addWorkDay(email, workDay)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultCallback::onSuccess,
                        throwable -> failedCallback.onError(convertToReadableMessage(throwable)));
    }

    @Override
    public void setEmployeeOut(String email, String date, WorkDay workDay,
                               SuccessfulResponseCallback successCallback,
                               FailedResponseCallback failedCallback) {

    }

    private String convertToReadableMessage(Throwable t) {
        if (t instanceof IOException) {
            return "Server Error: Check your connection";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            Converter<ResponseBody, APIError> converter =
                    EIOSAPI.getClient().responseBodyConverter(APIError.class, new Annotation[0]);
            ResponseBody responseErrorBody = httpException.response().errorBody();
            if (responseErrorBody != null) {
                try {
                    APIError apiError = converter.convert(responseErrorBody);
                    return apiError.getMessage();
                } catch (IOException e) {
                    Log.e(TAG, "HTTP Converting Failed");
                }
            } else {
                return null;
            }
        }
//        return "Unknown Error: Try to Refresh Your App";
        Log.d(TAG, t.toString());
        return t.toString();
    }
}

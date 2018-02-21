package com.example.ilias.ntgemployeeiosystem.data.network;

import com.example.ilias.ntgemployeeiosystem.data.Employee;
import com.example.ilias.ntgemployeeiosystem.data.WorkDay;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ilias on 20/02/2018.
 */

public interface EIOSServiceInterface {

    @GET("employees/getEmployee/{email}")
    Observable<Employee> getEmployee(@Path("email") String email);

    @POST("employees/addEmployee")
    Observable<Employee> addEmployee(@Body Employee employee);

    @POST("employees/{email}/addWorkDay")
    Observable<WorkDay> addWorkDay(@Path("email") String email, @Body WorkDay workDay);

    @PUT("employees/{email}/setWorkDayOut/{date}")
    Observable<WorkDay> setWorkDayOut(@Path("email") String email, @Path("date") String date,
                                   @Body WorkDay workDay);

    @PUT("employees/updateEmployee/{email}")
    Observable<Employee> updateEmployee(@Path("email") String email, @Body Employee employee);
}

package com.example.ilias.ntgemployeeiosystem.utils;

import com.example.ilias.ntgemployeeiosystem.data.source.remote.EmployeesRemoteDataSource;

/**
 * Created by ilias on 24/02/2018.
 */

public class Injection {

    public static EmployeesRemoteDataSource provideEmployeesRemoteDataSource(){
        return EmployeesRemoteDataSource.getInstance();
    }
}

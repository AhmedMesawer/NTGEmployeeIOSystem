package com.example.ilias.ntgemployeeiosystem.data.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ilias on 21/01/2018.
 */

public class EIOSAPI {


    static Retrofit getClient() {

        String baseURL = "http://mesawer.getsandbox.com/";

        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


}

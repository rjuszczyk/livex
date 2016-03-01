package com.mygdx.livex.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Radek on 2016-02-26.
 */
public class MyRetrofit {

    private static MyApi mMyApi;

    public static MyApi getApi () {
        if(mMyApi!=null) {
            return mMyApi;
        }

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pharmawayjn.nazwa.pl/MedycynaRodzinna/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mMyApi = retrofit.create(MyApi.class);
        return mMyApi;
    }


}

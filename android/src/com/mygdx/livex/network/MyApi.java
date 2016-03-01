package com.mygdx.livex.network;

import com.mygdx.livex.model.RowResponse;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Radek on 2016-02-26.
 */
public interface MyApi {
    @GET("gardimax/scores_json2.php")
    Call<RowResponse> rows();
}

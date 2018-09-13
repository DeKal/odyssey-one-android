package com.odyssey.one.app.common.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataAPIService {
    @GET("/api/login")
    Call<AccountVerification> verifiedAccount(@Query("username") String username, @Query("password") String password);
}

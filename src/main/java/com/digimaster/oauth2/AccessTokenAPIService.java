package com.digimaster.oauth2;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccessTokenAPIService {
    @FormUrlEncoded
    @POST("/realms/topbank-realm/protocol/openid-connect/token")
    Call<AccessTokenResponse> getAccessToken(@Field("grant_type") String grantType,
                                             @Field("client_id") String clientId,
                                             @Field("client_secret") String clientSecret);
}

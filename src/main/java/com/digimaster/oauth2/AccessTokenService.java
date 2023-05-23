package com.digimaster.oauth2;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AccessTokenService {
    public static final int CONNECT_TIMEOUT = 60;
    public static final int READ_TIMEOUT = 120;
    public static final int WRITE_TIMEOUT = 120;
    //    public static final String BASE_URL = "http://localhost:8105/";
    public static final String BASE_URL = "http://api.topbanking.xyz:9999/";

    private AccessTokenAPIService accessTokenAPIService;
    private String grantType;
    private String clientId;
    private String clientSecret;

    public AccessTokenService(String grantType, String clientId, String clientSecret) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        accessTokenAPIService = retrofit.create(AccessTokenAPIService.class);
    }

    public String getAccessToken() throws IOException {
//        String grantType = "client_credentials";
//        String clientId = "service-auth-client";
//        String clientSecret = "WuEjzGHgcu88M5w4eFV8NkTtVKiYHgnl";
        Call<AccessTokenResponse> accessToken = accessTokenAPIService.getAccessToken(grantType, clientId, clientSecret);
        Response<AccessTokenResponse> response = accessToken.execute();
        AccessTokenResponse accessTokenResponse = response.body();
        return accessTokenResponse.getAccessToken();
    }
}

package com.digimaster.oauth2;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
public class TokenAuthenticator implements Authenticator {
    private AccessTokenService accessTokenService;
    public TokenAuthenticator(AccessTokenService accessTokenService){
        this.accessTokenService = accessTokenService;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {
        System.out.println("Masuk authenticator");
        if(response.code() == 401){
            System.out.println("Masuk if 401");
            String accessToken = accessTokenService.getAccessToken();
            System.out.println("Access Token = "+accessToken);
            if(!accessToken.isEmpty()){
                return response.request().newBuilder()
                        .header("Authorization", "Bearer "+accessToken)
                    .build();
            }
        }else {
            System.out.println("response code other than 401");
        }
        return null;
    }
}

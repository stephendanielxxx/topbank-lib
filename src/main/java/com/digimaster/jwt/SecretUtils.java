package com.digimaster.jwt;

public class SecretUtils {
    public static final String JWT_SECRET = "2A462D4A614E635266556A586E3272357538782F413F4428472B4B6250655367566B5970337336763979244226452948404D635166546A576E5A713474377721";
    public static final String JWT_SECRET_REFRESH = "5b0c9c23ec47d43592b0b73f81c29aa873a7d754468b91d70df72ac4bb61303d20f76390f7a54679be3b094ade40fd8cf40fae05c48428be094eebd7953e8971";
//    public static final long tokenValidity = 30000;
    public static final long tokenValidity = 180000;
    public static final long refreshTokenValidity = 600000;
}

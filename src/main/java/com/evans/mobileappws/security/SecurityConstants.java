package com.evans.mobileappws.security;

import com.evans.mobileappws.SpringAppContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000; //10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/create-user";

    public static String getTokenSecret(){
        AppProperties appProperties = (AppProperties) SpringAppContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}

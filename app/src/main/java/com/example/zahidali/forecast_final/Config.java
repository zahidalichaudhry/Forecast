package com.example.zahidali.forecast_final;

/**
 * Created by Itpvt on 09-Jan-18.
 */

public class Config {

    ////////////USER LOGIN////////////
    public static final String URL_USER_LOGIN = "http://192.168.1.6/forecast/login.php";
    //If server response is equal to this that means login is successful
//    public static final String LOGIN_SUCCESS = "success";


    public static final String SHARED_PREF_NAME = "forecast";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "user_email";


    public static final String SHARED_PREF_FirstName = "FirstName";
    public static final String SHARED_PREF_LastName = "LastName";

    public static final String SHARED_PREF_UserID = "UserID";
    public static final String SHARED_PREF_EntityID = "entity_id";
    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";


    //Keys for email and password as defined in our $_POST['key'] in login.php

    ///////////USER SIGNUP URL/////
    public static final String URL_USER_SIGNUP="http://192.168.1.6/forecast/customer_signup_demo.php";
    public static final String SU_KEY_EMAIl="email";
    public static final String SU_KEY_FISTANME="firstname";
    public static final String SU_KEY_LASTNAME="lastname";
    public static final String SU_KEY_PASSWORD="password";


    public static final String URL_All_Categories= "http://192.168.1.6/forecast/getCategories.php";
    public static final String URL_Sub_Categories= "http://192.168.1.6/forecast/getSubCategories.php";
    public static final String URL_ALL_PRODUCTS= "http://192.168.1.6/forecast/getAllProductsByCategories.php";
    public static final String URL_PRODUCT_DETAILS= "http://192.168.1.6/forecast/get_product_details.php";
    public static final String URL_PRODUCT_DETAILS_CONFIGURE= "http://192.168.1.6/forecast/get_product_config_details.php";

    public static final String URL_BASE_WEBVIEW= "http://www.forecast.com.pk/index.php";

}

package com.example.zahidali.forecast_final;

/**
 * Created by Itpvt on 09-Jan-18.
 */

public class Config {
    public static final String ip="192.168.10.9";

    //////////USER LOGIN////////////
    public static final String URL_USER_LOGIN = "http://"+ip+"/forecast/login.php";
    //If server response is equal to this that means login is successful
//    public static final String LOGIN_SUCCESS = "success";
    public static final String SHARED_PREF_CART = "cart";
    public static final String SHARED_PREF_CART_NO = "cart_id";

///////////////Home PAGE Pics////////////////

    public static final String BANNER1 = "http://www.forecast.com.pk/media/wysiwyg/porto/homepage/slider/02/13.jpg";
    public static final String BANNER2 = "http://www.forecast.com.pk/media/wysiwyg/porto/homepage/slider/02/banner3.jpg";
    public static final String BANNER3 = "http://www.forecast.com.pk/media/wysiwyg/porto/homepage/slider/02/11.jpg";

    public static final String HOME_MEN = "http://"+Config.ip+"/ForecastMobile/mens.jpg";
    public static final String HOME_WOMEN = "http://"+Config.ip+"/ForecastMobile/womens.jpg";
    public static final String HOME_FOOTWARE = "http://"+Config.ip+"/ForecastMobile/footware.jpg";
    public static final String HOMW_SALE = "http://"+Config.ip+"/ForecastMobile/sale.jpg";
/////////////////////////////////END///////////////////////
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
    public static final String URL_USER_SIGNUP="http://"+ip+"/forecast/customer_signup_demo.php";
    public static final String SU_KEY_EMAIl="email";
    public static final String SU_KEY_FISTANME="firstname";
    public static final String SU_KEY_LASTNAME="lastname";
    public static final String SU_KEY_PASSWORD="password";

////////GETTING PRODUCT //////
    public static final String URL_All_Categories= "http://"+ip+"/forecast/getCategories.php";
    public static final String URL_Sub_Categories= "http://"+ip+"/forecast/getSubCategories.php";
    public static final String URL_ALL_PRODUCTS= "http://"+ip+"/forecast/getAllProductsByCategories.php";
    public static final String URL_PRODUCT_DETAILS= "http://"+ip+"/forecast/get_product_details.php";
    public static final String URL_PRODUCT_DETAILS_CONFIGURE= "http://"+ip+"/forecast/congig_details_prooo_demo.php";
    public static final String URL_BASE_WEBVIEW= "http://www.forecast.com.pk/index.php";
//////////SHOPPING CART APIS////////////////
    public static final String URL_ADD_TO_CART="http://"+ip+"/forecast/add_to_cart.php";
    public static final String URL_SHOW_CART="http://"+ip+"/forecast/getting_quote_items.php";
    public static final String URL_REMOVE_ITEM_CART="http://"+ip+"/forecast/remove_item_from_cart.php";
    ////////////ORDER PLACE//////////////////
    public static final String URL_CREATE_ORDER="http://"+ip+"/forecast/create_order.php";
    public static final String FIRSTNAME= "firstname";
    public static final String LASTNAME = "lastname";
    public static final String CART_ID = "cart_id";
    public static final String EMAIL = "email";
    public static final String COMPANY = "company";
    public static final String STREET = "street";
    public static final String CITY = "city";
    public static final String REGION = "region";
    public static final String POSTALCODE = "postcode";
    public static final String COUNTRY = "country_id";
    public static final String PHONE = "telephone";
}

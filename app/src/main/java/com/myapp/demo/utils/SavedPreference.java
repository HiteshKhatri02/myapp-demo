package com.myapp.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Ranosys Technologies
 */

public class SavedPreference {
    private static final int MODE_PRIVATE=0;
    private final SharedPreferences preferences;

    public SavedPreference(Context context){
        String user_pref = "MyApp";
        preferences=context.getSharedPreferences(user_pref,MODE_PRIVATE);
    }

    //Method to store user email address
    public void storeUserEmail(String userId){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", userId);
        editor.apply();
    }
    //Method to get user email address
    public String getUserEmail(){
        return preferences.getString("email","");
    }
    //method to store user id
    public void storeUserID(String userId){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id", userId);
        editor.apply();
    }
    //Method to get user id.
    public String getUserId(){
        return preferences.getString("user_id","");
    }


    //method to store user id
    public void storeMD5Password(String md5){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", md5);
        editor.apply();
    }
    //Method to get user id.
    String getMD5Password(){
        return preferences.getString("password","");
    }

    //method to store device token
    public void storeDeviceToken(String userId){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id", userId);
        editor.apply();
    }
    //method to get device token
    private String getDeviceToken(){
        return preferences.getString("device_token","");
    }

    //method to clear shared preference
    public void getClear(){
        String deviceToken=getDeviceToken();
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().apply();
        storeDeviceToken(deviceToken);
    }

}

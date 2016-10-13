package com.myapp.demo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.myapp.demo.utils.SavedPreference;


/**
 * Splash Activity
 *
 */

public class SplashActivity extends BaseActivity{
    //Hold the screen for 1 second
   // private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * Method to redirect user on login activity or edit profile activity
     */
    private void init(){
        //Check user id is exists or not if exists then skip login screen
        SavedPreference savedPreference=new SavedPreference(SplashActivity.this);
        if (TextUtils.isEmpty(savedPreference.getUserId())){
            //Open Login Activity
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            finish();
        }else {
            //Open Login Activity
            startActivity(new Intent(SplashActivity.this,EditProfileActivity.class));
            finish();
        }
    }

}

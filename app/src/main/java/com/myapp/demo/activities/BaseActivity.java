package com.myapp.demo.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.myapp.demo.utils.MyAppProgressDialog;

/**
 * Base activity of the app
 */

public class BaseActivity extends AppCompatActivity {

    //Variable to initialise the progress dialog
    private ProgressDialog mPDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialise Progress dialog
        mPDialog = MyAppProgressDialog.ctor(this);
    }

    public void showProgressDialog() {
        if (null != mPDialog) {
            mPDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (null != mPDialog) {
            mPDialog.dismiss();
        }
    }



}

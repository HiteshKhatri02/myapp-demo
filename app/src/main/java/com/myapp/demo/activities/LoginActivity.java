package com.myapp.demo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.myapp.demo.R;
import com.myapp.demo.callbacks.SuccessCallback;
import com.myapp.demo.utils.MyAppCustomDialog;
import com.myapp.demo.utils.Utils;
import com.myapp.demo.utils.ViewValidator;


/**
 * Login Activity needs password, email
 * and Access code to perform login.
 */

public class LoginActivity extends FireBaseAuthActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       //Initialise login button click listener
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                //hide keyboard
                Utils.hideKeyBoard(LoginActivity.this);
                if(new ViewValidator(this ,getWindow().getDecorView().getRootView()).checkValidation((ViewGroup)v.getRootView().findViewById(R.id.parent_layout_login))) {
                    if (Utils.isOnline()) {
                        //call get community web service
                        signIn(((EditText) findViewById(R.id.et_user_name)).getText().toString(), ((EditText) findViewById(R.id.et_password)).getText().toString(), new SuccessCallback() {
                            @Override
                            public void onClick() {
                                startActivity(new Intent(LoginActivity.this, EditProfileActivity.class));
                                finish();
                            }
                        });
                    }else {
                        DialogFragment dialogFragment = MyAppCustomDialog.newInstance(getResources().getString(R.string.text_please_check_internet));
                        dialogFragment.show(getSupportFragmentManager(), "InternetError");
                    }
                    }
                break;
        }
    }
}



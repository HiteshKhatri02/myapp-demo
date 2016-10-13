package com.myapp.demo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.myapp.demo.R;


/**
 * A class for checking validations of views
 *
 */
public class ViewValidator {

    private final Context mContext;
    private final View mView;
    private boolean isValid = true;

    public ViewValidator(Context context, View view) {
        mContext = context;
        mView = view;
    }

    public boolean checkValidation(ViewGroup rootLayout) {
        // isValid = true;
        int childCount = rootLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {

            if (rootLayout.getChildAt(i) instanceof ViewGroup) {
                isValid = checkValidation((ViewGroup) rootLayout.getChildAt(i));
            } else if (rootLayout.getChildAt(i) != null) {
                View view = rootLayout.getChildAt(i);
                if (view instanceof EditText) {
                    EditText inputFieldLayout = (EditText) view;
                    switch (inputFieldLayout.getId()) {
                        case R.id.et_user_name:
                            isValidEmail(inputFieldLayout);
                            break;
                        case R.id.et_password:
                            checkBlankFieldValidation(inputFieldLayout);
                            break;
                        case R.id.tv_blood_group:
                            checkBlankFieldValidation(inputFieldLayout);
                            break;
                        case R.id.tv_day_of_birth:
                            checkBlankFieldValidation(inputFieldLayout);
                            break;
                        case R.id.tv_month_of_birth:
                            checkBlankFieldValidation(inputFieldLayout);
                            break;
                        case R.id.tv_year_of_birth:
                            checkBlankFieldValidation(inputFieldLayout);
                            break;
                        case R.id.et_contact_number:
                            mobileNoValidation(inputFieldLayout);
                            break;
                        case R.id.et_enter_password:
                            checkPasswordValidations(inputFieldLayout);
                            break;
                        case R.id.et_re_enter_password:
                            checkBlankFieldValidation(inputFieldLayout);
                            break;
                    }
                }
            }
        }
        return isValid;
    }

    //method to check validation for empty fields
    private void checkBlankFieldValidation(EditText editText){
        if (TextUtils.isEmpty(editText.getText().toString())) {
            Utils.showSnackBar(mView, mContext.getResources().getString(R.string.validation_all_field_required));
            isValid = false;
        }
    }

    //Method to check validation for password field
    private void checkPasswordValidations(EditText editText){
        if (TextUtils.isEmpty(editText.getText().toString())) {
            Utils.showSnackBar(mView, mContext.getResources().getString(R.string.validation_all_field_required));
            isValid = false;
        }else if (editText.getText().toString().length()<6){
            Utils.showSnackBar(mView, mContext.getResources().getString(R.string.validation_min_password_length));
            isValid = false;
        }else if (!Utils.md5Encryption(editText.getText().toString()).equals(new SavedPreference(mContext).getMD5Password())){
            Utils.showSnackBar(mView, mContext.getResources().getString(R.string.validation_password_do_not_match));
            isValid = false;
        }
    }

    //Method to check validation on email field
    private void isValidEmail(EditText editText){
        if (TextUtils.isEmpty(editText.getText().toString())) {
            Utils.showSnackBar(mView, mContext.getResources().getString(R.string.validation_all_field_required));
            isValid = false;
        }else{
            //Is valid email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()){
                Utils.showSnackBar(mView, mContext.getResources().getString(R.string.validation_email_not_correct));
                isValid=false;
            }
        }
    }

    //Mobile number validation
    private void mobileNoValidation(EditText editText){

        if (TextUtils.isEmpty(editText.getText().toString())) {
            Utils.showSnackBar(mView, mContext.getResources().getString(R.string.validation_all_field_required));
            isValid = false;

        }else if (editText.getText().toString().length()<10){

            Utils.showSnackBar(mView, mContext.getResources().getString(R.string.validation_contact_no_length));
            isValid = false;
        }
    }
}

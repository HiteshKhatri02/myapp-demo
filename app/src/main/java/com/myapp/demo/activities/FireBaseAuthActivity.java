package com.myapp.demo.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.myapp.demo.callbacks.SuccessCallback;
import com.myapp.demo.utils.MyAppCustomDialog;
import com.myapp.demo.utils.SavedPreference;
import com.myapp.demo.utils.Utils;

/**
 * This class act as a base class for login and edit profile activity
 * contains sign and sign out methods of firebase.
 *
 */
public class FireBaseAuthActivity extends BaseActivity {
    private final String TAG = "FireBaseAuthActivity";
    //Instance for firebase authorisation
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Method to sign with email and password from firebase server
     *
     * @param email: email of the which has been created on firebase console
     * @param password password of the which has been created on firebase console
     */

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    public void signIn(String email, final String password, final SuccessCallback  callback) {
        showProgressDialog();
        Log.d(TAG, "signIn:" + email);
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            if (null != user) {
                                // User is signed in
                                SavedPreference savedPreference = new SavedPreference(FireBaseAuthActivity.this);
                                savedPreference.storeUserID(user.getUid());
                                savedPreference.storeUserEmail(user.getEmail());
                                savedPreference.storeMD5Password(Utils.md5Encryption(password));
                                callback.onClick();
                            }
                        }else {
                            //Show alert if user is not successfully signed in
                            if (null!=task.getException()) {
                                DialogFragment dialogFragment = MyAppCustomDialog.newInstance(task.getException().getMessage());
                                dialogFragment.show(getSupportFragmentManager(), TAG);
                            }
                        }
                    }
                });
    }

    /**
     * Method to perform sign out from firebase
     */
    public void signOut() {
        mAuth.signOut();
        new SavedPreference(this).getClear();
    }
}

package com.myapp.demo.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.myapp.demo.R;
import com.myapp.demo.callbacks.SuccessCallback;


/**
 * A custom Dialog fragment class for alert dialog;
 */

public class MyAppCustomDialog extends DialogFragment {

    private static SuccessCallback successCallback;

    public static MyAppCustomDialog newInstance(String message, SuccessCallback callback) {
        successCallback=callback;
        MyAppCustomDialog frag = new MyAppCustomDialog();
        Bundle args = new Bundle();
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    public static MyAppCustomDialog newInstance(String message) {
        MyAppCustomDialog frag = new MyAppCustomDialog();
        Bundle args = new Bundle();
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message=getArguments().getString("message");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(),  R.style.AppCompatAlertDialogStyle);
        builder.setMessage(message);
        builder.setCancelable(false);
        if (null!=successCallback){

            setSuccessCallback(builder);

        }else {

            noCallback(builder);
        }
        return builder.create();
    }

    private void setSuccessCallback (AlertDialog.Builder builder){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                successCallback.onClick();
            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    private void noCallback(AlertDialog.Builder builder){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
    }
}

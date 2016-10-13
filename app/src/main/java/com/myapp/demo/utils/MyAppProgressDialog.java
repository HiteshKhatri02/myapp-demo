package com.myapp.demo.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;

import com.myapp.demo.R;


/**
 * A circular progress dialog with porterDuff mode SRC_IN Indeterminate drawable.
 */
public class MyAppProgressDialog extends ProgressDialog {

    private MyAppProgressDialog(Context context) {
        super(context);
    }
    public static ProgressDialog ctor(Context context) {
        MyAppProgressDialog dialog = new MyAppProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        if (null!=dialog.getWindow()) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        return dialog;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar_layout);
        ProgressBar progress1 = (ProgressBar) findViewById(R.id.progress_bar);
        if (null!=progress1) {
            progress1.setBackgroundColor(ContextCompat.getColor(getContext(),android.R.color.transparent));
            progress1.setBackground(null);
            progress1.setIndeterminate(isIndeterminate());
            progress1.getIndeterminateDrawable().setColorFilter(0xFF91BB3F, PorterDuff.Mode.SRC_IN);

        }
    }
}

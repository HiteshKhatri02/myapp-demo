package com.myapp.demo.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * A common edit text class for the app
 */
public class MyAppCheckBox extends CheckBox {
    public MyAppCheckBox(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Lt.ttf");
        setTypeface(tf);
    }

    public MyAppCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Lt.ttf");
        setTypeface(tf);
    }

    public MyAppCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Lt.ttf");
        setTypeface(tf);
    }
}

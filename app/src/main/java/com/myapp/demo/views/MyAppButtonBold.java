package com.myapp.demo.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * A common button class for the app
 */

public class MyAppButtonBold extends Button {
    public MyAppButtonBold(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Bd.ttf");
        setTypeface(tf);
    }

    public MyAppButtonBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Bd.ttf");
        setTypeface(tf);
    }

    public MyAppButtonBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Bd.ttf");
        setTypeface(tf);
    }
}
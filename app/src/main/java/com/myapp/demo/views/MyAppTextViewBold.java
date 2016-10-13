package com.myapp.demo.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A common text view class for the app
 *
 */
public class MyAppTextViewBold extends TextView {
    public MyAppTextViewBold(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Bd.ttf");
        setTypeface(tf);
    }

    public MyAppTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Bd.ttf");
        setTypeface(tf);
    }

    public MyAppTextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Bd.ttf");
        setTypeface(tf);
    }
}

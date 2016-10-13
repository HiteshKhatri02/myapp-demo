package com.myapp.demo.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A common text view class for the app
 */
public class MyAppTextView extends TextView {

    public MyAppTextView(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Lt.ttf");
        setTypeface(tf);
    }

    public MyAppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Lt.ttf");
        setTypeface(tf);
    }

    public MyAppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Lt.ttf");
        setTypeface(tf);
    }
}

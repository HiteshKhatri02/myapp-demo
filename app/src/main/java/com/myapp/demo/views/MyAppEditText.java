package com.myapp.demo.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;


import com.myapp.demo.R;

import java.lang.reflect.Field;

/**
 * common class for edit text
 */
public class MyAppEditText extends EditText {
    public MyAppEditText(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Lt.ttf");
        setTypeface(tf);
        setCursorDrawableColor(MyAppEditText.this, ContextCompat.getColor(context, R.color.colorLightGreen));
    }

    public MyAppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Lt.ttf");
        setTypeface(tf);
        setCursorDrawableColor(MyAppEditText.this, ContextCompat.getColor(context, R.color.colorLightGreen));

    }

    public MyAppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTCom-Lt.ttf");
        setTypeface(tf);
        setCursorDrawableColor(MyAppEditText.this, ContextCompat.getColor(context, R.color.colorLightGreen));
    }

    public static void setCursorDrawableColor(MyAppEditText editText, int color) {
        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);
            Drawable[] drawables = new Drawable[2];
            drawables[0] = ContextCompat.getDrawable(editText.getContext(), mCursorDrawableRes);
            drawables[1] = ContextCompat.getDrawable(editText.getContext(), mCursorDrawableRes);
            drawables[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            drawables[1].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            fCursorDrawable.set(editor, drawables);
        } catch (Throwable ignored) {
        }
    }
}

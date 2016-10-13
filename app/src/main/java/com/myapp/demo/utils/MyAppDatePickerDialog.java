package com.myapp.demo.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.myapp.demo.R;
import com.myapp.demo.callbacks.MyAppDateSet;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Custom App picker dialog shows individual day, month and year dialog
 */

public class MyAppDatePickerDialog extends DialogFragment {

    private static MyAppDateSet myAppDateSet;
    private int buttonClicked;

    public static MyAppDatePickerDialog newInstance(Bundle bundle, MyAppDateSet callBack) {
        myAppDateSet=callBack;
        MyAppDatePickerDialog frag = new MyAppDatePickerDialog();
        frag.setArguments(bundle);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String field = getArguments().getString("field");
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Use the current date as the default date in the picker
        Calendar c = Calendar.getInstance();
        View dialog = inflater.inflate(R.layout.date_picker_dialog, null);
        //Initialise picker dialog
        final NumberPicker dayPicker = (NumberPicker) dialog.findViewById(R.id.picker_day);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);
        //Conditions to check date picker called for
        if ("day".equals(field)){
            buttonClicked=R.id.picker_day;
            c.set((Calendar.YEAR),getArguments().getInt("year"));
            c.set(Calendar.MONTH,getArguments().getInt("month")-1);
            dayPicker.setMinValue(1);
            dayPicker.setMaxValue(c.getActualMaximum(Calendar.DAY_OF_MONTH));
            dayPicker.setFormatter(new NumberPicker.Formatter() {
                @SuppressLint("DefaultLocale")
                @Override
                public String format(int i) {
                    return String.format("%02d", i);
                }
            });
            dayPicker.setValue(c.getActualMaximum(Calendar.DAY_OF_MONTH));
            monthPicker.setVisibility(View.GONE);
            yearPicker.setVisibility(View.GONE);
        }else if ("month".equals(field)){
            buttonClicked=R.id.picker_month;
            monthPicker.setMinValue(1);
            monthPicker.setMaxValue(12);
            monthPicker.setValue(c.getActualMaximum(Calendar.DAY_OF_MONTH));
            monthPicker.setFormatter(new NumberPicker.Formatter() {
                @SuppressLint("DefaultLocale")
                @Override
                public String format(int i) {
                    return String.format("%02d", i);
                }
            });
            dayPicker.setVisibility(View.GONE);
            yearPicker.setVisibility(View.GONE);
        }else {
            buttonClicked=R.id.picker_year;
            int year = c.get(Calendar.YEAR);
            yearPicker.setMinValue(year-85);
            yearPicker.setMaxValue(year-18);
            yearPicker.setValue(year);
            dayPicker.setVisibility(View.GONE);
            monthPicker.setVisibility(View.GONE);
        }
        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        MyAppDatePickerDialog.this.getDialog().cancel();
                        switch (buttonClicked){
                            case R.id.picker_day:
                                if (null!=myAppDateSet){
                                    DecimalFormat formatter = new DecimalFormat("00");
                                    String day = formatter.format(dayPicker.getValue());
                                    myAppDateSet.onSet(day,null,null);
                                }
                                break;
                            case R.id.picker_month:
                                if (null!=myAppDateSet){
                                    DecimalFormat formatter = new DecimalFormat("00");
                                    String month = formatter.format(monthPicker.getValue());
                                    myAppDateSet.onSet(null,month,null);
                                }
                                break;
                            case R.id.picker_year:
                                if (null!=myAppDateSet){
                                    myAppDateSet.onSet(null,null,String.valueOf(yearPicker.getValue()));
                                }
                                break;
                        }
                    }
                });

        return builder.create();
    }
}

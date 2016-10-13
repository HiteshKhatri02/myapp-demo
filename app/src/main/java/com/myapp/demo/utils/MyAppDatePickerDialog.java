package com.myapp.demo.utils;

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

import java.util.Calendar;

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
            dayPicker.setValue(c.getActualMaximum(Calendar.DAY_OF_MONTH));
            monthPicker.setVisibility(View.GONE);
            yearPicker.setVisibility(View.GONE);
        }else if ("month".equals(field)){
            buttonClicked=R.id.picker_month;
            monthPicker.setMinValue(1);
            monthPicker.setMaxValue(12);
            monthPicker.setValue(c.getActualMaximum(Calendar.DAY_OF_MONTH));
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
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        MyAppDatePickerDialog.this.getDialog().cancel();
                        switch (buttonClicked){
                            case R.id.picker_day:
                                if (null!=myAppDateSet){
                                    myAppDateSet.onSet(dayPicker.getValue(),0,0);
                                }
                                break;
                            case R.id.picker_month:
                                if (null!=myAppDateSet){
                                    myAppDateSet.onSet(0,monthPicker.getValue(),0);
                                }
                                break;
                            case R.id.picker_year:
                                if (null!=myAppDateSet){
                                    myAppDateSet.onSet(0,0,yearPicker.getValue());
                                }
                                break;
                        }
                    }
                });

        return builder.create();
    }
}

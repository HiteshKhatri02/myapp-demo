package com.myapp.demo.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.myapp.demo.R;
import com.myapp.demo.callbacks.PopupSuccessCallBack;
import com.myapp.demo.views.MyAppRadioButton;

import java.util.ArrayList;

/**
 * Custom  popup dialog which has radio button with it's views
 */

public class MyAppPopupDialog extends DialogFragment {

    private static PopupSuccessCallBack popupSuccessCallBack;
    private String selectedItemText;


    public static MyAppPopupDialog newInstance(ArrayList<String> choiceArray, PopupSuccessCallBack callback) {
        popupSuccessCallBack =callback;
        MyAppPopupDialog frag = new MyAppPopupDialog();
        Bundle args = new Bundle();
        args.putString("title", "Blood Group");
        args.putStringArrayList("stringArray",choiceArray);
        frag.setArguments(args);
        return frag;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        ArrayList<String> stringArray = getArguments().getStringArrayList("stringArray");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        View view = getActivity().getLayoutInflater().inflate(R.layout.single_choice_dialog, null);
        ((TextView) view.findViewById(R.id.tv_dialog_title)).setText(title);
        RadioGroup choiceRadioGroup = (RadioGroup) view.findViewById(R.id.radio_btn_choice_group);

        if (null != stringArray) {
            for (String installationOption : stringArray) {

                Log.d("Installation method", "Otions in the list" + installationOption);
                MyAppRadioButton radioButton =
                        new MyAppRadioButton(getActivity());
                radioButton.setTextSize(16);
                radioButton.setText(installationOption);
                choiceRadioGroup.addView(radioButton);
            }
            builder.setView(view);
            if (null != popupSuccessCallBack) {
                onClick(view,choiceRadioGroup);
            }
        }
            return builder.create();
        }

    private void onClick(final View view, final RadioGroup radioGroup){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5ms = 500ms
                        RadioButton button = (RadioButton) view.findViewById(radioGroup.getCheckedRadioButtonId());
                        setSelectedItemText(button.getText().toString());
                        popupSuccessCallBack.onClick(MyAppPopupDialog.this);
                    }
                }, 250);
            }
        });
    }

    public String getSelectedItemText() {
        return selectedItemText;
    }

    public void setSelectedItemText(String selectedItemText) {
        this.selectedItemText = selectedItemText;
    }
}
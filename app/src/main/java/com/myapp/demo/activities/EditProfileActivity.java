package com.myapp.demo.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.myapp.demo.R;
import com.myapp.demo.callbacks.MyAppDateSet;
import com.myapp.demo.callbacks.PopupSuccessCallBack;
import com.myapp.demo.callbacks.SuccessCallback;
import com.myapp.demo.database.DBHelper;
import com.myapp.demo.model.EditProfileModel;
import com.myapp.demo.utils.AppConstants;
import com.myapp.demo.utils.MyAppCustomDialog;
import com.myapp.demo.utils.MyAppDatePickerDialog;
import com.myapp.demo.utils.MyAppPopupDialog;
import com.myapp.demo.utils.SavedPreference;
import com.myapp.demo.utils.Utils;
import com.myapp.demo.utils.ViewValidator;
import com.myapp.demo.views.MyAppEditText;
import com.myapp.demo.views.MyAppTextView;
import com.myapp.demo.views.MyAppTextViewBold;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.myapp.demo.utils.AppConstants.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS;

/**
 * Activity which contains views for editing profile, also fetch data
 * from local db and write and update data in local db.
 */
public class EditProfileActivity extends FireBaseAuthActivity implements View.OnClickListener {
    //Instance which is stores file path of file
    private Uri imageCaptureUri;
    //Instance database helper class
    private DBHelper dbHelper;

    private EditProfileModel editProfileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //Init edit profile model
        editProfileModel=new EditProfileModel();
        //Create data base
        createDataBase();
        //read value from database
        if (null!=dbHelper.searchRecord()){
            Bundle bundle=dbHelper.searchRecord();
            setValueToViewFromDB(bundle);
        }else {
            String email=new SavedPreference(this).getUserEmail();
            ((MyAppTextView)findViewById(R.id.tv_user_email)).setText(email);
        }
        //Initialise click listeners of the views
        findViewById(R.id.image_open_camera).setOnClickListener(this);
        findViewById(R.id.btn_logout).setOnClickListener(this);
        findViewById(R.id.tv_blood_group).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.tv_day_of_birth).setOnClickListener(this);
        findViewById(R.id.tv_month_of_birth).setOnClickListener(this);
        findViewById(R.id.tv_year_of_birth).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_open_camera:
                //Perform click for user profile
                if (Utils.isMNC()) {
                    requestForPermissions();
                } else {
                    imagePickerDialog();
                }
                break;
            case R.id.btn_logout:
                //perform click of logout
                signOut();
                startActivity(new Intent(EditProfileActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.tv_blood_group:
                //Show popup for blood group
                bloodTypeList();
                break;
            case R.id.tv_day_of_birth:
                //Show picker of day
                Bundle  bundle=new Bundle();
                bundle.putString("field","day");
                bundle.putInt("viewId",R.id.tv_day_of_birth);
                bundle.putInt("year",Integer.parseInt(((MyAppTextView)findViewById(R.id.tv_year_of_birth)).getText().toString().trim()));
                bundle.putInt("month",Integer.parseInt(((MyAppTextView)findViewById(R.id.tv_month_of_birth)).getText().toString().trim()));
                showDatePickerDialog(bundle);
                break;
            case R.id.tv_month_of_birth:
                //Show picker of month
                Bundle  bundleMonth=new Bundle();
                bundleMonth.putString("field","month");
                bundleMonth.putInt("viewId",R.id.tv_month_of_birth);
                bundleMonth.putInt("year",Integer.parseInt(((MyAppTextView)findViewById(R.id.tv_year_of_birth)).getText().toString().trim()));
                showDatePickerDialog(bundleMonth);
                break;
            case R.id.tv_year_of_birth:
                //Show picker of year
                Bundle  bundleYear=new Bundle();
                bundleYear.putString("field","year");
                bundleYear.putInt("viewId",R.id.tv_year_of_birth);
                showDatePickerDialog(bundleYear);
                break;
            case R.id.btn_save:
                //event for save database value
                if(new ViewValidator(this ,v.getRootView()).checkValidation((ViewGroup)v.getRootView().getRootView().findViewById(R.id.parent_edit_profile_layout))) {

                    if (null != imageCaptureUri) {

                        String etPassword = ((MyAppEditText) findViewById(R.id.et_enter_password)).getText().toString();
                        String etRePassword = ((MyAppEditText) findViewById(R.id.et_re_enter_password)).getText().toString();

                        if (etPassword.equals(etRePassword)) {
                            editProfileModel.setBloodType(((MyAppTextViewBold) findViewById(R.id.tv_blood_group)).getText().toString());
                            String dateOfBirth = ((MyAppTextView) findViewById(R.id.tv_day_of_birth)).getText().toString() + "-"
                                    + ((MyAppTextView) findViewById(R.id.tv_month_of_birth)).getText().toString() + "-"
                                    + ((MyAppTextView) findViewById(R.id.tv_year_of_birth)).getText().toString();
                            editProfileModel.setDateOfBirth(dateOfBirth);
                            editProfileModel.setContactNumber(((MyAppEditText) findViewById(R.id.et_contact_number)).getText().toString());
                            dbHelper.insertItem(editProfileModel);
                        } else {
                            Utils.showSnackBar(EditProfileActivity.this.findViewById(android.R.id.content), getResources().getString(R.string.validation_for_retyped_password));
                        }
                    } else {
                        Utils.showSnackBar(EditProfileActivity.this.findViewById(android.R.id.content), getResources().getString(R.string.validation_profile_picture));
                    }
                }
                break;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("picUri", imageCaptureUri);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        imageCaptureUri= savedInstanceState.getParcelable("picUri");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.PICK_FROM_CAMERA:
                    try {
                        String path = Utils.getImageFilePath(imageCaptureUri.toString(), EditProfileActivity.this);
                        editProfileModel.setFilePath(path);
                        showImage(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case AppConstants.PICK_FROM_GALLERY:
                    try {
                        Uri imageUri = data.getData();
                        String path = Utils.getImageFilePath(imageUri.toString().trim(),
                                this);
                        editProfileModel.setFilePath(path);
                        //entered value of path
                        imageCaptureUri=Uri.parse(path);
                        showImage(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void requestForPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();
        final List<String> permissionsList = new ArrayList<>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            //camera read permission
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            //read permission
            permissionsNeeded.add("Read Storage");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            //wright storage permission
            permissionsNeeded.add("Write Storage");
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            //phone state permission
            permissionsNeeded.add("Read phone state");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                DialogFragment dialogFragment=  MyAppCustomDialog.newInstance(message, new SuccessCallback() {
                    @Override
                    public void onClick() {
                        ActivityCompat.requestPermissions(EditProfileActivity.this,permissionsList.toArray(new String[permissionsList.size()]),
                                REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                    }
                });
                dialogFragment.show(getSupportFragmentManager(),"EditProfile");
                return;
            }
            ActivityCompat.requestPermissions(EditProfileActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        } else {
            doTakePhotoAction();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList,
                                  String permission) {
        if (this.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this,permission))
                return false;
        }
        return true;
    }

    /**
     * show dialog user to select image via gallery or camera.
     */
    private void imagePickerDialog() {
        final Dialog dialog = new Dialog(this, R.style.DialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_picker_dialog);
        Window window = dialog.getWindow();

        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        // A button for take photo functionality
        dialog.findViewById(R.id.buttonTakePicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                doTakePhotoAction();
            }
        });

        // A button for select image functionality
        dialog.findViewById(R.id.buttonChooseImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, AppConstants.PICK_FROM_GALLERY);
            }
        });

        // A button to cancel the dialog
        dialog.findViewById(R.id.cancel_image_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    /**
     * Method to create database if db doesn't exists
     */
    private void createDataBase(){
        dbHelper=new DBHelper(EditProfileActivity.this);
    }

    //Initialise popup for Set installation type options
    private void bloodTypeList() {
        ArrayList<String> choiceArray = new ArrayList<>();
        String[] array=new String[]{"A +", "A -", "B +", "B -", "AB +", "AB -", "O +", "O -"};
        Collections.addAll(choiceArray, array);
        final String[] selectedItemText = new String[1];
        DialogFragment dialogFragment = MyAppPopupDialog.newInstance(choiceArray, new PopupSuccessCallBack() {

            @Override
            public void onClick(MyAppPopupDialog dialogFragment) {
                selectedItemText[0] = dialogFragment.getSelectedItemText();
                dialogFragment.dismiss();
                ((MyAppTextViewBold) findViewById(R.id.tv_blood_group)).setText(selectedItemText[0]);
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "TAG");
    }

    /**
     * Method to show date picker dialog
     * @param bundle- contains parameters for date picker dialog
     */
    public void showDatePickerDialog( final Bundle bundle) {
        DialogFragment newFragment = MyAppDatePickerDialog.newInstance(bundle, new MyAppDateSet() {
            @Override
            public void onSet(String day, String month, String year) {
                switch (bundle.getInt("viewId")){
                    case R.id.tv_year_of_birth:
                        ((MyAppTextView)findViewById(R.id.tv_year_of_birth)).setText(year);
                        findViewById(R.id.tv_month_of_birth).setAlpha(1f);
                        findViewById(R.id.tv_month_of_birth).setEnabled(true);
                        break;
                    case R.id.tv_month_of_birth:
                        ((MyAppTextView)findViewById(R.id.tv_month_of_birth)).setText(month);
                        findViewById(R.id.tv_day_of_birth).setAlpha(1f);
                        findViewById(R.id.tv_day_of_birth).setEnabled(true);
                        break;
                    case R.id.tv_day_of_birth:
                        ((MyAppTextView)findViewById(R.id.tv_day_of_birth)).setText(day);
                        break;
                }
            }
        });
        //show dialog picker
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     *Method to show values on views
     * @param bundle: contains all required values from db to set on views
     */
    private void setValueToViewFromDB(Bundle bundle){
        //Insert vales in model
        editProfileModel.setDateOfBirth(bundle.getString("date_of_birth"));
        editProfileModel.setFilePath(bundle.getString("file_path"));
        editProfileModel.setBloodType(bundle.getString("blood_type"));
        editProfileModel.setContactNumber(bundle.getString("contact_number"));


        String[] separator = new String[3];
        if (null!=editProfileModel.getDateOfBirth()) {
            separator = editProfileModel.getDateOfBirth().split("-");
        }

        //Set values on views
        ((MyAppTextView)findViewById(R.id.tv_user_email)).setText(new SavedPreference(this).getUserEmail());
        ((MyAppEditText)findViewById(R.id.et_contact_number)).setText(editProfileModel.getContactNumber());
        ((MyAppTextViewBold)findViewById(R.id.tv_blood_group)).setText(editProfileModel.getBloodType());
        ((MyAppTextView)findViewById(R.id.tv_day_of_birth)).setText(separator[0]);
        ((MyAppTextView)findViewById(R.id.tv_month_of_birth)).setText(separator[1]);
        ((MyAppTextView)findViewById(R.id.tv_year_of_birth)).setText(separator[2]);
        //Show image
        showImage(editProfileModel.getFilePath());
        //reset alpha
        findViewById(R.id.tv_day_of_birth).setAlpha(1f);
        findViewById(R.id.tv_month_of_birth).setAlpha(1f);
        findViewById(R.id.tv_year_of_birth).setAlpha(1f);
    }

    /**
     * A method to take photo
     */
    private void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //check sd card is available or not for save image from camera
        if (Utils.isExternalStorageWritable()) {
            imageCaptureUri = Uri.fromFile(Utils.getImagePath());
            try {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
                intent.putExtra("return-data", false);
                startActivityForResult(intent, AppConstants.PICK_FROM_CAMERA);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("Space is not available", "SD CARD Not Available");

        }
    }

    /**
     * Method to Show image from provided path
     *
     * @param path; image path storage directory
     */
    private void showImage(String path) {
        DisplayImageOptions options;
        ImageLoader imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
        String url = "file://" + path.trim();
        final ImageView userProfilePic = (ImageView) findViewById(R.id.image_user_profile_pic);
        imageLoader.loadImage(url, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
            }
            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }
            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                userProfilePic.setImageBitmap(Utils.getCircularBitmap(bitmap));
            }
            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });
    }

}

package com.myapp.demo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.myapp.demo.R;
import com.myapp.demo.callbacks.SuccessCallback;
import com.myapp.demo.model.EditProfileModel;
import com.myapp.demo.utils.MyAppCustomDialog;
import com.myapp.demo.utils.SavedPreference;

/**
 * Class contains method to create database
 * insert value in database
 * update value in database
 * read value from database
 */

public class DBHelper extends SQLiteOpenHelper {

    private final Context mContext;

    public DBHelper(Context context) {
        super(context, DatabaseStructure.DATABASE_NAME, null, DatabaseStructure.DATABASE_VERSION);
        this.mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(DatabaseQueries.SQL_CREATE_PROFILE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Method o insert values in database
     * @param editProfileModel; model contains all required parameter which
     *                        has to be written in database
     */
    public void insertItem(EditProfileModel editProfileModel) {
        // Gets the data repository in write mode
        final SQLiteDatabase db =  this.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put(DatabaseStructure.Profile.USER_ID, new SavedPreference(mContext).getUserId());
        values.put(DatabaseStructure.Profile.BLOOD_TYPE, editProfileModel.getBloodType());
        values.put(DatabaseStructure.Profile.DATE_OF_BIRTH, editProfileModel.getDateOfBirth());
        values.put(DatabaseStructure.Profile.EMAIL_ADDRESS, new SavedPreference(mContext).getUserEmail());
        values.put(DatabaseStructure.Profile.CONTACT_NUMBER, editProfileModel.getContactNumber());
        values.put(DatabaseStructure.Profile.USER_IMAGE_PATH, editProfileModel.getFilePath());
        String query = DatabaseQueries.SQL_SEARCH_QUERY;
        final Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        //if count is less than zero i.e. has to insert value rather update value.
        if (count<=0) {
            //Show an alert to user to insert value
            DialogFragment dialogFragment = MyAppCustomDialog.newInstance(mContext.getResources().getString(R.string.text_enter_value_in_databse), new SuccessCallback() {
                @Override
                public void onClick() {
                    // Insert the new row in the table
                    db.insert(DatabaseStructure.Profile.TABLE_NAME, null, values);
                    cursor.close();
                    db.close();
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.text_data_has_been_saved), Toast.LENGTH_SHORT).show();
                }
            });
            dialogFragment.show(((AppCompatActivity)mContext).getSupportFragmentManager(), "DatabaseHelper");

        }else {
            //Show an alert to user to update value in db
            DialogFragment dialogFragment = MyAppCustomDialog.newInstance(mContext.getResources().getString(R.string.text_update_value_in_database), new SuccessCallback() {
                @Override
                public void onClick() {
                    // update value on the row
                    db.update(DatabaseStructure.Profile.TABLE_NAME, values,null, null);
                    cursor.close();
                    db.close();
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.text_data_has_been_saved), Toast.LENGTH_SHORT).show();
                }
            });
            dialogFragment.show(((AppCompatActivity)mContext).getSupportFragmentManager(), "DatabaseHelper");
        }

    }

    /**
     * Method to read value from database
     *
     * @return : return a bundle with all values which has written database
     */
    public Bundle searchRecord() {
        SQLiteDatabase sql = this.getReadableDatabase();
        Bundle cursorData = new Bundle();
        String query = DatabaseQueries.SQL_READ_PROFILE_ENTRIES;
        Cursor c = sql.rawQuery(
                query,new String[]{new SavedPreference(mContext).getUserId()});
        if (c.getCount()>0) {
            if (c.moveToFirst()) {
                do {
                    cursorData.putString("blood_type", c.getString(c.getColumnIndex(DatabaseStructure.Profile.BLOOD_TYPE)));
                    cursorData.putString("date_of_birth", c.getString(c.getColumnIndex(DatabaseStructure.Profile.DATE_OF_BIRTH)));
                    cursorData.putString("email_address", c.getString(c.getColumnIndex(DatabaseStructure.Profile.EMAIL_ADDRESS)));
                    cursorData.putString("contact_number", c.getString(c.getColumnIndex(DatabaseStructure.Profile.CONTACT_NUMBER)));
                    cursorData.putString("file_path", c.getString(c.getColumnIndex(DatabaseStructure.Profile.USER_IMAGE_PATH)));
                } while (c.moveToNext());
            }
            c.close();
            return cursorData;
        }else {
            c.close();
            return null;
        }
    }
}


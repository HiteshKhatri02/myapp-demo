package com.myapp.demo.database;


/**
 * A class contains structure of database.
 */

class DatabaseStructure {
    //Data Base Table Name
    final static String DATABASE_NAME="MyApp";
    //Data Base version
    final static int DATABASE_VERSION=1;
    //Fields to initialise in profile table
    static abstract class Profile {
        final static String TABLE_NAME="profile";
        final static String USER_ID="user_id";
        final static String BLOOD_TYPE="blood_type";
        final static String DATE_OF_BIRTH="dob";
        final static String EMAIL_ADDRESS="email_address";
        final static String CONTACT_NUMBER="contact_number";
        final static String USER_IMAGE_PATH="user_image";
    }

}

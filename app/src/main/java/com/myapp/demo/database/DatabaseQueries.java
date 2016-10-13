package com.myapp.demo.database;


/**
 * A class which contain database queries for the app
 */

abstract class DatabaseQueries {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    //Query to create Create Profile table
    static final String SQL_CREATE_PROFILE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
            +DatabaseStructure.Profile.TABLE_NAME + " (" +
            DatabaseStructure.Profile.USER_ID+INTEGER_TYPE+COMMA_SEP+
            DatabaseStructure.Profile.BLOOD_TYPE +INTEGER_TYPE+COMMA_SEP
            + DatabaseStructure.Profile.DATE_OF_BIRTH+TEXT_TYPE+COMMA_SEP
            +DatabaseStructure.Profile.EMAIL_ADDRESS+TEXT_TYPE+COMMA_SEP
            +DatabaseStructure.Profile.USER_IMAGE_PATH+TEXT_TYPE+COMMA_SEP
            +DatabaseStructure.Profile.CONTACT_NUMBER+TEXT_TYPE+" )";

    //Query to select row of a table
    static final String SQL_SEARCH_QUERY = "SELECT COUNT(*) FROM "
            + DatabaseStructure.Profile.TABLE_NAME;

    //Query for read files from database
    static final String SQL_READ_PROFILE_ENTRIES= "SELECT * FROM "
            + DatabaseStructure.Profile.TABLE_NAME
            + " WHERE " + DatabaseStructure.Profile.USER_ID + " = ?" ;
}

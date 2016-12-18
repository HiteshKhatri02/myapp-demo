# MyApp-Demo
# <hr>
# Table of Contents

* [Developer](#developer)

# <a name="developer"></a>Developer
* "Hitesh Khatri" <hiteshkhatri02@gmail.com>

# Third-Party APIs
I have used the following firbase library to ue firebase database, authuntication and firabase notifiction. <br/>

  **compile 'com.google.firebase:firebase-auth:9.6.1'**<br/>
  **compile 'com.google.firebase:firebase-database:9.6.1'**<br/>
  **compile 'com.google.firebase:firebase-messaging:9.6.1'**<br/>

Implemented nuatsra for image loading.<br/>
**compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.3'**<br/>

As networking libraries i have used retrofit 2.0.0- beta4<br/>

**compile 'com.squareup.retrofit2:retrofit:2.0.0-beta4'**
**compile 'com.squareup.retrofit2:converter-gson:2.0.0-beta4'**

#Login Credentials

Usrname:- testuser@demo.com<br/>
password:- password<br/>


#Screenshots, Flow and Code Snippet <br/>
## Splash Screen

As in the project there is no need not to process any work in splash except fetching user id from shared preference so I did not use handler for delay the next for 1 second. 

For that purpose I just made a drawable and set that drawable in *android:windowBackground* through styles by following this approach

At first I made the drawable name *drawable_splash_background*<br/> 

```javascript
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:drawable="@color/colorWhite"/>
    <item android:gravity="top" android:bottom="16dp" android:right="16dp"
        android:left="16dp" android:top="5dp">
        <bitmap
            android:gravity="center"
            android:src="@mipmap/ic_launcher"/>
    </item>
</layer-list>
```
<br/>

Then I implemented it in Styles <br/>
```javascript
 <!--Theme for now window background-->
    <style name="SplashTheme" parent="Theme.AppCompat.NoActionBar">
        <item name="android:windowBackground">@drawable/drawable_background_splash</item>
    </style>
```
<br/>

In *SplashActivity* I just checked if shared preference has a value then go to the edit screen otherwise go to the Login screen. <br/>

```javascript
/**
     * Method to redirect user on login activity or edit profile activity
     */
    private void init(){
        //Check user id is exists or not if exists then skip login screen
        SavedPreference savedPreference=new SavedPreference(SplashActivity.this);
        if (TextUtils.isEmpty(savedPreference.getUserId().trim())){
            //Open Login Activity
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            finish();
        }else {
            //Open Login Activity
            startActivity(new Intent(SplashActivity.this,EditProfileActivity.class));
            finish();
        }
    }
```
<br/>





















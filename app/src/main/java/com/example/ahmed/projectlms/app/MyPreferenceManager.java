package com.example.ahmed.projectlms.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.IntentCompat;
import android.util.Log;

import com.example.ahmed.projectlms.Activity.HomeActivity;
import com.example.ahmed.projectlms.Activity.LoginActivity;
import com.example.ahmed.projectlms.Models.UserModel;



public class MyPreferenceManager {

    private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "lms";

    // All Shared Preferences Keys
    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_STUDENT_PASS = "student_pass";
    private static final String KEY_STUDENT_FIRST_NAME = "user_first_name";
    private static final String KEY_STUDENT_LAST_NAME = "user_last_name";
    private static final String KEY_STUDENT_USER_NAME = "user_name";
    private static final String KEY_STUDENT_Image = "student_image";
    private static final String KEY_CLASS_ID = "class_id";
    private static final String KEY_STATUS = "status";


    // Constructor
    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void storeUser(UserModel user) {
        editor.clear();
        editor.commit();
        editor.putString(KEY_STUDENT_ID, user.getStudent_id());
        editor.putString(KEY_STUDENT_USER_NAME, user.getUserName());
        editor.putString(KEY_STUDENT_FIRST_NAME, user.getFirstName());
        editor.putString(KEY_STUDENT_LAST_NAME, user.getLastName());
        editor.putString(KEY_STUDENT_Image, user.getStudent_img());
        editor.putString(KEY_STUDENT_PASS, user.getPassword());
        editor.putString(KEY_CLASS_ID,user.getClass_id());
        editor.putString(KEY_STATUS,user.getStatus());
        editor.commit();


        Log.e(TAG, "User is stored in shared preferences. " + user.getUserName() + " ," + user.getPassword() );
    }

    public UserModel getUser() {
        if (pref.getString(KEY_STUDENT_ID, null) != null) {
            String id, firstname,lastname,studentImg, username, pass,class_id,status;
            id = pref.getString(KEY_STUDENT_ID, null);
            firstname = pref.getString(KEY_STUDENT_FIRST_NAME, null);
            lastname = pref.getString(KEY_STUDENT_LAST_NAME, null);
            studentImg = pref.getString(KEY_STUDENT_Image, null);
            username = pref.getString(KEY_STUDENT_USER_NAME, null);
            pass = pref.getString(KEY_STUDENT_PASS, null);
            class_id = pref.getString(KEY_CLASS_ID,null);
            status = pref.getString(KEY_STATUS,null);

            UserModel user = new UserModel();
            user.setStudent_id(id);
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setStudent_img(studentImg);
            user.setUserName(username);
            user.setPassword(pass);
            user.setClass_id(class_id);
            user.setStatus(status);
            return user;
        }
        return null;
    }
    public void clear() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(_context, LoginActivity.class);
        ComponentName cn = intent.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
        _context.startActivity(mainIntent);
    }
}

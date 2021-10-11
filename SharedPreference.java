package com.example.fbans.projecthm.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static SharedPreference instance;
    private static Context context;

    private static final String SHARED_PREF_NAME = "hospmanagepref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_NO = "keynum";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_TYPE = "keytype";
    private static final String KEY_AGE = "keyage";
    private static final String KEY_id = "keyid";

    private static final String KEY_APPO_TYPE = "keyaptype";
    private static final String KEY_APPO_DATE = "keyapdate";
    private static final String KEY_APPO_TIME = "keyaptime";

    private static final String KEY_INS = "keyins";
    private static final String KEY_PATNAME = "keypatname";
    private static final String KEY_ROONNO = "keyroomnum";
    private static final String KEY_STAFFNAME = "keysname";

    public SharedPreference(Context ctx) {
        context = ctx;
    }

    public static synchronized SharedPreference getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreference(context);
        }
        return instance;
    }

    public boolean userLogin(int id,String type,String name,String mobnum,String age,String gender ) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_id, id);
        editor.putString(KEY_TYPE,type);
        editor.putString(KEY_NO, mobnum);
        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_GENDER,age );
        editor.putString(KEY_AGE, gender);
        editor.apply();
        return true;
    }

    public boolean fixappo(String patname,String patmno,String appotype, String appodate,String appotime){

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_APPO_TYPE,appotype);
        editor.putString(KEY_NO, patmno);
        editor.putString(KEY_USERNAME, patname);
        editor.putString(KEY_APPO_DATE,appodate );
        editor.putString(KEY_APPO_TIME,appotime);
        editor.apply();
        return true;

    }

    public boolean addIns(String staffname,String patname,String roomnum, String ins){

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ROONNO,roomnum);
        editor.putString(KEY_PATNAME, patname);
        editor.putString(KEY_USERNAME,staffname);
        editor.putString(KEY_INS,ins );
        editor.apply();
        return true;

    }
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USERNAME, null) != null) {
            return true;
        }
        return false;
    }
    public boolean isSelected() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt(KEY_id, 0);
        return true;
        }

    public boolean logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}


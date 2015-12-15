package com.bernardrenzema.aimm.database.entities;

import android.util.Log;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends SugarRecord<User> {

    private static final String			TAG = "User";

    private int 		                mId;
    private String 		                mFirstName;
    private String 		                mLastNAme;
    private String 		                mUsername;
    private String 		                mClearenceLevel;
    private String 		                mPicture;

    public User() {
    }

    public User(JSONObject user) {
        set(user);
    }

    public User update(JSONObject user) {
        set(user);
        return this;
    }

    private void set(JSONObject user) {
        try {
            mId = user.getInt("id");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mFirstName = user.getString("firstName");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mLastNAme = user.getString("lastName");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mUsername = user.getString("username");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mClearenceLevel = user.getString("clearenceLevel");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mPicture = user.getString("picture");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
    }

    /*
    Getters
     */
    public int getUserId() {
        return mId;
    }
    public String getFirstName() {
        return mFirstName;
    }
    public String getLastName() {
        return mLastNAme;
    }
    public String getUsername() {
        return mUsername;
    }
    public String getClearenceLevel() {
        return mClearenceLevel;
    }
    public String getPicture() {
        return mPicture;
    }
}

package com.bernardrenzema.aimm.database.entities;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.bernardrenzema.aimm.entities.Callback;
import com.bernardrenzema.aimm.entities.RestClient;
import com.bernardrenzema.aimm.entities.Server;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Captain-Hook on 19/06/2015.
 */
public class Work implements Parcelable {

    private static String			    TAG = "Work";

    private int 		                mId;
    private String 		                mDescription;
    private int 		                mClient;
    private String 		                mClientName;
    private String 		                mStarted;
    private String 		                mEnded;
    private String 		                mPicture;

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Work createFromParcel(Parcel in) {
                    return new Work(in);
                }

                public Work[] newArray(int size) {
                    return new Work[size];
                }
            };

    public Work() {
    }

    public Work(JSONObject work) {
        set(work);
    }

    public Work(Parcel in){
        TAG = in.readString();
        mId = in.readInt();
        mDescription = in.readString();
        mClient = in.readInt();
        mClientName = in.readString();
        mStarted = in.readString();
        mEnded = in.readString();
        mPicture = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(TAG);
        out.writeInt(mId);
        out.writeString(mDescription);
        out.writeInt(mClient);
        out.writeString(mClientName);
        out.writeString(mStarted);
        out.writeString(mEnded);
        out.writeString(mPicture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Work update(JSONObject work) {
        set(work);
        return this;
    }

    private void set(JSONObject work) {
        try {
            mId = work.getInt("id");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mDescription = work.getString("description");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mClient = work.getInt("client");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mClientName = work.getString("clientName");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mStarted = work.getString("started");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mEnded = work.getString("ended");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mPicture = work.getString("picture");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
    }

    /*
    Getters
     */
    public int getWorkId() {
        return mId;
    }
    public String getDescription() {
        return mDescription;
    }
    public int getClient() {
        return mClient;
    }
    public String getClientName() {
        return mClientName;
    }
    public String getStarted() {
        return mStarted;
    }
    public String getEnded() {
        return mEnded;
    }
    public String getPicture() {
        return mPicture;
    }

    public static void getWorks(Activity context, final Callback callback) {
        RestClient.get(context, Server.R_WORKS, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Work> result = new ArrayList<Work>();

                for (int i = 0; i < response.length(); ++i) {
                    try {
                        Work work = new Work(response.getJSONObject(i));

                        result.add(work);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException: error = " + e);
                    }
                }
                callback.onSuccess(result);
            }
        });
    }

    public static void getRunningWorks(Activity context, final Callback callback) {
        RestClient.get(context, Server.R_RUNNING_WORKS, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Work> result = new ArrayList<Work>();

                for (int i = 0; i < response.length(); ++i) {
                    try {
                        Work work = new Work(response.getJSONObject(i));

                        result.add(work);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException: error = " + e);
                    }
                }
                callback.onSuccess(result);
            }
        });
    }
}

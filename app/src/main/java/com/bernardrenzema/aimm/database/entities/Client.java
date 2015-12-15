package com.bernardrenzema.aimm.database.entities;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.bernardrenzema.aimm.entities.Callback;
import com.bernardrenzema.aimm.entities.RestClient;
import com.bernardrenzema.aimm.entities.Server;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.orm.SugarRecord;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Client implements Parcelable {

    private static String			    TAG = "Client";

    private int 		                mId;
    private String 		                mStatus;
    private String 		                mName;
    private String 		                mAdress;
    private String 		                mCp;
    private String 		                mCity;
    private String 		                mCountry;
    private String 		                mPicture;

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Client createFromParcel(Parcel in) {
                    return new Client(in);
                }

                public Client[] newArray(int size) {
                    return new Client[size];
                }
            };

    public Client() {
    }

    public Client(JSONObject client) {
        set(client);
    }

    public Client(Parcel in){
        TAG = in.readString();
        mId = in.readInt();
        mStatus = in.readString();
        mName = in.readString();
        mAdress = in.readString();
        mCp = in.readString();
        mCity = in.readString();
        mCountry = in.readString();
        mPicture = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(TAG);
        out.writeInt(mId);
        out.writeString(mStatus);
        out.writeString(mName);
        out.writeString(mAdress);
        out.writeString(mCp);
        out.writeString(mCity);
        out.writeString(mCountry);
        out.writeString(mPicture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Client update(JSONObject client) {
        set(client);
        return this;
    }

    private void set(JSONObject client) {
        try {
            mId = client.getInt("id");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mStatus = client.getString("status");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mName = client.getString("name");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mAdress = client.getString("adress");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mCp = client.getString("cp");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mCity = client.getString("cp");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mCountry = client.getString("country");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mPicture = client.getString("picture");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;

        if (mId > 0 && other.mId > 0 && mId != other.mId) {
            return false;
        }
        if (mStatus != null  && other.mStatus != null && !mStatus.equals(other.mStatus)) {
            return false;
        }
        if (mName != null  && other.mName != null && !mName.equals(other.mName)) {
            return false;
        }
        if (mAdress != null  && other.mAdress != null && !mAdress.equals(other.mAdress)) {
            return false;
        }
        if (mCp != null  && other.mCp != null && !mCp.equals(other.mCp)) {
            return false;
        }
        if (mCity != null  && other.mCity != null && !mCity.equals(other.mCity)) {
            return false;
        }
        if (mCountry != null  && other.mCountry != null && !mCountry.equals(other.mCountry)) {
            return false;
        }
        return true;
    }

    /*
        Getters
         */
    public int getClientId() {
        return mId;
    }
    public String getName() {
        return mName;
    }

    public static void getClients(Activity context, final Callback callback) {
        RestClient.get(context, Server.R_CLIENTS, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Client> result = new ArrayList<Client>();

                for (int i = 0; i < response.length(); ++i) {
                    try {
                        Client client = new Client(response.getJSONObject(i));

                        result.add(client);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException: error = " + e);
                    }
                }
                callback.onSuccess(result);
            }
        });
    }

    public static void getClientById(Activity context, int id, final Callback callback) {
        RestClient.get(context, Server.R_CLIENT_BY_ID(id), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Client> result = new ArrayList<Client>();

                for (int i = 0; i < response.length(); ++i) {
                    try {
                        Client client = new Client(response.getJSONObject(i));

                        result.add(client);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException: error = " + e);
                    }
                }
                callback.onSuccess(result.get(0));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onFailure(statusCode, responseString);
            }
        });
    }
}

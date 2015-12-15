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

public class Workmaterial implements Parcelable {

    private static String			    TAG = "Workmaterial";

    private int 		                mId;
    private boolean                     mDirectDelivery;
    private int 		                mFamily;
    private String 		                mDescription;
    private String 		                mUnit;
    private String 		                mCode;
    private String 		                mBarcode;
    private double 		                mBuyPrice;
    private double 		                mSellPrice;
    private int 		                mAlertLevel;
    private String 		                mPicture;
    private int 		                mWork;
    private int 		                mClient;
    private double 		                mUsed;

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Workmaterial createFromParcel(Parcel in) {
                    return new Workmaterial(in);
                }

                public Workmaterial[] newArray(int size) {
                    return new Workmaterial[size];
                }
            };

    public Workmaterial() {
    }

    public Workmaterial(JSONObject workmaterial) {
        set(workmaterial);
    }

    public Workmaterial(Parcel in){
        TAG = in.readString();
        mId = in.readInt();
        mDirectDelivery = in.readByte() != 0;
        mFamily = in.readInt();
        mDescription = in.readString();
        mUnit = in.readString();
        mCode = in.readString();
        mBarcode = in.readString();
        mBuyPrice = in.readDouble();
        mSellPrice = in.readDouble();
        mAlertLevel = in.readInt();
        mPicture = in.readString();
        mBuyPrice = in.readDouble();
        mSellPrice = in.readDouble();
        mWork = in.readInt();
        mClient = in.readInt();
        mUsed = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(TAG);
        out.writeInt(mId);
        out.writeByte((byte) (mDirectDelivery ? 1 : 0));
        out.writeInt(mFamily);
        out.writeString(mDescription);
        out.writeString(mUnit);
        out.writeString(mCode);
        out.writeString(mBarcode);
        out.writeDouble(mBuyPrice);
        out.writeDouble(mSellPrice);
        out.writeInt(mAlertLevel);
        out.writeString(mPicture);
        out.writeDouble(mBuyPrice);
        out.writeDouble(mSellPrice);
        out.writeInt(mWork);
        out.writeInt(mClient);
        out.writeDouble(mUsed);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Workmaterial update(JSONObject workmaterial) {
        set(workmaterial);
        return this;
    }

    protected void set(JSONObject workmaterial) {
        try {
            mId = workmaterial.getInt("id");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mDirectDelivery = workmaterial.getBoolean("directDelivery");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mFamily = workmaterial.getInt("family");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mDescription = workmaterial.getString("description");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mUnit = workmaterial.getString("unit");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mCode = workmaterial.getString("code");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mBarcode = workmaterial.getString("barcode");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mBuyPrice = workmaterial.getDouble("buyPrice");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mAlertLevel = workmaterial.getInt("alertLevel");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mPicture = workmaterial.getString("picture");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mBuyPrice = workmaterial.getDouble("buyPrice");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mSellPrice = workmaterial.getDouble("sellPrice");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mWork = workmaterial.getInt("work");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mClient = workmaterial.getInt("client");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mUsed = workmaterial.getDouble("used");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
    }

    /*
    Getters
     */
    public int getWorkmaterialId() {
        return mId;
    }
    public int getFamily() {
        return mFamily;
    }
    public String getDescription() {
        return mDescription;
    }
    public String getUnit() {
        return mUnit;
    }
    public double getUsed() {
        return mUsed;
    }

    public static void getWorkmaterials(Activity context, final Callback callback) {
        RestClient.get(context, Server.R_WORKMATERIALS, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Workmaterial> result = new ArrayList<Workmaterial>();

                for (int i = 0; i < response.length(); ++i) {
                    try {
                        Workmaterial workmaterial = new Workmaterial(response.getJSONObject(i));

                        result.add(workmaterial);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException: error = " + e);
                    }
                }
                callback.onSuccess(result);
            }
        });
    }

    public static void getWorkmaterialsByWorkId(Activity context, int workId, final Callback callback) {
        RestClient.get(context, Server.R_WORKMATERIALS_BY_WORKID(workId), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Workmaterial> result = new ArrayList<Workmaterial>();
                for (int i = 0; i < response.length(); ++i) {
                    try {
                        Workmaterial workmaterial = new Workmaterial(response.getJSONObject(i));

                        result.add(workmaterial);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException: error = " + e);
                    }
                }
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, responseString + " " + statusCode);
            }
        });
    }

    public static void add(Activity context, JSONObject data, final Callback callback) {
        try {
            HttpEntity entity = new StringEntity(data.toString());

            RestClient.post(context, Server.R_WORKMATERIALS, entity, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Workmaterial result = new Workmaterial(response);

                    callback.onSuccess(result);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e(TAG, responseString + " " + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e(TAG, statusCode + " " + errorResponse);
                }
            });
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "UnsupportedEncodingException: error = " + e);
        }
    }
}

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

public class Stockmaterial implements Parcelable {

    private static String               TAG = "Stocmaterial";

    private int 		                mId;
    private int 		                mFamily;
    private String 		                mDescription;
    private String 		                mUnit;
    private String 		                mCode;
    private String 		                mBarcode;
    private double 		                mBuyPrice;
    private double 		                mSellPrice;
    private int 		                mAlertLevel;
    private String 		                mPicture;
    private double 		                mAvailable;

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Stockmaterial createFromParcel(Parcel in) {
                    return new Stockmaterial(in);
                }

                public Stockmaterial[] newArray(int size) {
                    return new Stockmaterial[size];
                }
            };

    public Stockmaterial() {
    }

    public Stockmaterial(JSONObject stockmaterial) {
        set(stockmaterial);
    }

    public Stockmaterial(Parcel in){
        TAG = in.readString();
        mId = in.readInt();
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
        mAvailable = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(TAG);
        out.writeInt(mId);
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
        out.writeDouble(mAvailable);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Stockmaterial update(JSONObject stockmaterial) {
        set(stockmaterial);
        return this;
    }

    private void set(JSONObject stockmaterial) {
        try {
            mId = stockmaterial.getInt("id");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mFamily = stockmaterial.getInt("family");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mDescription = stockmaterial.getString("description");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mUnit = stockmaterial.getString("unit");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mCode = stockmaterial.getString("code");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mBarcode = stockmaterial.getString("barcode");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mBuyPrice = stockmaterial.getDouble("buyPrice");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mAlertLevel = stockmaterial.getInt("alertLevel");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mPicture = stockmaterial.getString("picture");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mBuyPrice = stockmaterial.getDouble("buyPrice");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mSellPrice = stockmaterial.getDouble("sellPrice");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
        try {
            mAvailable = stockmaterial.getDouble("available");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: error = " + e);
        }
    }

    /*
    Getters
     */
    public int getStockId() {
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
    public String getBarcode() {
        return mBarcode;
    }
    public double getAvailable() {
        return mAvailable;
    }

    public static void getByKeywords(Activity context, final String query, final Callback callback) {
        RestClient.get(context, Server.R_STOCKMATERIALS_BY_KEYWORDS(query), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Stockmaterial> result = new ArrayList<Stockmaterial>();

                for (int i = 0; i < response.length(); ++i) {
                    try {
                        Stockmaterial material = new Stockmaterial(response.getJSONObject(i));

                        result.add(material);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException: error = " + e);
                    }
                }
                callback.onSuccess(result);

            }
        });
    }

    public static void getByBarcode(Activity context, final String barcode, final Callback callback) {
        RestClient.get(context, Server.R_STOCKMATERIALS_BY_BARCODE(barcode), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Stockmaterial> result = new ArrayList<Stockmaterial>();

                for (int i = 0; i < response.length(); ++i) {
                    try {
                        Stockmaterial material = new Stockmaterial(response.getJSONObject(i));

                        result.add(material);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException: error = " + e);
                    }
                }
                callback.onSuccess(result);

            }
        });
    }

    public static void entry(Activity context, JSONObject data, final Callback callback) {
        try {
            HttpEntity entity = new StringEntity(data.toString());

            RestClient.post(context, Server.R_STOCK_ENTRY, entity, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    callback.onSuccess(new Stockmaterial(response));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e(TAG, statusCode + " " + errorResponse);
                    callback.onFailure(statusCode, errorResponse);
                }
            });
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "UnsupportedEncodingException: error = " + e);
        }
    }

    public static void exit(Activity context, JSONObject data, final Callback callback) {
        try {
            HttpEntity entity = new StringEntity(data.toString());

            RestClient.post(context, Server.R_STOCK_EXIT, entity, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    callback.onSuccess(new Stockmaterial(response));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e(TAG, statusCode + " " + errorResponse);
                    callback.onFailure(statusCode, errorResponse);
                }
            });
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "UnsupportedEncodingException: error = " + e);
        }
    }
}

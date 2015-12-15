package com.bernardrenzema.aimm.entities;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.bernardrenzema.aimm.LoginActivity;
import com.bernardrenzema.aimm.MainActivity;
import com.bernardrenzema.aimm.R;
import com.bernardrenzema.aimm.database.entities.User;
import com.bernardrenzema.aimm.tools.SnackbarHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AppUser {

	private static final String			TAG = "AppUser";

	private static AppUser				gAppUser;

	private int							mUserId;
	private String						mToken;

	private AppUser(int id, String token) {
		mUserId = id;
		mToken = token;
	}
	
	public static AppUser newAppUser(int id, String token) {
		return gAppUser =  new AppUser(id, token);
	}
	
	public static void connect(final Activity context, final String username, String password, final Callback callback) {
		try {
			MessageDigest mDigest = MessageDigest.getInstance("SHA1");

			Log.e(TAG, byteArrayToHexString(mDigest.digest(password.getBytes())));
			JSONObject data = new JSONObject().
					put("username", username).
					put("password", byteArrayToHexString(mDigest.digest(password.getBytes())));
			HttpEntity entity = new StringEntity(data.toString());

			RestClient.post(context, Server.R_LOGIN, entity, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					List<User> results = User.find(User.class, "m_username = ?", username);

					if (results.size() > 0) {
						results.get(0).update(response)
								.save();
					} else {
						new User(response).save();
					}
					try {
						AppUser.newAppUser(response.getInt("id"), response.getString("token"));
					} catch (JSONException e) {
						Log.e(TAG, "JSONException: error = " + e);
					}
					callback.onSuccess();
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					callback.onFailure(statusCode, responseString);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
					callback.onFailure(statusCode, "Error : " + statusCode);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					try {
						callback.onFailure(statusCode, errorResponse.getString("message"));
					} catch (JSONException e) {
						Log.e(TAG, "JSONException: error = " + e);
					} catch (NullPointerException e1) {
						callback.onFailure(statusCode, context.getResources().getString(R.string.serverUnreachableError));
					}
				}
			});
		} catch (JSONException e) {
			Log.e(TAG, "ClientProtocolExeption: error = " + e);
		} catch (UnsupportedEncodingException e1) {
			Log.e(TAG, "UnsupportedEncodingException: error = " + e1);
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "NoSuchAlgorithmException: error = " + e);
		}
	}

	public static void disconnect(final Activity context, final boolean redirect) {
				Intent i = new Intent(context, LoginActivity.class);

				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(i);
				context.finish();
	}

	private static String byteArrayToHexString(byte[] b) {
		String result = "";
		for (int i=0; i < b.length; i++) {
			result +=
					Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}

	/*
    Getters
     */
	public static AppUser getInstance() {
		return gAppUser;
	}
	public static int getUserId() {
		return gAppUser.mUserId;
	}
	public static String getToken() {
		return gAppUser.mToken;
	}
}
package com.bernardrenzema.aimm.entities;

import android.app.Activity;
import android.nfc.Tag;
import android.util.Log;

import com.bernardrenzema.aimm.R;
import com.bernardrenzema.aimm.tools.ConnectionDetector;
import com.bernardrenzema.aimm.tools.SnackbarHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;

public class RestClient {

    private static AsyncHttpClient      mClient = new AsyncHttpClient();

    private RestClient() {
    }

    public static void newRestClient(Activity context) {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);

        mClient.setCookieStore(myCookieStore);
        mClient.setMaxRetriesAndTimeout(100, 5000);
    }

    public static RequestHandle get(Activity context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (!new ConnectionDetector(context).isOnline()) {
            new SnackbarHelper().make(context.findViewById(android.R.id.content), context.getString(R.string.noConnectionError))
                    .show();
            return null;
        }
        mClient.addHeader("Content-Type", "application/json");
        if (AppUser.getInstance() != null) {
            mClient.addHeader("aimm-id", String.valueOf(AppUser.getUserId()));
            mClient.addHeader("aimm-token", AppUser.getToken());
        }
        return mClient.get(url, params, responseHandler);
    }

    public static RequestHandle post(Activity context, String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
        if (!new ConnectionDetector(context).isOnline()) {
            new SnackbarHelper().make(context.findViewById(android.R.id.content), context.getString(R.string.noConnectionError))
                    .show();
            return null;
        }
        mClient.addHeader("Content-Type", "application/json");
        if (AppUser.getInstance() != null) {
            mClient.addHeader("aimm-id", String.valueOf(AppUser.getUserId()));
            mClient.addHeader("aimm-token", AppUser.getToken());
        }
        return mClient.post(context, url, entity, "application/json", responseHandler);
    }

    public static RequestHandle delete(String url, AsyncHttpResponseHandler responseHandler) {
        mClient.addHeader("Content-Type", "application/json");
        return mClient.delete(url, responseHandler);
    }
}

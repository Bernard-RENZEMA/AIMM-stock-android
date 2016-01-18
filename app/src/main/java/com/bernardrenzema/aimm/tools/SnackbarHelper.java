package com.bernardrenzema.aimm.tools;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SnackbarHelper {

	private Snackbar	mSnackbar;

	public SnackbarHelper make(View view, String text) {
		if (view != null) {
			mSnackbar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE);
		}
		return this;
	}

	public SnackbarHelper setAction(String action, OnClickListener listener) {
		if (mSnackbar != null) {
			mSnackbar.setAction(action, listener);
		}
		return this;
	}

	public SnackbarHelper setTextColor(int color) {
		if (mSnackbar != null) {
			View view = mSnackbar.getView();
			TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);

			tv.setTextColor(color);
		}
		return this;
	}

	public void show() {
		if (mSnackbar != null) {
			mSnackbar.show();
		}
	}
}

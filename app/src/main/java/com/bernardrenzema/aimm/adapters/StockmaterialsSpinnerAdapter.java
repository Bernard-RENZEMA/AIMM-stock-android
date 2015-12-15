package com.bernardrenzema.aimm.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.bernardrenzema.aimm.R;
import com.bernardrenzema.aimm.database.entities.Client;
import com.bernardrenzema.aimm.database.entities.Stockmaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StockmaterialsSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Activity                        mContext;
    private ArrayList<Stockmaterial>        mStoclmaterials;

    public StockmaterialsSpinnerAdapter(Activity context, ArrayList<Stockmaterial> stockmaterials) {
        mContext = context;
        mStoclmaterials = stockmaterials;
    }

    public int getCount() {
        return mStoclmaterials.size();
    }

    public Object getItem(int position) {
        return mStoclmaterials.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if ( convertView == null ){
            convertView = mContext.getLayoutInflater().inflate(R.layout.spinner_item, null);
        }

        TextView t1 = (TextView) convertView.findViewById(R.id.text);

        t1.setText(mStoclmaterials.get(position).getDescription());

        return convertView;
    }

    public int indexOf(Stockmaterial stockmaterial) {
        return mStoclmaterials.indexOf(stockmaterial);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView = mContext.getLayoutInflater().inflate(R.layout.spinner_item, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.text);

        textView.setText(mStoclmaterials.get(position).getDescription());

        return convertView;
    }
}

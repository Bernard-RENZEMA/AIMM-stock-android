package com.bernardrenzema.aimm.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bernardrenzema.aimm.R;
import com.bernardrenzema.aimm.WorkActivity;
import com.bernardrenzema.aimm.database.entities.Client;
import com.bernardrenzema.aimm.database.entities.User;
import com.bernardrenzema.aimm.database.entities.Work;
import com.bernardrenzema.aimm.database.entities.Workmaterial;
import com.bernardrenzema.aimm.entities.Callback;

import java.util.ArrayList;
import java.util.List;

public class WorkmaterialsRecyclerViewAdapter extends RecyclerView.Adapter<WorkmaterialsRecyclerViewAdapter.ItemViewHolder> {

    private static final String             TAG = "WorkmaterialsRecyclerViewAdapter";

    private Activity mContext;
    private ArrayList<Workmaterial>         mItems = new ArrayList<>();
    public android.os.Handler               mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();

            mItems = b.getParcelableArrayList("workmaterials");
            notifyDataSetChanged();
        }
    };

    public WorkmaterialsRecyclerViewAdapter(Activity context) {
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workmaterials_list_item, parent, false);

        return new ItemViewHolder((RelativeLayout) v);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final Workmaterial workmaterial = mItems.get(position);

        holder.mMaterial.setText(workmaterial.getDescription());
        holder.mUsed.setText(workmaterial.getUsed() + " " + workmaterial.getUnit());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView            mMaterial;
        private TextView            mUsed;

        public ItemViewHolder(final RelativeLayout root) {
            super(root);
            mMaterial = (TextView) root.findViewById(R.id.material);
            mUsed = (TextView) root.findViewById(R.id.used);
        }
    }
}

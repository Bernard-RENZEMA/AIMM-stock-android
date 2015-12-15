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
import android.widget.TextView;

import com.bernardrenzema.aimm.R;
import com.bernardrenzema.aimm.WorkActivity;
import com.bernardrenzema.aimm.database.entities.Client;
import com.bernardrenzema.aimm.database.entities.User;
import com.bernardrenzema.aimm.database.entities.Work;
import com.bernardrenzema.aimm.entities.Callback;

import java.util.ArrayList;
import java.util.List;

public class WorksRecyclerViewAdapter extends RecyclerView.Adapter<WorksRecyclerViewAdapter.ItemViewHolder> {

    private static final String             TAG = "WorksRecyclerViewAdapter<";

    private Activity mContext;
    private ArrayList<Work>                 mItems = new ArrayList<>();
    public android.os.Handler               mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();

            mItems = b.getParcelableArrayList("works");
            notifyDataSetChanged();
        }
    };

    public WorksRecyclerViewAdapter(Activity context) {
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.works_list_item, parent, false);

        return new ItemViewHolder((LinearLayout) v);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final Work work = mItems.get(position);

        holder.mClient.setText(work.getClientName());
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkActivity.class);
                Bundle b = new Bundle();

                b.putInt("client", work.getClient());
                b.putParcelable("work", work);
                intent.putExtras(b);
                mContext.startActivity(intent);
            }
        });
        holder.mDescription.setText(work.getDescription());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /*public void update(int pos, Work work) {
        mItems.set(pos, work);
        notifyItemChanged(pos);
    }

    public void add(int pos, Work work) {
        mItems.add(pos, work);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        mItems.remove(pos);
        notifyItemRemoved(pos);
    }*/

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout            mRoot;
        TextView                mClient;
        TextView                mDescription;

        public ItemViewHolder(final LinearLayout root) {
            super(root);
            mRoot = root;
            mClient = (TextView) root.findViewById(R.id.client);
            mDescription = (TextView) root.findViewById(R.id.description);
        }
    }
}

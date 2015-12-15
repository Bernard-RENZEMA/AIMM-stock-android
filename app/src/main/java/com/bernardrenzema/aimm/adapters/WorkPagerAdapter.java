package com.bernardrenzema.aimm.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.bernardrenzema.aimm.R;
import com.bernardrenzema.aimm.adapters.SmartFragmentStatePagerAdapter;
import com.bernardrenzema.aimm.database.entities.Client;
import com.bernardrenzema.aimm.database.entities.Work;
import com.bernardrenzema.aimm.fragments.OverviewFragment;
import com.bernardrenzema.aimm.fragments.WorksFragment;

public class WorkPagerAdapter extends SmartFragmentStatePagerAdapter {

    private static final String     TAG = "WorkPagerAdapter";

    private Context                 mContext;
    private Client                  mClient;
    private Work                    mWork;

    private final static int        NUM_ITEMS = 1;

    public final static int         POS_OVERVIEW = 0;

    public WorkPagerAdapter(Context context, FragmentManager fragmentManager, Client client, Work work) {
        super(fragmentManager);
        mContext = context;
        mClient = client;
        mWork = work;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POS_OVERVIEW:
                return OverviewFragment.newInstance(mClient, mWork);
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getStringArray(R.array.work_tab_items)[position];
    }
}

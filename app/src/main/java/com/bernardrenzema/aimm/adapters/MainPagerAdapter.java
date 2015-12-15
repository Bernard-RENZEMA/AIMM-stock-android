package com.bernardrenzema.aimm.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.bernardrenzema.aimm.R;
import com.bernardrenzema.aimm.adapters.SmartFragmentStatePagerAdapter;
import com.bernardrenzema.aimm.fragments.WorksFragment;

public class MainPagerAdapter extends SmartFragmentStatePagerAdapter {

    private static final String     TAG = "MainPagerAdapter";

    private Context                 mContext;

    private final static int        NUM_ITEMS = 1;

    public final static int         POS_WORKS = 0;

    public MainPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POS_WORKS:
                return WorksFragment.newInstance();
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
        return mContext.getResources().getStringArray(R.array.main_tab_items)[position];
    }
}

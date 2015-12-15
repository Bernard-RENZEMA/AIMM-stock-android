package com.bernardrenzema.aimm;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bernardrenzema.aimm.adapters.MainPagerAdapter;
import com.bernardrenzema.aimm.entities.OnFABClick;
import com.bernardrenzema.aimm.interfaces.WithFAB;
import com.bernardrenzema.aimm.interfaces.WithFABOnCLickListenerCallback;
import com.bernardrenzema.aimm.tools.FABHelper;
import com.bernardrenzema.aimm.tools.SnackbarHelper;


public class MainActivity extends AppCompatActivity implements WithFAB {

    private static final String     TAG = "MainActivity";

    public static final int         RESULT_NO_KO = -1;
    public static final int         RESULT_OK = 1;
    public static final int         RESULT_KO = 2;
    public static final int         REQUEST_VOICE = 1337;
    public static final int         RESULT_CANCELED = 3;

    public FABHelper                mFabHelper;

    private ViewPager               mViewPager;
    public Toolbar                  mActionBar;
    private TabLayout               mTabLayout;
    private MainPagerAdapter        mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mActionBar = (Toolbar) findViewById(R.id.actionBar);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mFabHelper = new FABHelper(this,
                new OnFABClick() {
                    @Override
                    public void onClick() {
                        switch (mViewPager.getCurrentItem()) {
                            case MainPagerAdapter.POS_WORKS:
                                WithFABOnCLickListenerCallback fragment = (WithFABOnCLickListenerCallback) mAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());

                                fragment.onFABCLick();
                                break;
                        }
                    }
                },
                new OnFABClick() {
                    @Override
                    public void onClick() {

                    }
                },
                new OnFABClick() {
                    @Override
                    public void onClick() {
                        switch (mViewPager.getCurrentItem()) {
                            case MainPagerAdapter.POS_WORKS:
                                WithFABOnCLickListenerCallback fragment = (WithFABOnCLickListenerCallback) mAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());

                                fragment.onFABOpt1CLick();
                                break;
                        }
                    }
                },
                new OnFABClick() {
                    @Override
                    public void onClick() {

                    }
                },
                new OnFABClick() {
                    @Override
                    public void onClick() {

                    }
                }
        );

        mActionBar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(mActionBar);
        mAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public FABHelper getFABHelper() {
        return mFabHelper;
    }
}

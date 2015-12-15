package com.bernardrenzema.aimm;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bernardrenzema.aimm.adapters.WorkPagerAdapter;
import com.bernardrenzema.aimm.database.entities.Client;
import com.bernardrenzema.aimm.database.entities.Work;
import com.bernardrenzema.aimm.entities.Callback;
import com.bernardrenzema.aimm.entities.OnFABClick;
import com.bernardrenzema.aimm.interfaces.WithFAB;
import com.bernardrenzema.aimm.interfaces.WithFABOnCLickListenerCallback;
import com.bernardrenzema.aimm.tools.FABHelper;
import com.google.zxing.integration.android.IntentIntegrator;


public class WorkActivity extends AppCompatActivity implements WithFAB {

    private static final String     TAG = "WorkActivity";

    public FABHelper                mFabHelper;

    private ViewPager               mViewPager;
    public Toolbar                  mActionBar;
    private TabLayout               mTabLayout;
    private WorkPagerAdapter        mAdapter;

    private Client                  mClient;
    private Work                    mWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        Client.getClientById(this, getIntent().getExtras().getInt("client"), new Callback() {
            @Override
            public void onSuccess(Object result) {
                mClient = (Client) result;
                mWork = getIntent().getExtras().getParcelable("work");


                getSupportActionBar().setTitle(mClient.getName());
                mAdapter = new WorkPagerAdapter(WorkActivity.this, getSupportFragmentManager(), mClient, mWork);
                mViewPager.setAdapter(mAdapter);
                mTabLayout.setupWithViewPager(mViewPager);
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mActionBar = (Toolbar) findViewById(R.id.actionBar);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mFabHelper = new FABHelper(this,
                new OnFABClick() {
                    @Override
                    public void onClick() {
                        switch (mViewPager.getCurrentItem()) {
                            case WorkPagerAdapter.POS_OVERVIEW:
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
                            case WorkPagerAdapter.POS_OVERVIEW:
                                WithFABOnCLickListenerCallback fragment = (WithFABOnCLickListenerCallback) mAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());

                                fragment.onFABOpt1CLick();
                                break;
                        }
                    }
                },
                new OnFABClick() {
                    @Override
                    public void onClick() {
                        switch (mViewPager.getCurrentItem()) {
                            case WorkPagerAdapter.POS_OVERVIEW:
                                WithFABOnCLickListenerCallback fragment = (WithFABOnCLickListenerCallback) mAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());

                                fragment.onFABOpt2CLick();
                                break;
                        }
                    }
                },
                new OnFABClick() {
                    @Override
                    public void onClick() {
                        switch (mViewPager.getCurrentItem()) {
                            case WorkPagerAdapter.POS_OVERVIEW:
                                WithFABOnCLickListenerCallback fragment = (WithFABOnCLickListenerCallback) mAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());

                                fragment.onFABOpt3CLick();
                                break;
                        }
                    }
                }
        );

        setSupportActionBar(mActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }

        return false;
    }

    @Override
    public FABHelper getFABHelper() {
        return mFabHelper;
    }
}

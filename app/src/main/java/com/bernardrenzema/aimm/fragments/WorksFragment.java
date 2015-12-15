package com.bernardrenzema.aimm.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bernardrenzema.aimm.CaptureActivityPortraitOrientation;
import com.bernardrenzema.aimm.CreatorActivity;
import com.bernardrenzema.aimm.MainActivity;
import com.bernardrenzema.aimm.R;
import com.bernardrenzema.aimm.adapters.WorksRecyclerViewAdapter;
import com.bernardrenzema.aimm.database.entities.Stockmaterial;
import com.bernardrenzema.aimm.database.entities.Work;
import com.bernardrenzema.aimm.entities.Callback;
import com.bernardrenzema.aimm.entities.Server;
import com.bernardrenzema.aimm.interfaces.WithFAB;
import com.bernardrenzema.aimm.interfaces.WithFABOnCLickListenerCallback;
import com.bernardrenzema.aimm.tools.FABHelper;
import com.bernardrenzema.aimm.tools.SnackbarHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class WorksFragment extends Fragment implements View.OnClickListener, WithFABOnCLickListenerCallback {

    private static final String     TAG = "WorksFragment";

    private Activity 		            mActivity;

    private RecyclerView                mRecyclerView;
    private WorksRecyclerViewAdapter    mAdapter;

    public static WorksFragment newInstance() {
        WorksFragment fragment = new WorksFragment();

        return fragment;
    }

    public WorksFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflateView = inflater.inflate(R.layout.fragment_works, container, false);

        mRecyclerView = (RecyclerView) inflateView.findViewById(R.id.recycler);

        setHasOptionsMenu(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new WorksRecyclerViewAdapter(mActivity);
        mRecyclerView.setAdapter(mAdapter);

        ((WithFAB) mActivity).getFABHelper().show(R.id.fab, R.drawable.ic_swap_horiz_white_36dp, -1);

        getRunningWorks();

        return inflateView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent intent) {
        switch (requestCode) {
            case CreatorActivity.REQUEST_CREATOR:
                String barcode = intent != null ? intent.getStringExtra("barcode") : null;
                String description = intent != null ? intent.getStringExtra("description") : null;
                String unit = intent != null ? intent.getStringExtra("unit") : null;
                int quantity = intent != null ? intent.getIntExtra("quantity", 0) : 0;
                final int type = intent != null ? intent.getIntExtra("type", -1) : -1;
                final SnackbarHelper snackbarHelper = new SnackbarHelper();

                if (resultCode == MainActivity.RESULT_OK) {
                    switch (type) {
                        case CreatorActivity.STOCK_ENTRY:
                            snackbarHelper.make(mActivity.findViewById(R.id.anchor), getString(R.string.entryOf, quantity, unit, description));
                            break;
                    }

                    if (barcode != null && quantity != 0) {
                        snackbarHelper.setAction(getString(android.R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    JSONObject data = new JSONObject().
                                            put("barcode", intent.getStringExtra("barcode")).
                                            put("quantity", intent.getIntExtra("quantity", 0));

                                    switch (type) {
                                        case CreatorActivity.STOCK_ENTRY:
                                            Stockmaterial.exit(mActivity, data, new Callback() {
                                                @Override
                                                public void onSuccess(Object result) {
                                                    new SnackbarHelper().make(mActivity.findViewById(R.id.anchor), mActivity.getResources().getString(R.string.success))
                                                            .setTextColor(mActivity.getResources().getColor(R.color.tealAccent2))
                                                            .show();
                                                }

                                                @Override
                                                public void onFailure(int code, Object error) {
                                                }
                                            });
                                            break;
                                    }
                                } catch (JSONException e) {
                                    Log.e(TAG, "JSONException: error = " + e);
                                }
                            }
                        });
                    }
                    snackbarHelper.setTextColor(mActivity.getResources().getColor(R.color.tealAccent2));
                    snackbarHelper.show();
                    getRunningWorks();
                } else if (resultCode == MainActivity.RESULT_KO) {
                    String error = intent == null ? getString(R.string.error) : intent.getStringExtra("error") == null ? getString(R.string.error) : intent.getStringExtra("error");

                    new SnackbarHelper().make(mActivity.findViewById(R.id.anchor), error)
                            .setTextColor(mActivity.getResources().getColor(R.color.red))
                            .show();
                    getRunningWorks();

                }
                break;
            case IntentIntegrator.REQUEST_CODE:
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                if(result != null) {
                    if(result.getContents() == null) {
                        Log.e(TAG, "Cancelled from fragment " + requestCode + " " + resultCode);
                    } else {
                        Intent i = new Intent(mActivity, CreatorActivity.class);
                        barcode = result.getContents();

                        Bundle b = new Bundle();
                        b.putInt("type", CreatorActivity.STOCK_ENTRY);
                        b.putString("barcode", barcode);
                        i.putExtras(b);
                        startActivityForResult(i, CreatorActivity.REQUEST_CREATOR);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void onFABCLick() {
        ((WithFAB) mActivity).getFABHelper().show(R.id.fabOpt1, R.drawable.ic_arrow_back_white_24dp, R.string.stockEntry);
    }

    @Override
    public void onFABCancelCLick() {
    }

    @Override
    public void onFABOpt1CLick() {
        Intent i = new Intent(mActivity, CreatorActivity.class);

        Bundle b = new Bundle();
        b.putInt("type", CreatorActivity.STOCK_ENTRY);
        i.putExtras(b);
        startActivityForResult(i, CreatorActivity.REQUEST_CREATOR);
/*        IntentIntegrator.forSupportFragment(this)
                .setCaptureActivity(CaptureActivityPortraitOrientation.class)
                .setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
                .setOrientationLocked(false)
                .initiateScan();*/
    }

    @Override
    public void onFABOpt2CLick() {
    }

    @Override
    public void onFABOpt3CLick() {
    }

    private void getRunningWorks() {
        Work.getRunningWorks(mActivity, new Callback() {
            @Override
            public void onSuccess(Object result) {
                Message m = new Message();
                Bundle data = new Bundle();

                data.putParcelableArrayList("works", (ArrayList<Work>) result);
                m.setData(data);
                mAdapter.mHandler.sendMessage(m);
            }
        });
    }
}
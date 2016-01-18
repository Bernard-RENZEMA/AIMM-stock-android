package com.bernardrenzema.aimm.fragments;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bernardrenzema.aimm.CreatorActivity;
import com.bernardrenzema.aimm.MainActivity;
import com.bernardrenzema.aimm.R;
import com.bernardrenzema.aimm.adapters.StockmaterialsSpinnerAdapter;
import com.bernardrenzema.aimm.database.entities.Client;
import com.bernardrenzema.aimm.database.entities.Stockmaterial;
import com.bernardrenzema.aimm.database.entities.Work;
import com.bernardrenzema.aimm.entities.Callback;
import com.bernardrenzema.aimm.tools.SnackbarHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StockEntryFragment extends Fragment implements View.OnClickListener {

    private static final String     TAG = "StockEntryFragment";

    private Activity                mActivity;

    private Spinner                 mAutoDescription;
    private ImageButton             mVoiceSearch;
    private LinearLayout            mClientWrapper;
    private TextView                mClientName;
    private LinearLayout            mWorkWrapper;
    private TextView                mWorkDescription;
    private EditText                mQuantity;
    private TextView                mUnit;
    private Button                  mOk;
    private RelativeLayout          mLoading;

    private Client                  mClient;
    private Work                    mWork;

    private boolean                 mProcessingClick = false;

    public static StockEntryFragment newInstance(Client client, Work work, String barcode) {
        StockEntryFragment fragment = new StockEntryFragment();
        Bundle b = new Bundle();

        b.putParcelable("client", client);
        b.putParcelable("work", work);
        b.putString("barcode", barcode);
        fragment.setArguments(b);
        return fragment;
    }

    public StockEntryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflateView = inflater.inflate(R.layout.fragment_stock_movement, container, false);

        mClient = getArguments().getParcelable("client");
        mWork = getArguments().getParcelable("work");

        mAutoDescription = (Spinner) inflateView.findViewById(R.id.autoDescription);
        mVoiceSearch = (ImageButton) inflateView.findViewById(R.id.voiceSearch);
        mClientWrapper = (LinearLayout) inflateView.findViewById(R.id.clientWrapper);
        mClientName = (TextView) inflateView.findViewById(R.id.client);
        mWorkWrapper = (LinearLayout) inflateView.findViewById(R.id.workWrapper);
        mWorkDescription = (TextView) inflateView.findViewById(R.id.work);
        mQuantity = (EditText) inflateView.findViewById(R.id.quantity);
        mUnit = (TextView) inflateView.findViewById(R.id.unit);
        mLoading = (RelativeLayout) inflateView.findViewById(R.id.loading);
        mOk = (Button) inflateView.findViewById(R.id.ok);

        if (mClient == null) {
            mClientWrapper.setVisibility(View.GONE);
            mWorkWrapper.setVisibility(View.GONE);
        } else {
            mClientName.setText(mClient.getName());
            mWorkDescription.setText(mWork.getDescription());
        }

        mVoiceSearch.setOnClickListener(this);
        mOk.setOnClickListener(this);

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.voiceSearch:
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "fr-FR");
                try {
                    startActivityForResult(i, MainActivity.REQUEST_VOICE);
                } catch (Exception e) {
                    Log.e(TAG, "Exception: error = " + e);
                }
                break;
            case R.id.ok:
                if (mProcessingClick) {
                    return;
                }
                mProcessingClick = true;
                getView().setEnabled(false);
                mLoading.setVisibility(View.VISIBLE);

                try {
                    final float quantity = Float.valueOf(mQuantity.getText().toString());

                    if (quantity > 0 && mAutoDescription.getSelectedItem() != null) {
                        JSONObject data = new JSONObject()
                                .put("barcode", ((Stockmaterial) mAutoDescription.getSelectedItem()).getBarcode())
                                .put("type", "stockmaterial")
                                .put("quantity", quantity);

                        if (mWork != null) {
                            data.put("work", mWork.getWorkId());
                        }
                        Stockmaterial.entry(mActivity, data, new Callback() {
                            @Override
                            public void onSuccess(Object result) {
                                Intent i = new Intent();

                                i.putExtra("type", CreatorActivity.STOCK_ENTRY);
                                i.putExtra("barcode", ((Stockmaterial) mAutoDescription.getSelectedItem()).getBarcode());
                                i.putExtra("description", ((Stockmaterial) result).getDescription());
                                i.putExtra("unit", ((Stockmaterial) result).getUnit());
                                i.putExtra("quantity", quantity);
                                mActivity.setResult(MainActivity.RESULT_OK, i);
                                mActivity.finish();
                            }

                            @Override
                            public void onFailure(int code, Object error) {
                                Intent i = new Intent();

                                try {
                                    ((JSONObject) error).getString("material");
                                    i.putExtra("error", getString(R.string.unknownMaterialError));
                                } catch (JSONException e) {
                                    Log.e(TAG, "JSONException: error = " + e);
                                }
                                try {
                                    ((JSONObject) error).getString("workmaterial");
                                    i.putExtra("error", code == 404 ?
                                            getString(R.string.workmaterialNeverUsedError)
                                            :
                                            getString(R.string.quantityNegativeError));
                                } catch (JSONException e) {
                                    Log.e(TAG, "JSONException: error = " + e);
                                }
                                mActivity.setResult(MainActivity.RESULT_KO, i);
                                mActivity.finish();
                            }
                        });
                    } else {
                        mProcessingClick = false;
                        getView().setEnabled(true);
                        mLoading.setVisibility(View.GONE);
                    }
                } catch (JSONException | NumberFormatException  e) {
                    Log.e(TAG, "exception: error = " + e);
                    mProcessingClick = false;
                    getView().setEnabled(true);
                    mLoading.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent intent) {
        switch (requestCode) {
            case MainActivity.REQUEST_VOICE:
                if (resultCode == MainActivity.RESULT_NO_KO) {
                    ArrayList<String> thingsYouSaid = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String query = "";

                    for (String thing : thingsYouSaid) {
                        query += (query.length() <= 0 ? "" : "+") + thing.replace(" ", ",");
                    }
                    Stockmaterial.getByKeywords(mActivity, query, new Callback() {
                        @Override
                        public void onSuccess(Object result) {
                            mAutoDescription.setAdapter(new StockmaterialsSpinnerAdapter(mActivity, (ArrayList<Stockmaterial>) result));
                        }
                    });
                }
                break;
        }
    }
}

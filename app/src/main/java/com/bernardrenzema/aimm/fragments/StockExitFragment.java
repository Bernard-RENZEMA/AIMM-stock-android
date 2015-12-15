package com.bernardrenzema.aimm.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StockExitFragment extends Fragment implements View.OnClickListener {

    private static final String     TAG = "StockExitFragment";

    private Activity                mActivity;

    private Spinner                 mAutoDescription;
    private ImageButton             mVoiceSearch;
    private TextView                mClientName;
    private LinearLayout            mWorkWrapper;
    private TextView                mWorkDescription;
    private EditText                mQuantity;
    private TextView                mUnit;
    private Button                  mOk;
    private RelativeLayout          mLoading;

    private Client                  mClient;
    private Work                    mWork;
    private String                  mBarcode;

    private boolean                 mProcessingClick = false;

    public static StockExitFragment newInstance(Client client, Work work, String barcode) {
        StockExitFragment fragment = new StockExitFragment();
        Bundle b = new Bundle();

        b.putParcelable("client", client);
        b.putParcelable("work", work);
        b.putString("barcode", barcode);
        fragment.setArguments(b);
        return fragment;
    }

    public StockExitFragment() {
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
        mBarcode = getArguments().getString("barcode");

        mAutoDescription = (Spinner) inflateView.findViewById(R.id.autoDescription);
        mVoiceSearch = (ImageButton) inflateView.findViewById(R.id.voiceSearch);
        mClientName = (TextView) inflateView.findViewById(R.id.client);
        mWorkWrapper = (LinearLayout) inflateView.findViewById(R.id.workWrapper);
        mWorkDescription = (TextView) inflateView.findViewById(R.id.work);
        mQuantity = (EditText) inflateView.findViewById(R.id.quantity);
        mUnit = (TextView) inflateView.findViewById(R.id.unit);
        mLoading = (RelativeLayout) inflateView.findViewById(R.id.loading);
        mOk = (Button) inflateView.findViewById(R.id.ok);

        mClientName.setText(mClient.getName());
        mWorkDescription.setText(mWork.getDescription());

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
                        JSONObject data = new JSONObject().
                                put("barcode", ((Stockmaterial) mAutoDescription.getSelectedItem()).getBarcode()).
                                put("work", mWork.getWorkId()).
                                put("client", mClient.getClientId()).
                                put("type", "stockmaterial").
                                put("quantity", quantity);

                        Stockmaterial.exit(mActivity, data, new Callback() {
                            @Override
                            public void onSuccess(Object result) {
                                Intent i = new Intent();

                                i.putExtra("type", CreatorActivity.STOCK_EXIT);
                                i.putExtra("barcode", mBarcode);
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
                                    ((JSONObject) error).getString("stockmaterial");
                                    i.putExtra("error", getString(R.string.quantityNegativeError));
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
                    Log.e(TAG, query);
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

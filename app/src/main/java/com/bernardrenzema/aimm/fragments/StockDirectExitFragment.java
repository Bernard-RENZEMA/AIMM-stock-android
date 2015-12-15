package com.bernardrenzema.aimm.fragments;

import android.app.Activity;
import android.content.Intent;
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
import com.bernardrenzema.aimm.database.entities.Client;
import com.bernardrenzema.aimm.database.entities.Work;
import com.bernardrenzema.aimm.database.entities.Workmaterial;
import com.bernardrenzema.aimm.entities.Callback;

import org.json.JSONException;
import org.json.JSONObject;

public class StockDirectExitFragment extends Fragment implements View.OnClickListener {

    private static final String     TAG = "StockExitFragment";

    private Activity                mActivity;

    private Spinner                 mAutoDescription;
    private EditText                mManualDescription;
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

    public static StockDirectExitFragment newInstance(Client client, Work work, String barcode) {
        StockDirectExitFragment fragment = new StockDirectExitFragment();
        Bundle b = new Bundle();

        b.putParcelable("client", client);
        b.putParcelable("work", work);
        b.putString("barcode", barcode);
        fragment.setArguments(b);
        return fragment;
    }

    public StockDirectExitFragment() {
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
        mManualDescription = (EditText) inflateView.findViewById(R.id.manualDescription);
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

        mAutoDescription.setVisibility(View.GONE);
        mManualDescription.setVisibility(View.VISIBLE);
        mVoiceSearch.setVisibility(View.GONE);
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
            case R.id.ok:
                if (mProcessingClick) {
                    return;
                }
                mProcessingClick = true;
                getView().setEnabled(false);
                mLoading.setVisibility(View.VISIBLE);

                try {
                    final float quantity = Float.valueOf(mQuantity.getText().toString());
                    final String description = mManualDescription.getText().toString();

                    if (quantity > 0 && description.length() > 0) {
                        JSONObject data = new JSONObject().
                                put("work", mWork.getWorkId()).
                                put("client", mClient.getClientId()).
                                put("description", description).
                                put("type", "directdelivery").
                                put("isExtra", false).
                                put("quantity", quantity).
                                put("unit", "unite");

                                Log.e(TAG, "d = " + data);
                                Workmaterial.add(mActivity, data, new Callback() {
                                    @Override
                                    public void onSuccess(Object result) {
                                        Intent i = new Intent();

                                        i.putExtra("type", CreatorActivity.STOCK_DIRECT);
                                        i.putExtra("description", ((Workmaterial) result).getDescription());
                                        i.putExtra("unit", ((Workmaterial) result).getUnit());
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
}

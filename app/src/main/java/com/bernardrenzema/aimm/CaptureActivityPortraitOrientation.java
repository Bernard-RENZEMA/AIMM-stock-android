package com.bernardrenzema.aimm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

public class CaptureActivityPortraitOrientation extends Activity {

    private static final String         TAG = CaptureActivityPortraitOrientation.class.getSimpleName();

    private CompoundBarcodeView         barcodeView;
    private Button                      mScan;
    private Button                      mCancel;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            //ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
            //imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
            Intent i = new Intent();

            i.putExtra("SCAN_RESULT", result.getText());
            i.putExtra("SCAN_RESULT_FORMAT", result.getBarcodeFormat());
            setResult(CaptureActivity.RESULT_OK, i);
            finish();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_capture);

        barcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);
        mScan = (Button) findViewById(R.id.scan);
        mCancel = (Button) findViewById(R.id.cancel);

        barcodeView.setStatusText("");
        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScan.setVisibility(View.INVISIBLE);
                mCancel.setVisibility(View.VISIBLE);
                barcodeView.decodeSingle(callback);
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}

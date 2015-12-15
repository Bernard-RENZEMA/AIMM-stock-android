package com.bernardrenzema.aimm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bernardrenzema.aimm.database.entities.Client;
import com.bernardrenzema.aimm.database.entities.Work;
import com.bernardrenzema.aimm.fragments.StockDirectExitFragment;
import com.bernardrenzema.aimm.fragments.StockEntryFragment;
import com.bernardrenzema.aimm.fragments.StockExitFragment;


public class CreatorActivity extends AppCompatActivity {

    private static final String             TAG = "CreatorActivity";
    public static final int                 REQUEST_CREATOR = 42;

    public static final int                 STOCK_ENTRY = 1;
    public static final int                 STOCK_EXIT = 2;
    public static final int                 STOCK_COMEBACK = 3;
    public static final int                 STOCK_DIRECT = 4;


    public Toolbar                          mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator);

        mActionBar = (Toolbar) findViewById(R.id.actionBar);

        mActionBar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(mActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        Bundle b = getIntent().getExtras();
        int type = b.getInt("type");

        getSupportActionBar().setTitle(getResources().getStringArray(R.array.creator_items)[type]);
        switch (type) {
            case STOCK_COMEBACK:
            case STOCK_ENTRY:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, StockEntryFragment.newInstance((Client) b.getParcelable("client"), (Work) b.getParcelable("work"), b.getString("barcode")))
                        .commit();
                break;
            case STOCK_EXIT:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, StockExitFragment.newInstance((Client) b.getParcelable("client"), (Work) b.getParcelable("work"), b.getString("barcode")))
                        .commit();
                break;
            case STOCK_DIRECT:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, StockDirectExitFragment.newInstance((Client) b.getParcelable("client"), (Work) b.getParcelable("work"), b.getString("barcode")))
                        .commit();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(MainActivity.RESULT_CANCELED);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

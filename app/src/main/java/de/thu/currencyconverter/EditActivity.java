package de.thu.currencyconverter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.helper.widget.Carousel;

import java.util.Arrays;

import io.paperdb.Paper;

public class EditActivity extends AppCompatActivity {
    //TEST
    ExchangeRate[] exchangeRates2 = Paper.book().read("Database");
    //ExchangeRate[] exchangeRates2 = new ExchangeRateDatabase().getExchangeRates();
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_curency);
        CurrencyListAdapter adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
        ListView listView = (ListView) findViewById(R.id.CurrencyList);
        listView.setAdapter(adapter);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Log.d("OnAct", "CALLED: ");
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    CurrencyListAdapter adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
                    assert data != null;
                    String newRate = data.getStringExtra("newCurrencyRate");
                    int position = data.getIntExtra("position",0);
                    ExchangeRate[] r = Paper.book().read("Database");
                    Log.d("PAPER", "onActivityResult: "+r[1].toString());
                    r[position].rateForOneEuro = Double.parseDouble(newRate);
                    Paper.book().write("Database", r);
                    adapter.notifyData();

                    Log.d("OnAct", "CALLED: " + newRate);
                }
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, EditingDetails.class);
            i.putExtra("currencyName", exchangeRates2[position].getCurrencyName());
            i.putExtra("currentRate", exchangeRates2[position].getRateForOneEuro());
            i.putExtra("position", position);
            //startActivity(i);
            activityResultLauncher.launch(i);
        });
        Toolbar toolbar_list = (Toolbar)findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar_list);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.edit_currency));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //@Override
    //protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    //    Log.d("OnAct", "CALLED: ");
    //    super.onActivityResult(requestCode, resultCode, data);
    //    assert data != null;
    //    CurrencyListAdapter adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
    //    String newRate = data.getStringExtra("newCurrencyRate");
    //    //ExchangeRate Entry = (ExchangeRate) adapter.getItem(requestCode);
    //    //Entry.rateForOneEuro = Double.parseDouble(newRate);
    //    ExchangeRate[] r = Paper.book().read("Database");
    //    Log.d("PAPER", "onActivityResult: "+r[1].toString());
    //    r[requestCode].rateForOneEuro = Double.parseDouble(newRate);
    //    Paper.book().write("Database", r);
    //    adapter.notifyData(); }
}
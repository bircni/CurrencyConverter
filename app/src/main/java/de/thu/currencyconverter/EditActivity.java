package de.thu.currencyconverter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.helper.widget.Carousel;

import java.util.Arrays;

public class EditActivity extends AppCompatActivity {
    ExchangeRate[] exchangeRates2 = new ExchangeRateDatabase().getExchangeRates();
    ExchangeRateDatabase db = new ExchangeRateDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_curency);
        CurrencyListAdapter adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
        ListView listView = (ListView) findViewById(R.id.CurrencyList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            //ExchangeRate Entry = (ExchangeRate) adapter.getItem(position);
            Intent i = new Intent(this, EditingDetails.class);
            i.putExtra("currencyName", exchangeRates2[position].getCurrencyName());
            i.putExtra("currentRate", exchangeRates2[position].getRateForOneEuro());
            startActivity(i);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        CurrencyListAdapter adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
        String newRate = data.getStringExtra("newCurrencyRate");
        ExchangeRate Entry = (ExchangeRate) adapter.getItem(requestCode);
        Entry.rateForOneEuro = Double.parseDouble(newRate);
        adapter.notifyData(); }
}
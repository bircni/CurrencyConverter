package de.thu.currencyconverter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class CurrencyListActivity extends AppCompatActivity {
    ExchangeRate[] exchangeRates2 = new ExchangeRateDatabase().getExchangeRates();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);
        CurrencyListAdapter adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
        ListView listView = (ListView) findViewById(R.id.CurrencyList);
        listView.setAdapter(adapter);
        //Still crashing on Item clicked
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView < ? > parent, View view,
                                    int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California"));
                if(i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }

            }
        } );
    }
}
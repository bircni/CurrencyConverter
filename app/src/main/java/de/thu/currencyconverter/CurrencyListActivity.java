package de.thu.currencyconverter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class CurrencyListActivity extends AppCompatActivity {
    ExchangeRate[] exchangeRates2 = new ExchangeRateDatabase().getExchangeRates();
    ExchangeRateDatabase db = new ExchangeRateDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);
        CurrencyListAdapter adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
        ListView listView = (ListView) findViewById(R.id.CurrencyList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String url = "geo:0,0?q=" + db.getCapital(exchangeRates2[position].getCurrencyName());
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);

        });
    }
}
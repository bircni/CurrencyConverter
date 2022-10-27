package de.thu.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.Arrays;

public class CurrencyListActivity extends AppCompatActivity {

    //String[] exchangeRates = ExchangeRateDatabase.getCurrencies();
    ExchangeRate[] exchangeRates2 = new ExchangeRateDatabase().getExchangeRates();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        //ArrayAdapter<String> adapter = new ArrayAdapter<>( this,
        //        R.layout.list_view_item, R.id.text_view, exchangeRates );
        CurrencyListAdapter adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
        ListView listView = (ListView) findViewById(R.id.CurrencyList);
        listView.setAdapter(adapter);
    }
}
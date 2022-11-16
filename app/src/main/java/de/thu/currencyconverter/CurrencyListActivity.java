package de.thu.currencyconverter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;

/**
 * This activity is show the list of currencies.
 */
public class CurrencyListActivity extends AppCompatActivity {
    ExchangeRate[] exchangeRates2 = new ExchangeRateDatabase().getExchangeRates();
    ExchangeRateDatabase db = new ExchangeRateDatabase();

    /**
     * This method is called when the activity is created.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);
        CurrencyListAdapter adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
        ListView listView = findViewById(R.id.CurrencyList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String url = "geo:0,0?q=" + db.getCapital(exchangeRates2[position].getCurrencyName());
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);

        });
        Toolbar toolbar_list = findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar_list);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * This method is called when the back button is pressed.
     *
     * @return True if the back button is pressed.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
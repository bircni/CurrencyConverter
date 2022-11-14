package de.thu.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;

import io.paperdb.Paper;

public class EditActivity extends AppCompatActivity {
    ExchangeRate[] exchangeRates;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_curency);
        exchangeRates = Paper.book().read("Database");
        assert exchangeRates != null;
        CurrencyEditAdapter adapter = new CurrencyEditAdapter(Arrays.asList(exchangeRates));
        ListView listView = findViewById(R.id.CurrencyListEdit);
        listView.setAdapter(adapter);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                CurrencyEditAdapter adapter1 = new CurrencyEditAdapter(Arrays.asList(exchangeRates));
                assert data != null;
                String newRate = data.getStringExtra("newCurrencyRate");
                int position = data.getIntExtra("position", 0);
                ExchangeRate[] r = Paper.book().read("Database");
                assert r != null;
                r[position].rateForOneEuro = Double.parseDouble(newRate);
                Paper.book().write("Database", r);
                adapter1.notifyDataSetChanged();

            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, EditingDetails.class);
            i.putExtra("currencyName", exchangeRates[position].getCurrencyName());
            i.putExtra("currentRate", exchangeRates[position].getRateForOneEuro());
            i.putExtra("position", position);
            activityResultLauncher.launch(i);
        });
        Toolbar toolbar_list = findViewById(R.id.toolbar_list);
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
}
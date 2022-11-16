package de.thu.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import io.paperdb.Paper;

public class EditingDetails extends AppCompatActivity {

    String currencyName = null;
    String actionBar;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editing_details);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        TextView currRate = findViewById(R.id.currentRate);
        ExchangeRate[] exchangeRates = Paper.book().read("Database");
        if (b != null) {
            currencyName = (String) b.get("currencyName");
            position = (int) b.get("position");
        }
        assert exchangeRates != null;
        String rate = String.format(getResources().getConfiguration().getLocales().get(0), "%1.2f", exchangeRates[position].rateForOneEuro);
        currRate.setText(String.format(getString(R.string.current_rate), rate));
        if (currencyName != null) {
            actionBar = String.format(getString(R.string.edit_text), currencyName);
        } else {
            actionBar = getString(R.string.edit_currency);
        }
        TextView textView = findViewById(R.id.edit_details);
        textView.setOnEditorActionListener((textView1, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("newCurrencyRate", textView1.getText().toString());
                returnIntent.putExtra("position", position);
                setResult(RESULT_OK, returnIntent);
                finish();
                return true;
            }
            return false;
        });
        Toolbar toolbar_list = findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar_list);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBar);
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
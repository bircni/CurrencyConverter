package de.thu.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

public class EditingDetails extends AppCompatActivity {

    String currencyName = null;
    double currencyRate;
    String actionBar;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editing_details);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        TextView currRate = findViewById(R.id.currentRate);
        if (b != null) {
            currencyName = (String) b.get("currencyName");
            currencyRate = (Double) b.get("currentRate");
            position = (int) b.get("position");
        }
        currRate.setText("Current rate: " + String.valueOf(currencyRate));
        if (currencyName != null) {
            actionBar = String.format(getString(R.string.edit_text), currencyName);
        } else {
            actionBar = getString(R.string.edit_currency);
        }
        TextView textView = (TextView) findViewById(R.id.edit_details);
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
        Toolbar toolbar_list = (Toolbar) findViewById(R.id.toolbar_list);
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
package de.thu.currencyconverter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import java.util.Arrays;
import java.util.Objects;

import io.paperdb.Paper;

/**
 * Main activity of the app.
 */
public class MainActivity extends AppCompatActivity {

    ExchangeRate[] exchangeRates2; // = new ExchangeRateDatabase().getExchangeRates();
    CurrencyListAdapter adapter; // = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
    ShareActionProvider shareActionProvider;
    /**
     * Checks if the input is a number > 0
     *
     * @param input String to check
     * @return true if String is usable
     */
    private static boolean checkInput(String input) {
        if (input.isEmpty()) return false;
        String temp = input.replace(",", ".");
        try {
            Double.parseDouble(temp);
        } catch (NumberFormatException ex) {
            return false;
        }
        return Double.parseDouble(temp) != 0;
    }

    /**
     * This method is called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        ExchangeRateDatabase.initDB();
        exchangeRates2 = Paper.book().read("Database");
        //NEW
        //exchangeRates2 = new ExchangeRateDatabase().getExchangeRates();
        adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
        Spinner from_value = findViewById(R.id.from_value);
        from_value.setAdapter(adapter);
        Spinner to_value = findViewById(R.id.to_value);
        to_value.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        setShareText(null);
        return true;
    }
    private void setShareText(String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        String share = text;
        shareIntent.setType("text/plain");
        if (text != null && !text.isEmpty()) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, share);
        }
        shareActionProvider.setShareIntent(shareIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.maps_menu:
                Intent detailsIntent = new Intent(getApplicationContext(), CurrencyListActivity.class);
                startActivity(detailsIntent);
                return true;
            case R.id.edit_menu:
                Intent editIntent = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(editIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * This method is called when the convert button is clicked.
     *
     * @param view The view that was clicked.
     */
    public void onConvertClick(View view) {
        //Add new PaperDB
        //Update DB
        exchangeRates2 = Paper.book().read("Database");
        Log.d("Test","Click!!");
        String from = exchangeRates2[((Spinner) findViewById(R.id.from_value)).getSelectedItemPosition()].getCurrencyName();
        int fromI = ((Spinner) findViewById(R.id.from_value)).getSelectedItemPosition();
        int toI = ((Spinner) findViewById(R.id.to_value)).getSelectedItemPosition();
        String to = exchangeRates2[((Spinner) findViewById(R.id.to_value)).getSelectedItemPosition()].getCurrencyName();
        EditText number = findViewById(R.id.number_input);
        if (!checkInput(number.getText().toString())) {
            displayAlert(getString(R.string.no_number));
        } else {
            double amount = number.getText().toString().isEmpty() ? 0 : Double.parseDouble(number.getText().toString());
            //double result = ExchangeRateDatabase.convert(amount, from, to);
            //Log.d("CONVERT", "onConvertClick: " + result);
            double result = ExchangeRateDatabase.convertNEW(amount, fromI, toI);
            //Log.d("CONVERT", "onConvertClick: " + result2);
            TextView result_value = findViewById(R.id.Converted);
            String resultF = String.format(getResources().getConfiguration().getLocales().get(0), "%1.2f", result);
            String amountF = String.format(getResources().getConfiguration().getLocales().get(0), "%1.2f", amount);
            result_value.setText(resultF);
            String share = String.format(getString(R.string.share_text),amountF,from,to,resultF);
            setShareText(share);
        }
    }

    /**
     * Displays an Alert on the phone screen
     *
     * @param message the message to be displayed
     */
    private void displayAlert(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setCancelable(true);
        alert.setPositiveButton("Ok", (dialog, id) -> dialog.cancel());
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
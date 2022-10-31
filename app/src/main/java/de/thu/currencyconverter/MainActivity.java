package de.thu.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

/**
 * Main activity of the app.
 */
public class MainActivity extends AppCompatActivity {

    ExchangeRate[] exchangeRates2 = new ExchangeRateDatabase().getExchangeRates();
    CurrencyListAdapter adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));

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
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner from_value = findViewById(R.id.from_value);
        from_value.setAdapter(adapter);
        Spinner to_value = findViewById(R.id.to_value);
        to_value.setAdapter(adapter);
    }

    /**
     * This method is called when the convert button is clicked.
     *
     * @param view The view that was clicked.
     */
    public void onConvertClick(View view) {
        String from = exchangeRates2[((Spinner) findViewById(R.id.from_value)).getSelectedItemPosition()].getCurrencyName();
        String to = exchangeRates2[((Spinner) findViewById(R.id.to_value)).getSelectedItemPosition()].getCurrencyName();
        EditText number = findViewById(R.id.number_input);
        if (!checkInput(number.getText().toString())) {
            displayAlert(getString(R.string.no_number));
        } else {
            double amount = number.getText().toString().isEmpty() ? 0 : Double.parseDouble(number.getText().toString());
            double result = ExchangeRateDatabase.convert(amount, from, to);
            TextView result_value = findViewById(R.id.Converted);
            result_value.setText(String.format(getResources().getConfiguration().getLocales().get(0), "%1.2f", result));
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
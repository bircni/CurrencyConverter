package de.thu.currencyconverter;



import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //String[] exchangeRates = ExchangeRateDatabase.getCurrencies();
    ExchangeRate[] exchangeRates2 = new ExchangeRateDatabase().getExchangeRates();
    CurrencyListAdapter adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates2));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,
        //        android.R.layout.simple_spinner_item, exchangeRates );
        //adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        Spinner from_value = (Spinner) findViewById(R.id.from_value);
        from_value.setAdapter(adapter);
        Spinner to_value = (Spinner) findViewById(R.id.to_value);
        to_value.setAdapter(adapter);
    }

    public void onConvertClick(View view) {
        //Spinner from_value = (Spinner) findViewById(R.id.from_value);
        //Spinner to_value = (Spinner) findViewById(R.id.to_value);
        String from = exchangeRates2[((Spinner) findViewById(R.id.from_value)).getSelectedItemPosition()].getCurrencyName();
        String to = exchangeRates2[((Spinner) findViewById(R.id.to_value)).getSelectedItemPosition()].getCurrencyName();
        EditText number = (EditText) findViewById(R.id.number_input);
        double amount = Double.parseDouble(number.getText().toString());
        //Log.d("amout", String.valueOf(amount));
        double result = ExchangeRateDatabase.convert(amount, from, to);
        //double result = amount;
        TextView result_value = (TextView) findViewById(R.id.Converted);
        //result_value.setText(String.valueOf(result).substring(0,7));
        result_value.setText(String.format(getResources().getConfiguration().getLocales().get(0), "%1.2f", result));
        //.substring(0,7)
    }

}
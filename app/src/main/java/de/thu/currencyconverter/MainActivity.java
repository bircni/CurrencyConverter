package de.thu.currencyconverter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import io.paperdb.Paper;

/**
 * Main activity of the app.
 */
public class MainActivity extends AppCompatActivity {

    ExchangeRate[] exchangeRates; // = new ExchangeRateDatabase().getExchangeRates();
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
        exchangeRates = Paper.book().read("Database");
        updateCurrencies();
        //NEW
        assert exchangeRates != null;
        adapter = new CurrencyListAdapter(Arrays.asList(exchangeRates));
        Spinner from_value = findViewById(R.id.from_value);
        from_value.setAdapter(adapter);
        Spinner to_value = findViewById(R.id.to_value);
        to_value.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     *
     * @param menu The options menu in which you place your items.
     *
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    //TODO: RestrictedApi solve
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        if(menu instanceof MenuBuilder) {  //To display icon on overflow menu
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        setShareText(null);
        return true;
    }

    /**
     * This method sets the text to be shared.
     *
     * @param text The text to share
     */
    private void setShareText(String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        if (text != null && !text.isEmpty()) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        }
        shareActionProvider.setShareIntent(shareIntent);
    }

    /**
     * This method is called when the user clicks on a menu item.
     *
     * @param item The menu item that was clicked
     */
    @SuppressLint("NonConstantResourceId")
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
            case R.id.reset_menu:
                Paper.book().write("Database", new ExchangeRateDatabase().getExchangeRates());
                Toast.makeText(this,getString(R.string.currency_reset), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.refresh_menu:
                updateCurrencies();
                return true;
                case R.id.about_menu:
                    startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method fetches the exchange rates from the internet.
     */
    private void updateCurrencies() {
        Thread thread = new Thread(() -> {
            try {
                String webString = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
                try {
                    URL url = new URL(webString);
                    URLConnection connection = url.openConnection();
                    XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                    parser.setInput(connection.getInputStream(), connection.getContentEncoding());
                    int eventType = parser.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("Cube")) {
                                String currency = parser.getAttributeValue(null, "currency");
                                String rate = parser.getAttributeValue(null, "rate");
                                if (currency != null && rate != null) {
                                    ExchangeRateDatabase.setRates(currency, rate);
                                }
                            }
                        }
                        eventType = parser.next();
                    }
                } catch (Exception e) {
                    Log.e("ErrorURL", "Error with XML: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if(hasInternetConnection(this)) {
            Toast.makeText(this, getString(R.string.currency_update), Toast.LENGTH_SHORT).show();
            thread.start();
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method checks if the device has an internet connection.
     *
     * @param context The context of the application
     * @return true if the device has an internet connection, false otherwise
     */
    public static boolean hasInternetConnection(final Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager)context.
                getSystemService(Context.CONNECTIVITY_SERVICE);

        final Network network = connectivityManager.getActiveNetwork();
        final NetworkCapabilities capabilities = connectivityManager
                .getNetworkCapabilities(network);

        return capabilities != null
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }

    /**
     * This method is called when the convert button is clicked.
     *
     * @param view The view that was clicked.
     */
    public void onConvertClick(View view) {
        adapter.notifyDataSetChanged();
        //ExchangeRate [] convRate = Paper.book().read("Database");
        Spinner from_value = findViewById(R.id.from_value);
        Spinner to_value = findViewById(R.id.to_value);
        String from = exchangeRates[from_value.getSelectedItemPosition()].getCurrencyName();
        int fromInt = from_value.getSelectedItemPosition();
        int toInt = to_value.getSelectedItemPosition();
        String to = exchangeRates[to_value.getSelectedItemPosition()].getCurrencyName();
        EditText number = findViewById(R.id.number_input);
        if (!checkInput(number.getText().toString())) {
            Toast.makeText(this, getString(R.string.no_number), Toast.LENGTH_LONG).show();
            //displayAlert(getString(R.string.no_number));
        } else {
            double amount = number.getText().toString().isEmpty() ? 0 : Double.parseDouble(number.getText().toString());
            double result = ExchangeRateDatabase.convertPaper(amount, fromInt, toInt);
            TextView result_value = findViewById(R.id.Converted);
            String resultF = String.format(getResources().getConfiguration().getLocales().get(0), "%1.2f", result);
            String amountF = String.format(getResources().getConfiguration().getLocales().get(0), "%1.2f", amount);
            result_value.setText(resultF);
            String share = String.format(getString(R.string.share_text),amountF,from,to,resultF);
            setShareText(share);
        }
    }
}
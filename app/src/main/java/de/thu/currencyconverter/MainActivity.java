package de.thu.currencyconverter;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
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
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

/**
 * Main activity of the app.
 */
public class MainActivity extends AppCompatActivity {

    ExchangeRate[] exchangeRates;
    CurrencyListAdapter adapter;
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
        silentUpdate();
        scheduleUpdate();
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
     * This method checks if the device has an internet connection.
     *
     * @param context The context of the application
     * @return true if the device has an internet connection, false otherwise
     */
    public static boolean hasInternetConnection(final Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final Network network = connectivityManager.getActiveNetwork();
        final NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        return capabilities != null
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
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
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    //TODO: RestrictedApi solve
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        setShareText(null);
        return true;
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
                Toast.makeText(this, getString(R.string.currency_reset), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.refresh_menu:
                silentUpdate();
                return true;
            case R.id.about_menu:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void scheduleUpdate() {
        WorkManager workManager = WorkManager.getInstance(this);
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(false)
                .build();
        PeriodicWorkRequest periodicCounterRequest =
                new PeriodicWorkRequest.Builder(ExchangeRateUpdateWorker.class, 24, TimeUnit.HOURS)
                        .setConstraints(constraints)
                        .addTag("periodicUpdateTag")
                        .build();
        workManager.enqueueUniquePeriodicWork("ExchangeRateUpdateWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicCounterRequest);
    }


    private boolean isForeground() {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        return (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND ||
                appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE);
    }

    /**
     * updates the currencies in the background
     */
    private void silentUpdate() {
        if (hasInternetConnection(this)) {
            if (isForeground())
                Toast.makeText(this, getString(R.string.currency_update), Toast.LENGTH_SHORT).show();
            WorkRequest countWorkRequest =
                    new OneTimeWorkRequest.Builder(ExchangeRateUpdateWorker.class).build();
            WorkManager.getInstance(this).enqueue(countWorkRequest);
        } else {
            if (isForeground())
                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the convert button is clicked.
     *
     * @param view The view that was clicked.
     */
    public void onConvertClick(View view) {
        adapter.notifyDataSetChanged();
        Spinner from_value = findViewById(R.id.from_value);
        Spinner to_value = findViewById(R.id.to_value);
        String from = exchangeRates[from_value.getSelectedItemPosition()].getCurrencyName();
        int fromInt = from_value.getSelectedItemPosition();
        int toInt = to_value.getSelectedItemPosition();
        String to = exchangeRates[to_value.getSelectedItemPosition()].getCurrencyName();
        EditText number = findViewById(R.id.number_input);
        if (!checkInput(number.getText().toString())) {
            Toast.makeText(this, getString(R.string.no_number), Toast.LENGTH_LONG).show();
        } else {
            double amount = number.getText().toString().isEmpty() ? 0 : Double.parseDouble(number.getText().toString());
            double result = ExchangeRateDatabase.convertPaper(amount, fromInt, toInt);
            TextView result_value = findViewById(R.id.Converted);
            String resultF = String.format(getResources().getConfiguration().getLocales().get(0), "%1.2f", result);
            String amountF = String.format(getResources().getConfiguration().getLocales().get(0), "%1.2f", amount);
            result_value.setText(resultF);
            String share = String.format(getString(R.string.share_text), amountF, from, to, resultF);
            setShareText(share);
        }
    }
}
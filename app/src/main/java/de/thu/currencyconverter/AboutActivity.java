package de.thu.currencyconverter;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * This activity is show the about information.
 */
public class AboutActivity extends AppCompatActivity {

    String versionName = "0.5";
    String actionBar = "About";

    /**
     * This method is called when the activity is created.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        TextView VN = findViewById(R.id.versionName);
        VN.setText(String.format(getString(R.string.version), versionName));
        TextView aboutText = findViewById(R.id.aboutText);
        aboutText.setText(getString(R.string.about_text));
        Toolbar toolbar = findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * This method is called when the user clicks the back button.
     *
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
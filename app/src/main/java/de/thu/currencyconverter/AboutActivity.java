package de.thu.currencyconverter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * This activity is show the about information.
 */
public class AboutActivity extends AppCompatActivity {

    final String versionName = BuildConfig.VERSION_NAME;
    final String actionBar = "About";

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
        String website = "https://www.thu.de";
        ImageButton socialsB = findViewById(R.id.socials_button);
        socialsB.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
            startActivity(intent);
        });
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
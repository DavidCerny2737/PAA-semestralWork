package cz.tul.nutritiontracker.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

import cz.tul.nutritiontracker.R;

public class SettingActivity extends MainActivity {

    public static final String LOCALE_KEY = "locale";
    public static final String ENGLISH = "en";
    public static final String CZECH = "cs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.activity_settings_detail_title);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.menu);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String locale = prefs.getString(LOCALE_KEY, "en");

        SwitchCompat languageSwitch = (SwitchCompat) findViewById(R.id.languageSwitch);

        if(locale.equals(ENGLISH)){
            languageSwitch.setChecked(false);
            languageSwitch.setText(getString(R.string.settings_localization_english_label));
        } else {
            languageSwitch.setChecked(true);
            languageSwitch.setText(getString(R.string.settings_localization_czech_label));
        }

        languageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Locale locale;
                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove(LOCALE_KEY);

                if(isChecked) {
                    locale = new Locale("cs", "CZ");
                    editor.putString(LOCALE_KEY, CZECH);
                }
                else {
                    locale = Locale.ENGLISH;
                    editor.putString(LOCALE_KEY, ENGLISH);
                }
                editor.apply();

                Locale.setDefault(locale);
                Configuration config = new Configuration();
                if (Build.VERSION.SDK_INT >= 24) {
                    config.setLocale(locale);
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                } else {
                    config.locale = locale;
                    getBaseContext().getApplicationContext().createConfigurationContext(config);
                }

                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

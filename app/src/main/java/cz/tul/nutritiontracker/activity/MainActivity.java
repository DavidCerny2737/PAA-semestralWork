package cz.tul.nutritiontracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

import cz.tul.nutritiontracker.R;

/**
 * Main activity, responsible for menu Toolbar, default actions and application state restore.
 */
public class MainActivity extends AppCompatActivity {

    public static boolean localeSet = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.activity_main_title);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.menu);
        setSupportActionBar(toolbar);

        // locale
        Locale locale = null;
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String language = prefs.getString(SettingActivity.LOCALE_KEY, SettingActivity.CZECH);
        if(language.equals(SettingActivity.CZECH)){
            locale = new Locale("cs", "CZ");
        } else if(language.equals(SettingActivity.ENGLISH)){
            locale = Locale.ENGLISH;
        }

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= 24) {
            config.setLocale(locale);
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        } else {
            config.locale = locale;
            getBaseContext().getApplicationContext().createConfigurationContext(config);
        }

        if(!localeSet) {
            localeSet = true;
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.menu_search_query_hint_label));

        final Activity actualActivity = this;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null && !query.equals("")){
                    Intent searchIntent = new Intent(actualActivity, ItemListActivity.class);
                    searchIntent.putExtra(getString(R.string.menu_search_query), query);
                    startActivity(searchIntent);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // User chose the "Search" item, expand search view
                return true;
            case R.id.action_history:
                // User chose the "History" action, show history activity
                Intent diaryIntent = new Intent(this, DiaryActivity.class);
                startActivity(diaryIntent);
                return true;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}

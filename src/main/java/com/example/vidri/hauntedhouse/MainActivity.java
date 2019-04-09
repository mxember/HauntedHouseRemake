package com.example.vidri.hauntedhouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Set;


public class MainActivity extends AppCompatActivity {

    public static final String DIFFICULTY_MODE = "pref_difficulty_mode";
    public boolean hardMode = false;
    private boolean preferencesChanged = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        //Change listener to remember preferences
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


    }


    @Override
    protected void onStart() {
        super.onStart();

        if(preferencesChanged) {
            MainActivityFragment splashFragment = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.splashFragment);

            splashFragment.resetGame();
            preferencesChanged = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent preferencesIntent = new Intent(this, SettingsActivity.class);
        startActivity(preferencesIntent);

        return super.onOptionsItemSelected(item);
    }

    private OnSharedPreferenceChangeListener preferenceChangeListener =
            new OnSharedPreferenceChangeListener() {
                // called when the user changes the app's preferences
                @Override
                public void onSharedPreferenceChanged(
                        SharedPreferences sharedPreferences, String key) {
                    preferencesChanged = true; // user changed app setting

                    MainActivityFragment gameFragment = (MainActivityFragment)
                            getSupportFragmentManager().findFragmentById(
                                    R.id.gameFragment);

                    //gameFragment.updateDifficulty(sharedPreferences);

                    //gameFragment.resetGame();


                    //Let user know preferences were changed and resetting the game
                    Toast.makeText(MainActivity.this,
                            R.string.restarting_game,
                            Toast.LENGTH_SHORT).show();
                }
            };





}

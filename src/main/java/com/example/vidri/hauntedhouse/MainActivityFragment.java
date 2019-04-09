package com.example.vidri.hauntedhouse;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Main Activity Fragment to hold splash screen with buttons
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = "Haunted House";

    private LinearLayout splashScreenLinearLayout;          //Linear Layout of main screen
    private LinearLayout splashScreenLinearLayout2;         //Linear Layout of main screen 2
    private Button playGameButton;                          //Button to play game
    private Button highScoreButton;                         //Button to go to high score screen
    private GameActivityFragment gameFragment;              //Game Fragment to load game
    private HighScoreActivityFragment highScoreFragment;    //High Score fragment to hold high scores
    private Button preferencesButton;                       //Button to go to preferences screen
    private SettingsActivityFragment preferenceFragment;    //Preference Fragment
    private MainActivityFragment splashFragment;            //Main Activity Fragment to return to

    private static Context context;



    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        this.context = getApplicationContext();

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Set up layouts and buttons
        splashScreenLinearLayout = (LinearLayout) view.findViewById(R.id.splashScreenLinearLayout);
        splashScreenLinearLayout2 = (LinearLayout) view.findViewById(R.id.splashScreenLinearLayout2);
        playGameButton = (Button) view.findViewById(R.id.playGameButton);
        highScoreButton = (Button) view.findViewById(R.id.highScoreButton);
        preferencesButton = (Button) view.findViewById(R.id.preferencesButton);

        //Set onclick listener for each button
        playGameButton.setOnClickListener(playGameButtonListener);
        highScoreButton.setOnClickListener(highScoreButtonListener);
        preferencesButton.setOnClickListener(preferencesButtonListener);


        return view;
    }

    public static Context getApplicationContext () {
        return context;
    }

    public void updateDifficulty(SharedPreferences sharedPreferences) {

        //Needs to be able to change difficulty setting

    }

    //To reload Main Fragment to go back to game
    public void resetGame(){

        splashFragment = new MainActivityFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, splashFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.attach(splashFragment).commit();
    }

    private View.OnClickListener playGameButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Fragment manager to load Game Fragment
            gameFragment = new GameActivityFragment();

            //Load fragment to play game
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, gameFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.attach(gameFragment).commit();
        }
    };


    private View.OnClickListener highScoreButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Fragment manager to load High Score Fragment
            highScoreFragment = new HighScoreActivityFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, highScoreFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.attach(highScoreFragment).commit();


        }
    };

    private View.OnClickListener preferencesButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Fragment manager to load Preference Fragment
            preferenceFragment = new SettingsActivityFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, preferenceFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.attach(preferenceFragment).commit();


        }
    };





}

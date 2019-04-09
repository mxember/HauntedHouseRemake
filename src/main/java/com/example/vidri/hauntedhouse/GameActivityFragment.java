package com.example.vidri.hauntedhouse;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Game Activity Fragment.
 */
public class GameActivityFragment extends Fragment {


    public GameActivityFragment() {
    }

    private GameView gameView;                  //The game screen




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
       View view = inflater.inflate(R.layout.fragment_game, container, false);


       //Create new game screen
        gameView = new GameView(getContext());


        return gameView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}

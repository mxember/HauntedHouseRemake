package com.example.vidri.hauntedhouse;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * High Score Fragment.
 */
public class HighScoreActivityFragment extends Fragment {

    private TextView easyScoreTV;               //Easy mode high score
    private TextView hardScoreTV;               //Hard mode high score



    public HighScoreActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
     }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_high_score, container, false);

        //Create TextViews for holding high scores
        easyScoreTV = (TextView) view.findViewById(R.id.easyScore1);
        hardScoreTV = (TextView) view.findViewById(R.id.hardScore1);

        //Get scores from SharedPreferences to display
        SharedPreferences easyScore = getContext().getSharedPreferences("EasyModeScore", Context.MODE_PRIVATE);
        int easyScore1 = easyScore.getInt("easy_score", 0);


        SharedPreferences hardScore = getContext().getSharedPreferences("HardMoreScore", Context.MODE_PRIVATE);
        int hardScore1 = hardScore.getInt("hard_score", 0);


        //Set scores in TextView
       easyScoreTV.setText(Integer.toString(easyScore1));

       hardScoreTV.setText(Integer.toString(hardScore1));


        return view;
    }
}

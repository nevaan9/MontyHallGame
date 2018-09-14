package com.example.nevaanperera.montyhallgame;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {


    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        // Get the doors
        ImageButton door1 = rootView.findViewById(R.id.door1);
        ImageButton door2 = rootView.findViewById(R.id.door2);
        ImageButton door3 = rootView.findViewById(R.id.door3);

        door1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hello! I just got clicke!");
            }
        });

        return rootView;
    }

}

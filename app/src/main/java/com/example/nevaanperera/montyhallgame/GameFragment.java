package com.example.nevaanperera.montyhallgame;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    ImageButton door1 = null;
    ImageButton door2 = null;
    ImageButton door3 = null;
    private boolean initialDoorPressed = false;
    private int prize_door;


    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        // Get the doors
        door1 = rootView.findViewById(R.id.door1);
        door2 = rootView.findViewById(R.id.door2);
        door3 = rootView.findViewById(R.id.door3);

        // Initialize the prize door
        prize_door = (int) (Math.random() * 3 + 1);
        System.out.println(prize_door);

        // Get the header
        final TextView game_activity_heaer = rootView.findViewById(R.id.game_activity_header);

        // Set onClick Listerner for door1
        door1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!initialDoorPressed) {
                    door1.setImageLevel(1);
                    doorPressed();
                    initialDoorPressed = true;
                    System.out.println(prize_door);
                }
            }
        });

        // Set onClick Listerner for door2
        door2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!initialDoorPressed){
                    door2.setImageLevel(1);
                    doorPressed();
                    initialDoorPressed = true;
                    System.out.println(prize_door);
                }
            }
        });

        // Set onClick Listerner for door1
        door3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!initialDoorPressed) {
                    door3.setImageLevel(1);
                    doorPressed();
                    initialDoorPressed = true;
                    System.out.println(prize_door);
                }
            }
        });

        return rootView;
    }

    public void doorPressed () {
        // Do something
    }

}

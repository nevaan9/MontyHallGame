package com.example.nevaanperera.montyhallgame;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    ImageButton door1 = null;
    ImageButton door2 = null;
    ImageButton door3 = null;
    private boolean initialDoorPressed = false;
    private int prize_door;
    private int choosen_door;
    private String[] doorIDs = new String[3];


    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        // Initialize the doorID's
        doorIDs[0] = "door1";
        doorIDs[1] = "door2";
        doorIDs[2] = "door3";

        // Get the doors
        door1 = rootView.findViewById(R.id.door1);
        door2 = rootView.findViewById(R.id.door2);
        door3 = rootView.findViewById(R.id.door3);

        // Initialize the prize door
        prize_door = (int) (Math.random() * 3 + 1);

        // Get the header
        final TextView game_activity_heaer = rootView.findViewById(R.id.game_activity_header);

        // Set onClick Listerner for door1
        door1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!initialDoorPressed) {
                    door1.setImageLevel(1);
                    choosen_door = 1;
                    showGoat();
                    initialDoorPressed = true;
                }
            }
        });

        // Set onClick Listerner for door2
        door2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!initialDoorPressed){
                    door2.setImageLevel(1);
                    choosen_door = 2;
                    showGoat();
                    initialDoorPressed = true;
                }
            }
        });

        // Set onClick Listerner for door1
        door3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!initialDoorPressed) {
                    door3.setImageLevel(1);
                    choosen_door = 3;
                    showGoat();
                    initialDoorPressed = true;
                }
            }
        });

        return rootView;
    }

    // Initialize the listener

    public void doorPressed () {
        // Do something

    }

    public void showGoat () {
        Set<Integer> myHashSet = new HashSet<>();
        int randomDoor = 0;

        // Insert the doors (into the set) that is not chooses and is not the prize door
        for (String anID : doorIDs) {
            if (!anID.equals("door" + prize_door) && !anID.equals("door" + choosen_door)){
                myHashSet.add(Integer.parseInt(anID.replaceAll("\\D+","")));
            }
        }

        // get a random door
        int size = myHashSet.size();
        int item = (int) (Math.random() * size + 1);
        int i = 1;
        for(int aGoatDoor : myHashSet)
        {
            if (i == item) {
                randomDoor = aGoatDoor;
                myHashSet.remove(aGoatDoor);
                break;
            }
            i++;
        }

        // Set the goat image
        switch (randomDoor) {
            case 1:
                door1.setImageLevel(5);
                break;
            case 2:
                door2.setImageLevel(5);
                break;
            case 3:
                door3.setImageLevel(5);
                break;
        }

        // Set the Stay Image
        if (choosen_door == prize_door) {
            switch (choosen_door) {
                case 1:
                    door1.setImageLevel(3);
                    break;
                case 2:
                    door2.setImageLevel(3);
                    break;
                case 3:
                    door3.setImageLevel(3);
                    break;
            }

            // Find  the remaining door
            int remainingDoor = 0;
            for (int r = 1; r < 4; r++){
                if (r != choosen_door && r != randomDoor) {
                    remainingDoor = r;
                }
            }

            // Change the remaining door to switch
            switch (remainingDoor) {
                case 1:
                    door1.setImageLevel(2);
                    break;
                case 2:
                    door2.setImageLevel(2);
                    break;
                case 3:
                    door3.setImageLevel(2);
                    break;
            }

        } else {
            // If we are in this else statement, it means the choosen door and prize door are different
            switch (choosen_door) {
                case 1:
                    door1.setImageLevel(3);
                    break;
                case 2:
                    door2.setImageLevel(3);
                    break;
                case 3:
                    door3.setImageLevel(3);
                    break;
            }

            switch (prize_door) {
                case 1:
                    door1.setImageLevel(2);
                    break;
                case 2:
                    door2.setImageLevel(2);
                    break;
                case 3:
                    door3.setImageLevel(2);
                    break;
            }

        }
    }

}

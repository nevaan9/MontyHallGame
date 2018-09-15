package com.example.nevaanperera.montyhallgame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private ImageButton door1 = null;
    private ImageButton door2 = null;
    private ImageButton door3 = null;

    private boolean door1Pressed = false;
    private boolean door2Pressed = false;
    private boolean door3Pressed = false;

    private int firstGoatDoor = 0;
    private int secondGoatDoor = 0;

    private boolean doorChoosen = false;

    private int prize_door;
    private int choosen_door;
    private String[] doorIDs = new String[3];
    private ImageButton[] doorImages = new ImageButton[3];


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

        // Get the doors and add them to the array
        door1 = rootView.findViewById(R.id.door1);
        doorImages[0] = door1;

        door2 = rootView.findViewById(R.id.door2);
        doorImages[1] = door2;

        door3 = rootView.findViewById(R.id.door3);
        doorImages[2] = door3;


        // Initialize the prize door
        prize_door = (int) (Math.random() * 3 + 1);

        // Get the header
        final TextView game_activity_heaer = rootView.findViewById(R.id.game_activity_header);

        // Set onClick Listerner for door1
        door1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!door1Pressed) {
                    if (doorChoosen) {
                        revealAnswers(1);
                    } else {
                        door1.setImageLevel(1);
                        choosen_door = 1;
                        revealDoors();
                    }
                }
            }
        });

        // Set onClick Listerner for door2
        door2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!door2Pressed) {
                    if (doorChoosen) {
                        revealAnswers(2);
                    } else {
                        door2.setImageLevel(1);
                        choosen_door = 2;
                        revealDoors();
                    }
                }
            }
        });

        // Set onClick Listerner for door1
        door3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!door3Pressed) {
                    if (doorChoosen) {
                        revealAnswers(3);
                    } else {
                        door3.setImageLevel(1);
                        choosen_door = 3;
                        revealDoors();
                    }
                }
            }
        });

        return rootView;
    }

    // Initialize the listener

    public void doorPressed () {
        // Do something

    }

    public void revealDoors () {
        Set<Integer> myHashSet = new HashSet<>();
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
                firstGoatDoor = aGoatDoor;
                myHashSet.remove(aGoatDoor);
                break;
            }
            i++;
        }

        // Set the goat image
        switch (firstGoatDoor) {
            case 1:
                door1.setImageLevel(5);
                door1Pressed = true;
                doorChoosen = true;
                break;
            case 2:
                door2.setImageLevel(5);
                door2Pressed = true;
                doorChoosen = true;
                break;
            case 3:
                door3.setImageLevel(5);
                door3Pressed = true;
                doorChoosen = true;
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
            for (int r = 1; r < 4; r++){
                if (r != choosen_door && r != firstGoatDoor) {
                    secondGoatDoor = r;
                }
            }

            // Change the remaining door to switch
            switch (secondGoatDoor) {
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

    public void revealAnswers (int pressedButton) {
        for (int i = 1; i < 4; i++) {
            if (i == prize_door) {
                doorImages[i -1].setImageLevel(4);
            } else {
                doorImages[i -1].setImageLevel(5);
            }
        }

        if (pressedButton == prize_door) {
            System.out.println("YOU WON!");
        } else {
            System.out.println("YOU LOST!");
        }
    }

}

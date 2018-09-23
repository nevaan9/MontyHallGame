package com.example.nevaanperera.montyhallgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private ImageButton door1 = null;
    private ImageButton door2 = null;
    private ImageButton door3 = null;

    private TextView header = null;
    private TextView switchWinsText = null;
    private TextView switchLossesText = null;
    private TextView stayTotal = null;
    private TextView stayWinsText = null;
    private TextView stayLossesText = null;
    private TextView switchTotal = null;

    private ViewGroup header_layout = null;

    private Button replayBtn = null;

    private boolean door1Pressed = false;
    private boolean door2Pressed = false;
    private boolean door3Pressed = false;

    private int firstGoatDoor = 0;
    private int secondGoatDoor = 0;

    private double switchWins = 0;
    private double switchLosses = 0;
    private double stayWins = 0;
    private double stayLosses = 0;

    private boolean doorChoosen = false;
    private int strategy = 0; // 1 is stay, 2 is switch, 0 is not initialized

    private int prize_door;
    private int choosen_door;
    private String[] doorIDs = new String[3];
    private ImageButton[] doorImages = new ImageButton[3];

    // Media player
    MediaPlayer goat_sound = null;
    MediaPlayer win1_sound = null;
    MediaPlayer win2_sound = null;
    MediaPlayer loss1_sound = null;
    MediaPlayer loss2_sound = null;

    private ArrayList<MediaPlayer> winSound_arrayList;
    private ArrayList<MediaPlayer> lossSound_arrayList;

    // Variable to keep track of Game state
    private int gameState;


    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Game state
        gameState = 0;

        // Initialize the doorID's
        doorIDs[0] = "door1";
        doorIDs[1] = "door2";
        doorIDs[2] = "door3";

        // Initialize the media player sounds
        goat_sound = MediaPlayer.create(getContext(), R.raw.goat);
        win1_sound = MediaPlayer.create(getContext(), R.raw.win1);
        win2_sound = MediaPlayer.create(getContext(), R.raw.win2);
        loss1_sound = MediaPlayer.create(getContext(), R.raw.loss1);
        loss2_sound = MediaPlayer.create(getContext(), R.raw.loss2);

        // Initialize the arrayLists
        winSound_arrayList = new ArrayList<>();
        winSound_arrayList.add(win1_sound);
        winSound_arrayList.add(win2_sound);

        lossSound_arrayList = new ArrayList<>();
        lossSound_arrayList.add(loss1_sound);
        lossSound_arrayList.add(loss2_sound);


        // Make the replay button
        replayBtn = new Button(getContext());
        replayBtn.setText("Yes");
        replayBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Initialize the prize door
        prize_door = (int) (Math.random() * 3 + 1);

        // This will not destroy the instance data on rotation
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        // Get the doors and add them to the array
        door1 = rootView.findViewById(R.id.door1);
        doorImages[0] = door1;

        door2 = rootView.findViewById(R.id.door2);
        doorImages[1] = door2;

        door3 = rootView.findViewById(R.id.door3);
        doorImages[2] = door3;

        switchWinsText = rootView.findViewById(R.id.switch_wins);
        switchLossesText = rootView.findViewById(R.id.switch_losses);
        stayWinsText = rootView.findViewById(R.id.stay_wins);
        stayLossesText = rootView.findViewById(R.id.stay_losses);
        stayTotal = rootView.findViewById(R.id.stay_total);
        switchTotal = rootView.findViewById(R.id.switch_total);

        // Get the header
        header = rootView.findViewById(R.id.game_activity_header);

        // Get the header layout
        header_layout = rootView.findViewById(R.id.header_layout);

        // Initialize the animation
        final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);

        // Set onClick Listerner for door1
        door1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!door1Pressed) {
                    if (doorChoosen) {
                        strategy = (choosen_door == 1) ? 1 : 2;
                        revealAnswers(1);
                    } else {
                        door1.setImageLevel(1);
                        door1.startAnimation(animShake);
                        goat_sound.start();
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
                        strategy = (choosen_door == 2) ? 1 : 2;
                        revealAnswers(2);
                    } else {
                        door2.setImageLevel(1);
                        door2.startAnimation(animShake);
                        goat_sound.start();
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
                        strategy = (choosen_door == 3) ? 1 : 2;
                        revealAnswers(3);
                    } else {
                        door3.setImageLevel(1);
                        door3.startAnimation(animShake);
                        goat_sound.start();
                        choosen_door = 3;
                        revealDoors();
                    }
                }
            }
        });

        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


        // This means the continue button was clicked
            if (!(getActivity().getSharedPreferences(MainFragment.PREF_NAME, Context.MODE_PRIVATE).getBoolean(MainFragment.NEW_CLICKED, true)) && getActivity().getSharedPreferences(MainFragment.PREF_NAME, Context.MODE_PRIVATE).getBoolean("backPressed", false)) {
                SharedPreferences pref_ed = getActivity().getSharedPreferences(MainFragment.PREF_NAME, Context.MODE_PRIVATE);
                switchWins = pref_ed.getFloat("switchWins", 0);
                switchLosses = pref_ed.getFloat("switchLosses", 0);
                stayWins = pref_ed.getFloat("stayWins", 0);
                door1Pressed = pref_ed.getBoolean("door1Pressed", false);
                door2Pressed = pref_ed.getBoolean("door2Pressed", false);
                door3Pressed = pref_ed.getBoolean("door3Pressed", false);
                firstGoatDoor = pref_ed.getInt("firstGoatDoor", 0);
                secondGoatDoor = pref_ed.getInt("secondGoatDoor", 0);
                doorChoosen = pref_ed.getBoolean("doorChoosen", false);
                strategy = pref_ed.getInt("strategy", 0);
                prize_door = pref_ed.getInt("prizeDoor", 0);
                choosen_door = pref_ed.getInt("choosenDoor", 0);
                gameState = pref_ed.getInt("gameState", 0);

                SharedPreferences.Editor editor = pref_ed.edit();
                editor.putBoolean("backPressed", false).apply();
        }

        header_layout.removeView(replayBtn);
        updateUI();
        updateWinLossBoard ();

        return rootView;
    }

    public void updateUI(){
        if (gameState == 1) {
            switch (choosen_door) {
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
            revealDoors();
        } else if (gameState == 2) {
            for (int i = 1; i < 4; i++) {
                if (i == prize_door) {
                    doorImages[i -1].setImageLevel(4);
                } else {
                    doorImages[i -1].setImageLevel(5);
                }
            }
            header.setText("Play Again?");

            if (replayBtn.getParent() != null) {
                ((ViewGroup)replayBtn.getParent()).removeView(replayBtn);
            }

            header_layout.addView(replayBtn);
        }
    }

    public void revealDoors () {
        // Now game state is 1
        gameState = 1;

        if (!doorChoosen) {
            Set<Integer> myHashSet = new HashSet<>();
            // Insert the doors (into the set) that is not chooses and is not the prize door
            for (String anID : doorIDs) {
                if (!anID.equals("door" + prize_door) && !anID.equals("door" + choosen_door)) {
                    myHashSet.add(Integer.parseInt(anID.replaceAll("\\D+", "")));
                }
            }

            // get a random door
            int size = myHashSet.size();
            int item = (int) (Math.random() * size + 1);
            int i = 1;
            for (int aGoatDoor : myHashSet) {
                if (i == item) {
                    firstGoatDoor = aGoatDoor;
                    myHashSet.remove(aGoatDoor);
                    break;
                }
                i++;
            }
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

        // Remove the header
        header.setText("");

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

    public void revealAnswers (final int  pressedButton) {
        // Now game state is 2
        gameState = 2;

        // Disable all the buttons
        door1Pressed = true;
        door2Pressed = true;
        door3Pressed = true;

        // Start a counter
        new CountDownTimer(3000, 1000) {

            private int count = 6;

            public void onTick(long millisUntilFinished) {
                switch (pressedButton) {
                    case 1:
                        door1.setImageLevel(count++);
                        break;
                    case 2:
                        door2.setImageLevel(count++);
                        break;
                    case 3:
                        door3.setImageLevel(count++);
                        break;
                }
            }

            public void onFinish() {

                String strategy_text = strategy == 1 ? "Stayed" : "Switched";

                if (pressedButton == prize_door) {
                    // Won
                    int randomSound = (int) (Math.random() * winSound_arrayList.size());
                    winSound_arrayList.get(randomSound).start();
                    if (strategy == 1) {
                        stayWins++;
                    } else {
                        switchWins++;
                    }

                    // Toast for winning
                    Toast toast = Toast.makeText(getContext(), "You " + strategy_text + " and Won!", Toast.LENGTH_SHORT);
                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        toast.setGravity(Gravity.BOTTOM, 0, 50);
                        toast.show();
                    } else {
                        toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 100, 50);
                        toast.show();
                    }
                } else {
                    // Lost
                    int randomSound = (int) (Math.random() * lossSound_arrayList.size());
                    lossSound_arrayList.get(randomSound).start();
                    if (strategy == 1) {
                        stayLosses++;
                    } else {
                        switchLosses++;
                    }

                    // Show a won / lost message
                    Toast toast = Toast.makeText(getContext(), "You " + strategy_text + " and Lost.", Toast.LENGTH_SHORT);
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        toast.setGravity(Gravity.BOTTOM, 0, 50);
                        toast.show();
                    } else {
                        toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 100, 50);
                        toast.show();
                    }
                }

                updateUI();
                updateWinLossBoard();
            }
        }.start();

    }

    public void resetGame() {
        // Game state is set to 0 again
        gameState = 0;

        // Reset the images
        for (int i = 1; i < 4; i++) {
            if (i == prize_door) {
                doorImages[i -1].setImageLevel(0);
            } else {
                doorImages[i -1].setImageLevel(0);
            }
        }

        door1Pressed = false;
        door2Pressed = false;
        door3Pressed = false;

        firstGoatDoor = 0;
        secondGoatDoor = 0;

        doorChoosen = false;
        strategy = 0; // 1 is stay, 2 is switch, 0 is not initialized

        prize_door = (int) (Math.random() * 3 + 1);
        choosen_door = 0;
        header.setText("Choose a door");
        header_layout.removeView(replayBtn);
    }

    public void updateWinLossBoard () {
        int switchTotalNum = (int) (switchWins + switchLosses);
        double switchWinPerc = (switchTotalNum > 0) ? (switchWins / switchTotalNum) : 0;
        double switchLossPerc = (switchTotalNum > 0) ? (switchLosses / switchTotalNum) : 0;
        String switchWinCell = (int) switchWins + " (" + (int)(switchWinPerc*100)+ "%)";
        String switchLossCell = (int) switchLosses + " (" + (int)(switchLossPerc*100) + "%)";

        switchWinsText.setText(switchWinCell);
        switchLossesText.setText(switchLossCell);
        switchTotal.setText(String.valueOf((int)(switchWins + switchLosses)));

        int stayTotalNum = (int) (stayWins + stayLosses);
        double stayWinPerc = (stayTotalNum > 0) ? (stayWins / stayTotalNum) : 0;
        double stayLossPerc = (stayTotalNum > 0) ? (stayLosses / stayTotalNum) : 0;
        String stayWinCell = (int) stayWins + " (" + (int)(stayWinPerc*100) + "%)";
        String stayLossCell = (int) stayLosses + " (" + (int)(stayLossPerc*100) + "%)";

        stayWinsText.setText(stayWinCell);
        stayLossesText.setText(stayLossCell);
        stayTotal.setText(String.valueOf((int)(stayWins + stayLosses)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor pref_ed = getActivity().getSharedPreferences(MainFragment.PREF_NAME, Context.MODE_PRIVATE).edit();
        pref_ed.putFloat("switchWins", (float) switchWins).apply();
        pref_ed.putFloat("switchLosses", (float) switchLosses).apply();
        pref_ed.putFloat("stayWins", (float) stayWins).apply();
        pref_ed.putBoolean("door1Pressed", door1Pressed).apply();
        pref_ed.putBoolean("door2Pressed", door2Pressed).apply();
        pref_ed.putBoolean("door3Pressed", door3Pressed).apply();
        pref_ed.putInt("firstGoatDoor", firstGoatDoor).apply();
        pref_ed.putInt("secondGoatDoor", secondGoatDoor).apply();
        pref_ed.putBoolean("doorChoosen", doorChoosen).apply();
        pref_ed.putInt("strategy", strategy).apply();
        pref_ed.putInt("prizeDoor", prize_door).apply();
        pref_ed.putInt("choosenDoor", choosen_door).apply();
        pref_ed.putInt("gameState", gameState).apply();
        pref_ed.putBoolean("backPressed", true).apply();
    }
}

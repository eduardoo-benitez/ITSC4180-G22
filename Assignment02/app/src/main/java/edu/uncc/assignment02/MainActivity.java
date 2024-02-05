package edu.uncc.assignment02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton case1, case2, case3, case4, case5, case6, case7, case8, case9, case10;
    TextView chooseFourCases;
    Button deal, noDeal, reset;
    ArrayList<ArrayList<Integer>> intListList;
    ArrayList<ImageButton> enabledButtons;
    int count = 10;
    int bank = 661461;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intListList = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(
                        R.drawable.suitcase_open_1,
                        R.id.oned,
                        R.drawable.reward_open_1,
                        1,
                        R.drawable.reward_1
                )),
                new ArrayList<>(Arrays.asList(
                        R.drawable.suitcase_open_10,
                        R.id.tend,
                        R.drawable.reward_open_10,
                        10,
                        R.drawable.reward_10
                )),
                new ArrayList<>(Arrays.asList(
                        R.drawable.suitcase_open_50,
                        R.id.fiftyd,
                        R.drawable.reward_open_50,
                        50,
                        R.drawable.reward_50
                )),
                new ArrayList<>(Arrays.asList(
                        R.drawable.suitcase_open_100,
                        R.id.onehundredd,
                        R.drawable.reward_open_100,
                        100,
                        R.drawable.reward_100
                )),
                new ArrayList<>(Arrays.asList(
                        R.drawable.suitcase_open_300,
                        R.id.threehundredd,
                        R.drawable.reward_open_300,
                        300,
                        R.drawable.reward_300
                )),
                new ArrayList<>(Arrays.asList(
                        R.drawable.suitcase_open_1000,
                        R.id.onethousandd,
                        R.drawable.reward_open_1000,
                        1000,
                        R.drawable.reward_1000
                )),
                new ArrayList<>(Arrays.asList(
                        R.drawable.suitcase_open_10000,
                        R.id.tenthousandd,
                        R.drawable.reward_open_10000,
                        10000,
                        R.drawable.reward_10000
                )),
                new ArrayList<>(Arrays.asList(
                        R.drawable.suitcase_open_50000,
                        R.id.fiftythousandd,
                        R.drawable.reward_open_50000,
                        50000,
                        R.drawable.reward_50000
                )),
                new ArrayList<>(Arrays.asList(
                        R.drawable.suitcase_open_100000,
                        R.id.onehundredthousandd,
                        R.drawable.reward_open_100000,
                        100000,
                        R.drawable.reward_100000
                )),
                new ArrayList<>(Arrays.asList(
                        R.drawable.suitcase_open_500000,
                        R.id.fivehundredthousandd,
                        R.drawable.reward_open_500000,
                        500000,
                        R.drawable.reward_500000
                ))
        ));

        Collections.shuffle(intListList);

        case1 = findViewById(R.id.case1);
        case2 = findViewById(R.id.case2);
        case3 = findViewById(R.id.case3);
        case4 = findViewById(R.id.case4);
        case5 = findViewById(R.id.case5);
        case6 = findViewById(R.id.case6);
        case7 = findViewById(R.id.case7);
        case8 = findViewById(R.id.case8);
        case9 = findViewById(R.id.case9);
        case10 = findViewById(R.id.case10);

        chooseFourCases = findViewById(R.id.chooseFourCases);

        deal = findViewById(R.id.deal);
        noDeal = findViewById(R.id.noDeal);
        reset = findViewById(R.id.reset);

        findViewById(R.id.deal).setOnClickListener(this);
        findViewById(R.id.noDeal).setOnClickListener(this);
        findViewById(R.id.reset).setOnClickListener(this);

        enabledButtons = new ArrayList<>(Arrays.asList(case1, case2, case3, case4, case5, case6, case7, case8, case9, case10));

        findViewById(R.id.case1).setOnClickListener(this);
        findViewById(R.id.case2).setOnClickListener(this);
        findViewById(R.id.case3).setOnClickListener(this);
        findViewById(R.id.case4).setOnClickListener(this);
        findViewById(R.id.case5).setOnClickListener(this);
        findViewById(R.id.case6).setOnClickListener(this);
        findViewById(R.id.case7).setOnClickListener(this);
        findViewById(R.id.case8).setOnClickListener(this);
        findViewById(R.id.case9).setOnClickListener(this);
        findViewById(R.id.case10).setOnClickListener(this);
    }

    private void enable(boolean enabled) {
        Log.d("AO2", String.valueOf(enabledButtons.size()));
        for (ImageButton button : enabledButtons) {
            button.setEnabled(enabled);
        }
    }
    private void update(ArrayList<Integer> selected) {
        ImageView rewardImageView = findViewById(selected.get(1));
        rewardImageView.setImageResource(selected.get(2));
        count--;
        bank = bank - selected.get(3);

        switch (count) {
            case 9:
                chooseFourCases.setText("Choose Three Cases");
                break;
            case 8:
                chooseFourCases.setText("Choose Two Cases");
                break;
            case 7:
                chooseFourCases.setText("Choose One Case");
                break;
            case 6:
                chooseFourCases.setText("Bank Deal is $" + (int) (.6 * (bank/count)));
                deal.setVisibility(View.VISIBLE);
                noDeal.setVisibility(View.VISIBLE);

                enable(false);

                findViewById(R.id.deal).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        chooseFourCases.setText("Congratulations! You won $" + (int) (.6 * (bank/count)));
                        deal.setVisibility(View.INVISIBLE);
                        noDeal.setVisibility(View.INVISIBLE);
                    }
                });
                findViewById(R.id.noDeal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseFourCases.setText("Choose Four Cases");
                        deal.setVisibility(View.INVISIBLE);
                        noDeal.setVisibility(View.INVISIBLE);
                        enable(true);
                    }
                });
                break;
            case 5:
                chooseFourCases.setText("Choose Four Cases");
                break;
            case 4:
                chooseFourCases.setText("Choose Three Cases");
                break;
            case 2:
                chooseFourCases.setText("Bank Deal is $" + (int) (.6 * (bank/count)));
                deal.setVisibility(View.VISIBLE);
                noDeal.setVisibility(View.VISIBLE);

                enable(false);

                findViewById(R.id.deal).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        chooseFourCases.setText("Congratulations! You won $" + (int) (.6 * (bank/count)));
                        deal.setVisibility(View.INVISIBLE);
                        noDeal.setVisibility(View.INVISIBLE);
                    }
                });
                findViewById(R.id.noDeal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseFourCases.setText("Choose One More Case!");
                        deal.setVisibility(View.INVISIBLE);
                        noDeal.setVisibility(View.INVISIBLE);
                        enable(true);
                    }
                });
                break;
            case 1:
                chooseFourCases.setText("Congratulations! You won $" + bank);
                enable(false);
                break;
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.case1) {
            ArrayList<Integer> selected = intListList.get(0);
            case1.setImageResource(selected.get(0));
            case1.setEnabled(false);
            enabledButtons.remove(case1);
            update(selected);
        }
        else if (v.getId() == R.id.case2) {
            ArrayList<Integer> selected = intListList.get(1);
            case2.setImageResource(selected.get(0));
            case2.setEnabled(false);
            enabledButtons.remove(case2);
            update(selected);
        }
        else if (v.getId() == R.id.case3) {
            ArrayList<Integer> selected = intListList.get(2);
            case3.setImageResource(selected.get(0));
            case3.setEnabled(false);
            enabledButtons.remove(case3);
            update(selected);
        }
        else if (v.getId() == R.id.case4) {
            ArrayList<Integer> selected = intListList.get(3);
            case4.setImageResource(selected.get(0));
            case4.setEnabled(false);
            enabledButtons.remove(case4);
            update(selected);
        }
        else if (v.getId() == R.id.case5) {
            ArrayList<Integer> selected = intListList.get(4);
            case5.setImageResource(selected.get(0));
            case5.setEnabled(false);
            enabledButtons.remove(case5);
            update(selected);
        }
        else if (v.getId() == R.id.case6) {
            ArrayList<Integer> selected = intListList.get(5);
            case6.setImageResource(selected.get(0));
            case6.setEnabled(false);
            enabledButtons.remove(case6);
            update(selected);
        }
        else if (v.getId() == R.id.case7) {
            ArrayList<Integer> selected = intListList.get(6);
            case7.setImageResource(selected.get(0));
            case7.setEnabled(false);
            enabledButtons.remove(case7);
            update(selected);
        }
        else if (v.getId() == R.id.case8) {
            ArrayList<Integer> selected = intListList.get(7);
            case8.setImageResource(selected.get(0));
            case8.setEnabled(false);
            enabledButtons.remove(case8);
            update(selected);
        }
        else if (v.getId() == R.id.case9) {
            ArrayList<Integer> selected = intListList.get(8);
            case9.setImageResource(selected.get(0));
            case9.setEnabled(false);
            enabledButtons.remove(case9);
            update(selected);
        }
        else if (v.getId() == R.id.case10) {
            ArrayList<Integer> selected = intListList.get(9);
            case10.setImageResource(selected.get(0));
            case10.setEnabled(false);
            enabledButtons.remove(case10);
            update(selected);
        }
        else if (v.getId() == R.id.reset) {
            count = 10;
            bank = 661461;

            deal.setVisibility(View.INVISIBLE);
            noDeal.setVisibility(View.INVISIBLE);

            case1.setImageResource(R.drawable.suitcase_position_1);
            case2.setImageResource(R.drawable.suitcase_position_2);
            case3.setImageResource(R.drawable.suitcase_position_3);
            case4.setImageResource(R.drawable.suitcase_position_4);
            case5.setImageResource(R.drawable.suitcase_position_5);
            case6.setImageResource(R.drawable.suitcase_position_6);
            case7.setImageResource(R.drawable.suitcase_position_7);
            case8.setImageResource(R.drawable.suitcase_position_8);
            case9.setImageResource(R.drawable.suitcase_position_9);
            case10.setImageResource(R.drawable.suitcase_position_10);

            enabledButtons = new ArrayList<>(Arrays.asList(case1, case2, case3, case4, case5, case6, case7, case8, case9, case10));
            enable(true);

            for (ArrayList<Integer> arr: intListList) {
                ImageView rewardImageView = findViewById(arr.get(1));
                rewardImageView.setImageResource(arr.get(4));
            }

            chooseFourCases.setText("Choose Four Cases");

            Collections.shuffle(intListList);
        }
    }
}
package edu.uncc.assignment01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.util.Log;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

//Assignment 01
//MainActivity.java
//Group 22: Angie Chang & Eduardo Benitez-Villegas

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //used for log messages, which are for debugging.
    final String TAG = "A01";
    //global variable to hold the discount percent. Must be initialized to 0.10 in case
    //the user leaves the default radio button selected and an event is never called to
    //set the discount percent.
    double discountPercent = 0.10;
    //flag that allows for custom discount percents to be used. must be global to be visible
    //to both functions it is referenced in.
    boolean customFlag = false;
    //declaration of components that are to be used in event handlers.
    //global for the sake of readability.
    SeekBar seekBar;
    TextView textViewProgress;
    TextView enterItemPrice;
    RadioGroup radioGroup;
    TextView finalPriceAmount;
    TextView discountAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-------PART 2A-------
        //components whose values are to be changed in event handlers.
        enterItemPrice = findViewById(R.id.enterItemPrice);
        radioGroup = findViewById(R.id.saleGroup);
        finalPriceAmount = findViewById(R.id.priceAmount);
        discountAmount = findViewById(R.id.discountAmount);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //flag is used to make sure that the custom percent is only used when custom is checked.
                if(checkedId == R.id.tenPercent) {
                    customFlag = false;
                    discountPercent = 0.10;
                }
                else if(checkedId == R.id.fifteenPercent) {
                    customFlag = false;
                    discountPercent = 0.15;
                }
                else if(checkedId == R.id.eighteenPercent) {
                    customFlag = false;
                    discountPercent = 0.18;
                }
                else if(checkedId == R.id.custom) {
                    customFlag = true;
                }
            }
        });

        //opens on click listeners for reset & calculate buttons.
        findViewById(R.id.reset).setOnClickListener(this);
        findViewById(R.id.calculate).setOnClickListener(this);
        //---------------------

        //-------PART 2B-------
        seekBar = findViewById(R.id.seekBar);
        textViewProgress = findViewById(R.id.progressPercent);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n") //suppress linting error when concatenating progress & "%".
            @Override
            public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                textViewProgress.setText(progress + "%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch");
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch");
            }
        });
        //---------------------
    }

    //-------PART 2A-------
    //suppress error linting error when passing "0.00" into setText & warning when rounding discount & final price.
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void onClick(View v) {
        double itemPrice = 0;
        if (v.getId() == R.id.reset) {
            enterItemPrice.setText("");
            radioGroup.check(R.id.tenPercent);
            seekBar.setProgress(25);
            discountAmount.setText("0.00");
            finalPriceAmount.setText("0.00");
        }
        else if (v.getId() == R.id.calculate) {
            try {
                itemPrice = Double.parseDouble(enterItemPrice.getText().toString()); //attempts to parse a double from textView,
            }                                                                        //catches anything that is not a double.
            catch (NumberFormatException ex) {
                Toast.makeText(this, "Invalid format, dummy. Enter Item Price as double values only.", Toast.LENGTH_LONG).show();
                enterItemPrice.setText("");
            }
            if (customFlag) {
                String percent = textViewProgress.getText().toString(); //extracts the string from textViewProgress.
                discountPercent = Double.parseDouble(percent.substring(0, percent.length() - 1)) / 100; //trims the % from the end of the string
            }                                                                                           //and converts to a double, divided by 100.
            double discount = itemPrice * discountPercent;
            double finalPrice = itemPrice - discount;
            discountAmount.setText(String.format("%.2f", discount)); //rounds a double and sets it to a string to be used in textView.
            finalPriceAmount.setText(String.format("%.2f", finalPrice)); //rounds a double and sets it to a string to be used in textView.
        }
    }
    //---------------------
}
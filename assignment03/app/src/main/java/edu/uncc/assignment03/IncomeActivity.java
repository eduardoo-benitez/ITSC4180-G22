package edu.uncc.assignment03;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class IncomeActivity extends AppCompatActivity{
    final static public String RESPONSE_KEY = "RESPONSE";
    String incomeStatus = "<$25k";
    Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_activity);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(DemographicActivity.RESPONSE_KEY)){
            response = (Response)getIntent().getSerializableExtra(DemographicActivity.RESPONSE_KEY);
        }

        SeekBar householdBar = findViewById(R.id.householdBar);
        TextView textViewProgress = findViewById(R.id.incomeDesc);

        householdBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0 ) {
                    textViewProgress.setText("<$25K");
                    incomeStatus = "<$25K";
                }
                else if (progress == 1) {
                    textViewProgress.setText("$25K to < $50K");
                    incomeStatus = "$25K to < $50K";
                }
                else if (progress == 2) {
                    textViewProgress.setText("$50K to < $100K");
                    incomeStatus = "$50K to < $100K";
                }
                else if (progress == 3) {
                    textViewProgress.setText("$100K to < $200K");
                    incomeStatus = "$100K to < $200K";
                }
                else if (progress == 4) {
                    textViewProgress.setText("<$200K");
                    incomeStatus = "<$200K";
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(RESPONSE_KEY, "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(RESPONSE_KEY, "onStopTrackingTouch");
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setIncomeStatus(incomeStatus);
                Intent intent = new Intent();
                intent.putExtra(RESPONSE_KEY, response);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}

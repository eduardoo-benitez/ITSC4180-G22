package edu.uncc.evaluation01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Intent;

public class SelectTipActivity extends AppCompatActivity implements View.OnClickListener {

    SeekBar seekBar;
    TextView textViewProgress;
    RadioGroup radioGroup;
    boolean customFlag = false;
    String percent = "10%";
    public static final String KEY_PERC = "PERC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tip);

        seekBar = findViewById(R.id.seekBar);
        textViewProgress = findViewById(R.id.textViewProgress);
        radioGroup = findViewById(R.id.radioGroup);

        findViewById(R.id.buttonSubmit).setOnClickListener(this);
        findViewById(R.id.buttonCancel).setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton10) {
                    customFlag = false;
                    percent = "10%";
                }
                else if(checkedId == R.id.radioButton15) {
                    customFlag = false;
                    percent = "15%";
                }
                else if(checkedId == R.id.radioButton18) {
                    customFlag = false;
                    percent = "18%";
                }
                else if(checkedId == R.id.radioButtonCustom) {
                    customFlag = true;
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewProgress.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    @SuppressLint("DefaultLocale")
    public void onClick(View v) {
        if (v.getId() == R.id.buttonCancel) {
            finish();
        }
        else if (v.getId() == R.id.buttonSubmit) {
            if (customFlag) {
                percent = textViewProgress.getText().toString();
            }
            Intent intent = new Intent();
            intent.putExtra(KEY_PERC, percent);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
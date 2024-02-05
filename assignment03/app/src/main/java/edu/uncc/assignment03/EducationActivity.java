package edu.uncc.assignment03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.content.Intent;
import android.widget.Toast;

public class EducationActivity extends AppCompatActivity {
    final static public String RESPONSE_KEY = "RESPONSE";
    String education = "";
    Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_activity);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(DemographicActivity.RESPONSE_KEY)) {
            response = (Response)getIntent().getSerializableExtra(DemographicActivity.RESPONSE_KEY);
        }

        RadioGroup educationGroup = findViewById(R.id.educationGroup);

        educationGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = findViewById(checkedId);
                if (checkedRadioButton != null) {
                    education = checkedRadioButton.getText().toString();
                }
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (education.equals("")) {
                    Toast.makeText(EducationActivity.this, "Select your highest degree or level of education.", Toast.LENGTH_LONG).show();
                }
                else {
                    response.setEducation(education);
                    Intent intent = new Intent();
                    intent.putExtra(RESPONSE_KEY, response);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
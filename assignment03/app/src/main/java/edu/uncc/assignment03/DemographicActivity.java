package edu.uncc.assignment03;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DemographicActivity extends AppCompatActivity {
    final static public String RESPONSE_KEY = "RESPONSE";
    Response response;
    TextView educationStatus, maritalStatusStatus, livingStatusStatus, incomeStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityResultLauncher<Intent> startNextActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getSerializableExtra(EducationActivity.RESPONSE_KEY) != null) {
                    response = (Response) result.getData().getSerializableExtra(EducationActivity.RESPONSE_KEY);
                    if (response != null) {
                        educationStatus.setText(response.getEducation());
                    }
                }
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getSerializableExtra(IncomeActivity.RESPONSE_KEY) != null) {
                    response = (Response) result.getData().getSerializableExtra(IncomeActivity.RESPONSE_KEY);
                    if (response != null) {
                        incomeStatus.setText(response.getIncomeStatus());
                    }
                }
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getSerializableExtra(MaritalActivity.RESPONSE_KEY) != null) {
                    response = (Response) result.getData().getSerializableExtra(MaritalActivity.RESPONSE_KEY);
                    if (response != null) {
                        maritalStatusStatus.setText(response.getMaritalStatus());
                    }
                }
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getSerializableExtra(LivingStatusActivity.RESPONSE_KEY) != null) {
                    response = (Response) result.getData().getSerializableExtra(LivingStatusActivity.RESPONSE_KEY);
                    if (response != null) {
                        livingStatusStatus.setText(response.getLivingStatus());
                    }
                }
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.demographic_activity);

        educationStatus = findViewById(R.id.educationStatus);
        maritalStatusStatus = findViewById(R.id.maritalStatusStatus);
        livingStatusStatus = findViewById(R.id.livingStatusStatus);
        incomeStatus = findViewById(R.id.incomeStatus);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(IdentificationActivity.RESPONSE_KEY)) {
            response = (Response)getIntent().getSerializableExtra(IdentificationActivity.RESPONSE_KEY);
        }

        findViewById(R.id.demoNextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemographicActivity.this, ProfileActivity.class);
                intent.putExtra(RESPONSE_KEY, response);
                startActivity(intent);
            }
        });

        findViewById(R.id.educationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemographicActivity.this, EducationActivity.class);
                intent.putExtra(RESPONSE_KEY, response);
                startNextActivityForResult.launch(intent);
            }
        });

        findViewById(R.id.maritalStatusButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemographicActivity.this, MaritalActivity.class);
                intent.putExtra(RESPONSE_KEY, response);
                startNextActivityForResult.launch(intent);
            }
        });

        findViewById(R.id.livingStatusButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemographicActivity.this, LivingStatusActivity.class);
                intent.putExtra(RESPONSE_KEY, response);
                startNextActivityForResult.launch(intent);
            }
        });

        findViewById(R.id.incomeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemographicActivity.this, IncomeActivity.class);
                intent.putExtra(RESPONSE_KEY, response);
                startNextActivityForResult.launch(intent);
            }
        });
    }
}
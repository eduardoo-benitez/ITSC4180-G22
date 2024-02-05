package edu.uncc.assignment03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    TextView nameResult, emailResult, roleResult, educationResult, maritalStatusResult, livingStatusResult, incomeStatusResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        nameResult = findViewById(R.id.nameResult);
        emailResult = findViewById(R.id.emailResult);
        roleResult = findViewById(R.id.roleResult);
        educationResult = findViewById(R.id.educationResult);
        maritalStatusResult = findViewById(R.id.maritalStatusResult);
        livingStatusResult = findViewById(R.id.livingStatusResult);
        incomeStatusResult = findViewById(R.id.incomeResult);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(DemographicActivity.RESPONSE_KEY)) {
            Response response = (Response)getIntent().getSerializableExtra(DemographicActivity.RESPONSE_KEY);
            nameResult.setText(response.getName());
            emailResult.setText(response.getEmail());
            roleResult.setText(response.getRole());
            educationResult.setText(response.getEducation());
            maritalStatusResult.setText(response.getMaritalStatus());
            livingStatusResult.setText(response.getLivingStatus());
            incomeStatusResult.setText(response.getIncomeStatus());
        }
    }
}
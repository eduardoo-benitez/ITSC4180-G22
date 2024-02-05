package edu.uncc.assignment03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class IdentificationActivity extends AppCompatActivity {
    final static public String RESPONSE_KEY = "RESPONSE";
    String name, email, role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identification_activity);

        TextView nameTextView = findViewById(R.id.enterName);
        TextView emailTextView = findViewById(R.id.enterEmail);
        RadioGroup roleGroup = findViewById(R.id.roleGroup);

        roleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = findViewById(checkedId);
                if (checkedRadioButton != null) {
                    role = checkedRadioButton.getText().toString();
                }
            }
        });

        findViewById(R.id.idNextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameTextView.getText().toString();
                email = emailTextView.getText().toString();
                if (name.equals("") || email.equals("") || role.equals("")) {
                    Toast.makeText(IdentificationActivity.this, "Missing input in one or more fields, try again.", Toast.LENGTH_LONG).show();
                }
                else {
                    Response response = new Response(name, email, role);
                    Intent intent = new Intent(IdentificationActivity.this, DemographicActivity.class);
                    intent.putExtra(RESPONSE_KEY, response);
                    startActivity(intent);
                }
            }
        });
    }
}
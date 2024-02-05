package edu.uncc.assignment03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.content.Intent;
import android.widget.Toast;

import java.security.PublicKey;

public class LivingStatusActivity extends AppCompatActivity{
    final static public String RESPONSE_KEY = "RESPONSE";
    String livingStatus = "";
    Response response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.living_activity);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(DemographicActivity.RESPONSE_KEY)){
           response = (Response)getIntent().getSerializableExtra(DemographicActivity.RESPONSE_KEY);
        }

        RadioGroup livingGroup = findViewById(R.id.livingGroup);

        livingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                RadioButton checkedRadioButton = findViewById(checkedId);
                if (checkedRadioButton != null){
                    livingStatus = checkedRadioButton.getText().toString();
                }
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (livingStatus.equals("")) {
                    Toast.makeText(LivingStatusActivity.this, "Select your Living Status", Toast. LENGTH_LONG).show();
                }
                else {
                    response.setLivingStatus(livingStatus);
                    Intent intent = new Intent();
                    intent.putExtra(RESPONSE_KEY, response);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}

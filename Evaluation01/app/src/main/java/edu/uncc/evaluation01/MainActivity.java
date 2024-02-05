package edu.uncc.evaluation01;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String percent;
    Double itemPrice;
    TextView textViewSelectedTip;
    TextView editTextBillAmount;

    public static final String KEY_SUMMARY = "SUMMARY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewSelectedTip = findViewById(R.id.textViewSelectedTip);
        editTextBillAmount = findViewById(R.id.editTextBillAmount);

        ActivityResultLauncher<Intent> startSelectTipActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    percent = data.getStringExtra(SelectTipActivity.KEY_PERC);
                    textViewSelectedTip.setText(percent);
                }
                else {
                    percent = null;
                    textViewSelectedTip.setText("N/A");
                }
            }
        });

        ActivityResultLauncher<Intent> startBillSummaryActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {

                }
                else {

                }
            }
        });

        findViewById(R.id.buttonSelectTip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SelectTipActivity.class);
                startSelectTipActivity.launch(intent);
            }
        });

        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemPriceString = editTextBillAmount.getText().toString();
                int intPercent;
                if (itemPriceString.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter a bill amount.", Toast.LENGTH_LONG).show();
                }
                else if (percent == null) {
                    Toast.makeText(MainActivity.this, "Enter a discount %.", Toast.LENGTH_LONG).show();
                }
                else {
                    itemPrice = Double.parseDouble(itemPriceString);
                    intPercent = Integer.parseInt(percent.substring(0, percent.length() - 1));

                    Bill bill = new Bill(itemPrice, intPercent);
                    Intent intent = new Intent(MainActivity.this, BillSummaryActivity.class);
                    intent.putExtra(KEY_SUMMARY, bill);
                    startBillSummaryActivity.launch(intent);
                }
            }
        });

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextBillAmount.setText("");
                textViewSelectedTip.setText("N/A");
            }
        });
    }
}
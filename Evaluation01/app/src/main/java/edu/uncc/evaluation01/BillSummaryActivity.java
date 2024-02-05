package edu.uncc.evaluation01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class BillSummaryActivity extends AppCompatActivity {

    TextView textViewBillAmount, textViewTipPercentage, textViewTipAmount, textViewTotalBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_summary);

        textViewBillAmount = findViewById(R.id.textViewBillAmount);
        textViewTipPercentage = findViewById(R.id.textViewTipPercentage);
        textViewTipAmount = findViewById(R.id.textViewTipAmount);
        textViewTotalBill = findViewById(R.id.textViewTotalBill);


        if(getIntent() != null && getIntent().hasExtra(MainActivity.KEY_SUMMARY)) {
            Bill bill = (Bill) getIntent().getSerializableExtra(MainActivity.KEY_SUMMARY);
            Log.d("EVAL3", String.valueOf(bill.getBillAmount()));

            String strBillAmount = String.valueOf(bill.getBillAmount());
            String strTipPercent = String.valueOf(bill.getTipPercent());
            String strTipAmount = String.valueOf(bill.getTipAmount());
            String strTotalBill = String.valueOf(bill.getTotal());

            textViewBillAmount.setText(strBillAmount);
            textViewTipPercentage.setText(strTipPercent);
            textViewTipAmount.setText(strTipAmount);
            textViewTotalBill.setText(strTotalBill);
        }
    }
}
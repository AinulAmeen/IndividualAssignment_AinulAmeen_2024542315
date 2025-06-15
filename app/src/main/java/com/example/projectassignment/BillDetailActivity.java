package com.example.projectassignment;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BillDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        setTitle("Bill Details");

        TextView monthLabel = findViewById(R.id.monthLabel);
        TextView monthText = findViewById(R.id.monthText);
        TextView unitLabel = findViewById(R.id.unitLabel);
        TextView unitText = findViewById(R.id.unitText);
        TextView totalChargesLabel = findViewById(R.id.totalChargesLabel);
        TextView totalChargesText = findViewById(R.id.totalChargesText);
        TextView rebateLabel = findViewById(R.id.rebateLabel);
        TextView rebateText = findViewById(R.id.rebateText);
        TextView finalCostLabel = findViewById(R.id.finalCostLabel);
        TextView finalCostText = findViewById(R.id.finalCostText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String month = extras.getString("month");
            double unit = extras.getDouble("unit");
            double totalCharges = extras.getDouble("totalCharges");
            double rebate = extras.getDouble("rebate");
            double finalCost = extras.getDouble("finalCost");

            monthText.setText(month);
            unitText.setText(String.format("%.2f", unit));
            totalChargesText.setText("RM " + String.format("%.2f", totalCharges));
            rebateText.setText(String.format("%.2f", rebate));
            finalCostText.setText("RM " + String.format("%.2f", finalCost));
        } else {
            finish(); // Close if no data
        }
    }
}
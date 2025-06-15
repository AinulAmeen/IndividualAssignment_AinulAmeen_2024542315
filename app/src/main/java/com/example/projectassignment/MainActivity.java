package com.example.projectassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText unitInput;
    private Spinner monthSpinner;
    private RadioGroup rebateRadioGroup;
    private Button calculateButton, aboutButton, viewBillsButton;
    private TextView totalChargesText, finalCostText;
    private DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Electricity Bill Calculator");


        unitInput = findViewById(R.id.unitInput);
        monthSpinner = findViewById(R.id.monthSpinner);
        rebateRadioGroup = findViewById(R.id.rebateRadioGroup);
        calculateButton = findViewById(R.id.calculateButton);
        aboutButton = findViewById(R.id.aboutButton);
        viewBillsButton = findViewById(R.id.viewBillsButton);
        totalChargesText = findViewById(R.id.totalChargesText);
        finalCostText = findViewById(R.id.finalCostText);
        dbHelper = new DataHelper(this);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(spinnerAdapter);

        calculateButton.setOnClickListener(v -> calculateAndSaveBill());
        aboutButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AboutActivity.class)));
        viewBillsButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BillsActivity.class)));
    }

    private void calculateAndSaveBill() {
        String month = monthSpinner.getSelectedItem().toString();
        String unitText = unitInput.getText().toString();

        if (unitText.isEmpty()) {
            Toast.makeText(this, "Please enter electricity units", Toast.LENGTH_SHORT).show();
            return;
        }

        double unit;
        double rebate = getSelectedRebate();
        try {
            unit = Double.parseDouble(unitText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid unit format", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalCharges = calculateTotalCharges(unit);
        double finalCost = totalCharges - (totalCharges * rebate / 100);
        finalCost = Math.round(finalCost * 100.0) / 100.0;

        boolean inserted = dbHelper.insertBill(month, unit, totalCharges, rebate, finalCost);
        if (inserted) {
            Toast.makeText(this, "Bill saved successfully", Toast.LENGTH_SHORT).show();
            totalChargesText.setText("Total Charges: RM " + String.format("%.2f", totalCharges));
            finalCostText.setText("Final Cost: RM " + String.format("%.2f", finalCost));
            totalChargesText.setVisibility(View.VISIBLE);
            finalCostText.setVisibility(View.VISIBLE);
            loadBills(); // Optional: Update any local list if needed
            unitInput.setText("");
        } else {
            Toast.makeText(this, "Failed to save bill", Toast.LENGTH_SHORT).show();
        }
    }

    private double getSelectedRebate() {
        int selectedId = rebateRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        if (selectedRadioButton == null) {
            return 0.0; // Default to 0% if no selection
        }
        String rebateText = selectedRadioButton.getText().toString().replace("%", "");
        return Double.parseDouble(rebateText);
    }

    private double calculateTotalCharges(double unit) {
        double total = 0;
        if (unit <= 200) {
            total = unit * 0.218;
        } else if (unit <= 300) {
            total = (200 * 0.218) + ((unit - 200) * 0.334);
        } else if (unit <= 600) {
            total = (200 * 0.218) + (100 * 0.334) + ((unit - 300) * 0.516);
        } else if (unit <= 900) {
            total = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((unit - 600) * 0.546);
        } else {
            total = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + (300 * 0.546) + ((unit - 900) * 0.546);
        }
        return Math.round(total * 100.0) / 100.0;
    }

    private void loadBills() {
        // No local list needed here; handled by BillsActivity
    }
}
package com.example.projectassignment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BillsActivity extends AppCompatActivity {
    private RecyclerView billsRecyclerView;
    private BillAdapter billAdapter;
    private DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        setTitle("Saved Bills");

        billsRecyclerView = findViewById(R.id.billsRecyclerView);
        dbHelper = new DataHelper(this);

        billsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        billAdapter = new BillAdapter(new ArrayList<>());
        billsRecyclerView.setAdapter(billAdapter);

        billsRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(BillsActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    if (position != RecyclerView.NO_POSITION) {
                        BillItem bill = billAdapter.getItem(position);
                        Intent intent = new Intent(BillsActivity.this, BillDetailActivity.class);
                        intent.putExtra("month", bill.getMonth());
                        intent.putExtra("unit", bill.getUnit());
                        intent.putExtra("totalCharges", bill.getTotalCharges());
                        intent.putExtra("rebate", bill.getRebate());
                        intent.putExtra("finalCost", bill.getFinalCost());
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });

        loadBills();
    }

    private void loadBills() {
        Cursor cursor = dbHelper.getAllBills();
        ArrayList<BillItem> bills = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String month = cursor.getString(cursor.getColumnIndexOrThrow(DataHelper.COLUMN_MONTH));
                double finalCost = cursor.getDouble(cursor.getColumnIndexOrThrow(DataHelper.COLUMN_FINAL_COST));
                double unit = cursor.getDouble(cursor.getColumnIndexOrThrow(DataHelper.COLUMN_UNIT));
                double totalCharges = cursor.getDouble(cursor.getColumnIndexOrThrow(DataHelper.COLUMN_TOTAL_CHARGES));
                double rebate = cursor.getDouble(cursor.getColumnIndexOrThrow(DataHelper.COLUMN_REBATE));
                bills.add(new BillItem(month, finalCost, unit, totalCharges, rebate, 0)); // Placeholder set to 0
            } while (cursor.moveToNext());
        }
        cursor.close();
        billAdapter.updateBills(bills);
    }
}
package com.example.projectassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private ArrayList<BillItem> bills;

    public BillAdapter(ArrayList<BillItem> bills) {
        this.bills = bills;
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BillViewHolder holder, int position) {
        BillItem bill = bills.get(position);
        holder.monthText.setText(bill.getMonth());
        holder.costText.setText("RM " + String.format("%.2f", bill.getFinalCost()));
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public void updateBills(ArrayList<BillItem> newBills) {
        bills.clear();
        bills.addAll(newBills);
        notifyDataSetChanged();
    }

    public BillItem getItem(int position) {
        return bills.get(position);
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView monthText, costText;

        public BillViewHolder(View itemView) {
            super(itemView);
            monthText = itemView.findViewById(R.id.billMonthText);
            costText = itemView.findViewById(R.id.billCostText);
        }
    }
}
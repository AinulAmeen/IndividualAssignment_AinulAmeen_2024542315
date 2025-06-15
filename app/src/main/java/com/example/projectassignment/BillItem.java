package com.example.projectassignment;

public class BillItem {
    private String month;
    private double finalCost;
    private double unit;
    private double totalCharges;
    private double rebate;
    private double placeholder; // Added to match constructor, can be removed if unused

    public BillItem(String month, double finalCost, double unit, double totalCharges, double rebate, double placeholder) {
        this.month = month;
        this.finalCost = finalCost;
        this.unit = unit;
        this.totalCharges = totalCharges;
        this.rebate = rebate;
        this.placeholder = placeholder;
    }

    // Getters
    public String getMonth() { return month; }
    public double getFinalCost() { return finalCost; }
    public double getUnit() { return unit; }
    public double getTotalCharges() { return totalCharges; }
    public double getRebate() { return rebate; }
}

package com.example.projectassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "electricity_bills.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "bills";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_TOTAL_CHARGES = "total_charges";
    public static final String COLUMN_REBATE = "rebate";
    public static final String COLUMN_FINAL_COST = "final_cost";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MONTH + " TEXT, " +
                COLUMN_UNIT + " REAL, " +
                COLUMN_TOTAL_CHARGES + " REAL, " +
                COLUMN_REBATE + " REAL, " +
                COLUMN_FINAL_COST + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertBill(String month, double unit, double totalCharges, double rebate, double finalCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MONTH, month);
        contentValues.put(COLUMN_UNIT, unit);
        contentValues.put(COLUMN_TOTAL_CHARGES, totalCharges);
        contentValues.put(COLUMN_REBATE, rebate);
        contentValues.put(COLUMN_FINAL_COST, finalCost);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllBills() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Cursor getBillByMonth(String month) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_MONTH + " = ?", new String[]{month});
    }

    public void updateMonthNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] shortMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] fullMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (int i = 0; i < shortMonths.length; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_MONTH, fullMonths[i]);
            db.update(TABLE_NAME, values, COLUMN_MONTH + "=?", new String[]{shortMonths[i]});
        }
        db.close();
    }
}
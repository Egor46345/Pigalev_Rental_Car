 package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

 public class booking_cars extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    SQLiteDatabase database;
    Button Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_cars);

        Exit = findViewById(R.id.Exit);
        Exit.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        UpdateTable();
    }
    public void UpdateTable(){
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_CONTACTS + " LEFT JOIN " + DBHelper.TABLE_BOOKED + " ON " + DBHelper.KEY_ID1 + " = " + DBHelper.KEY_IDCAR + " WHERE " + DBHelper.KEY_IDCAR + " IS NULL", null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID1);
            int brandRouteIndex = cursor.getColumnIndex(DBHelper.KEY_BRAND);
            int modelRouteIndex = cursor.getColumnIndex(DBHelper.KEY_MODEL);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int priceIndex = cursor.getColumnIndex(DBHelper.KEY_PRICE);

            TableLayout dbOutput = findViewById(R.id.dbOutput);
            dbOutput.removeAllViews();
            do {
                TableRow dbOutputRow = new TableRow(this);
                dbOutputRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                TextView outputID = new TextView(this);
                params.weight = 1.0f;
                outputID.setLayoutParams(params);
                outputID.setText(cursor.getString(idIndex));
                outputID.setTextSize(12);
                dbOutputRow.addView(outputID);

                TextView outputBrand = new TextView(this);
                params.weight = 3.0f;
                outputBrand.setLayoutParams(params);
                outputBrand.setText(cursor.getString(brandRouteIndex));
                outputBrand.setTextSize(12);
                dbOutputRow.addView(outputBrand);

                TextView outputModel = new TextView(this);
                params.weight = 3.0f;
                outputModel.setLayoutParams(params);
                outputModel.setText(cursor.getString(modelRouteIndex));
                outputModel.setTextSize(12);
                dbOutputRow.addView(outputModel);

                TextView outputDate = new TextView(this);
                params.weight = 3.0f;
                outputDate.setLayoutParams(params);
                outputDate.setText(cursor.getString(dateIndex));
                outputDate.setTextSize(12);
                dbOutputRow.addView(outputDate);

                TextView outputPrice = new TextView(this);
                params.weight = 3.0f;
                outputPrice.setLayoutParams(params);
                outputPrice.setText(cursor.getString(priceIndex));
                outputPrice.setTextSize(12);
                dbOutputRow.addView(outputPrice);

                Button deleteBtn = new Button(this);
                deleteBtn.setOnClickListener(this);
                params.weight = 1.0f;
                deleteBtn.setLayoutParams(params);
                deleteBtn.setText("Забронировать\nавтомобиль");
                deleteBtn.setTextSize(12);
                deleteBtn.setId(cursor.getInt(idIndex));
                dbOutputRow.addView(deleteBtn);

                dbOutput.addView(dbOutputRow);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Exit:
                startActivity(new Intent(this, menu_Users.class));
                finish();
                break;
            default:
                View outputDBRow = (View) v.getParent();
                ViewGroup outputDB = (ViewGroup) outputDBRow.getParent();
                int indexStr = outputDB.indexOfChild(outputDBRow);
                int index = 0;
                Cursor cursorUpdater = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_CONTACTS + " LEFT JOIN " + DBHelper.TABLE_BOOKED + " ON " + DBHelper.KEY_ID1 + " = " + DBHelper.KEY_IDCAR + " WHERE " + DBHelper.KEY_IDCAR + " IS NULL", null);

                if (cursorUpdater != null) {
                    cursorUpdater.moveToPosition(indexStr);
                    index =  cursorUpdater.getInt(0);
                }
                Intent intent = new Intent(booking_cars.this, booked.class);
                Bundle b = new Bundle();
                b.putInt("key", index);
                intent.putExtras(b);
                startActivity(intent);
                finish();
                assert cursorUpdater != null;
                cursorUpdater.close();
                break;
        }
    }
}
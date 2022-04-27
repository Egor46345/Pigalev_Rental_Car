package com.example.shop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class base_data_car extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnClear, Exit;
    EditText carBrand, carModel, etDate, etPrice;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_data_car);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        Exit = findViewById(R.id.Exit);
        Exit.setOnClickListener(this);

        carBrand = findViewById(R.id.carBrand);
        carModel = findViewById(R.id.carModel);
        etDate = findViewById(R.id.etDate);
        etPrice = findViewById(R.id.etPrice);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        UpdateTable();

        carBrand.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                carBrand.setHint("");
            else
                carBrand.setHint("Марка");
        });
        carModel.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                carModel.setHint("");
            else
                carModel.setHint("Модель");
        });
        etDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                etDate.setHint("");
            else
                etDate.setHint("Год выпуска");
        });
        etPrice.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                etPrice.setHint("");
            else
                etPrice.setHint("Стоимость проката");
        });
    }
    public void UpdateTable(){
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

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
                deleteBtn.setText("Удалить\nзапись");
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
            case R.id.btnAdd:
                String brand = carBrand.getText().toString();
                String model = carModel.getText().toString();
                String date = etDate.getText().toString();
                String price = etPrice.getText().toString();

                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_BRAND, brand);
                contentValues.put(DBHelper.KEY_MODEL, model);
                contentValues.put(DBHelper.KEY_DATE, date);
                contentValues.put(DBHelper.KEY_PRICE, price);

                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                carBrand.setText("");
                carModel.setText("");
                etDate.setText("");
                etPrice.setText("");
                UpdateTable();
                break;
            case R.id.btnClear:
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                TableLayout dbOutput = findViewById(R.id.dbOutput);
                dbOutput.removeAllViews();
                UpdateTable();
                break;
            case R.id.Exit:
                startActivity(new Intent(this, menu_base_data.class));
                finish();
                break;
            default:
                View outputDBRow = (View) v.getParent();
                ViewGroup outputDB = (ViewGroup) outputDBRow.getParent();
                outputDB.removeView(outputDBRow);
                outputDB.invalidate();

                database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID1 + " = ?", new String[]{String.valueOf(v.getId())});
        }
    }
}
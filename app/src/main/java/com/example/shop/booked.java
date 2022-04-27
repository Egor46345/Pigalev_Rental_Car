package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class booked extends AppCompatActivity implements View.OnClickListener {

    EditText surname, time;
    Button bookedBtn, Exit;
    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked);

        surname = findViewById(R.id.surname);
        time = findViewById(R.id.time);

        bookedBtn = findViewById(R.id.bookedBtn);
        bookedBtn.setOnClickListener(this);
        Exit = findViewById(R.id.Exit);
        Exit.setOnClickListener(this);

        Bundle arguments = getIntent().getExtras();
        index = arguments.getInt("key");

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        UpdateTable();

        surname.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                surname.setHint("");
            else
                surname.setHint("ФИО бронируемого");
        });
        time.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                time.setHint("");
            else
                time.setHint("Примерная продолжительность");
        });
    }
    public void UpdateTable(){
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, DBHelper.KEY_ID1  + "==" + index, null, null, null, null);

        if (cursor.moveToFirst()) {
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

                TextView outputBrand = new TextView(this);
                params.weight = 3.0f;
                outputBrand.setLayoutParams(params);
                outputBrand.setText(cursor.getString(brandRouteIndex));
                outputBrand.setTextSize(18);
                dbOutputRow.addView(outputBrand);

                TextView outputModel = new TextView(this);
                params.weight = 3.0f;
                outputModel.setLayoutParams(params);
                outputModel.setText(cursor.getString(modelRouteIndex));
                outputModel.setTextSize(18);
                dbOutputRow.addView(outputModel);

                TextView outputDate = new TextView(this);
                params.weight = 3.0f;
                outputDate.setLayoutParams(params);
                outputDate.setText(cursor.getString(dateIndex));
                outputDate.setTextSize(18);
                dbOutputRow.addView(outputDate);

                TextView outputPrice = new TextView(this);
                params.weight = 3.0f;
                outputPrice.setLayoutParams(params);
                outputPrice.setText(cursor.getString(priceIndex));
                outputPrice.setTextSize(18);
                dbOutputRow.addView(outputPrice);

                dbOutput.addView(dbOutputRow);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Exit:
                startActivity(new Intent(this, booking_cars.class));
                finish();
                break;
            case R.id.bookedBtn:
                String idCar = "" + index;
                String FIO = surname.getText().toString();
                String timeBooked = time.getText().toString();
                String data = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String id = "" + MainActivity.user;

                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_IDCAR, idCar);
                contentValues.put(DBHelper.KEY_SURNAME, FIO);
                contentValues.put(DBHelper.KEY_TIME, timeBooked);
                contentValues.put(DBHelper.KEY_DATEBOOKED, data);
                contentValues.put(DBHelper.KEY_IDUSERS, id);

                database.insert(DBHelper.TABLE_BOOKED, null, contentValues);
                Toast.makeText(this, "Вы забронировали автомобиль\nОформить договор можно по адресу г.Нижний Новгород ул.Мончегорская д.6а", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, menu_Users.class));
                finish();
                break;
        }
    }
}
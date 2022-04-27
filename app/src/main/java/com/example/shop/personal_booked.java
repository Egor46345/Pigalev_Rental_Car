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

public class personal_booked extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    SQLiteDatabase database;
    Button Exit;

    int k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_booked);

        Exit = findViewById(R.id.Exit);
        Exit.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        UpdateTable();
    }

    public void UpdateTable(){
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_BOOKED + " WHERE " + DBHelper.KEY_IDUSERS + " = '" + MainActivity.user + "'", null);
        k = 0;
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID2);
            int idCarsIndex = cursor.getColumnIndex(DBHelper.KEY_IDCAR);
            int surnameIndex = cursor.getColumnIndex(DBHelper.KEY_SURNAME);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            int dateBookedIndex = cursor.getColumnIndex(DBHelper.KEY_DATEBOOKED);
            int userIndex = cursor.getColumnIndex(DBHelper.KEY_IDUSERS);

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

                TextView outputIDCars = new TextView(this);
                params.weight = 1.0f;
                outputIDCars.setLayoutParams(params);
                outputIDCars.setText(cursor.getString(idCarsIndex));
                outputIDCars.setTextSize(12);
                dbOutputRow.addView(outputIDCars);

                TextView outputSurname = new TextView(this);
                params.weight = 3.0f;
                outputSurname.setLayoutParams(params);
                outputSurname.setText(cursor.getString(surnameIndex));
                outputSurname.setTextSize(12);
                dbOutputRow.addView(outputSurname);

                TextView outputTime = new TextView(this);
                params.weight = 3.0f;
                outputTime.setLayoutParams(params);
                outputTime.setText(cursor.getString(timeIndex));
                outputTime.setTextSize(12);
                dbOutputRow.addView(outputTime);

                TextView outputdateBooked = new TextView(this);
                params.weight = 3.0f;
                outputdateBooked.setLayoutParams(params);
                outputdateBooked.setText(cursor.getString(dateBookedIndex));
                outputdateBooked.setTextSize(12);
                dbOutputRow.addView(outputdateBooked);

                TextView outputUser = new TextView(this);
                params.weight = 3.0f;
                outputUser.setLayoutParams(params);
                outputUser.setText(cursor.getString(userIndex));
                outputUser.setTextSize(12);
                dbOutputRow.addView(outputUser);

                Button deleteBtn = new Button(this);
                deleteBtn.setOnClickListener(this);
                params.weight = 1.0f;
                deleteBtn.setLayoutParams(params);
                deleteBtn.setText("Снять\nбронь");
                deleteBtn.setTextSize(12);
                deleteBtn.setId(cursor.getInt(idCarsIndex));
                dbOutputRow.addView(deleteBtn);

                dbOutput.addView(dbOutputRow);
                k++;
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
                Cursor cursorUpdater = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_BOOKED + " WHERE " + DBHelper.KEY_IDUSERS + " = '" + MainActivity.user + "'", null);
                if (cursorUpdater != null) {
                    cursorUpdater.moveToPosition(indexStr);
                    index =  cursorUpdater.getInt(0);
                }
                database.delete(DBHelper.TABLE_BOOKED, DBHelper.KEY_ID2 + " = " + index, null);
                if(k == 1){
                    TableLayout dbOutput = findViewById(R.id.dbOutput);
                    dbOutput.removeAllViews();
                }
                database.delete(DBHelper.TABLE_BOOKED, DBHelper.KEY_ID2 + " = " + index, null);
                UpdateTable();
                assert cursorUpdater != null;
                cursorUpdater.close();
                break;
        }
    }
}
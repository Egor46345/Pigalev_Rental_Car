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

public class UpdateUsers extends AppCompatActivity implements View.OnClickListener {

    Button exitBtn, saveBtn, deleteBtn;
    EditText etLogin, etPassword;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_users);
        exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(this);

        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);

        deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(this);

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        Bundle arguments = getIntent().getExtras();
        index = arguments.getInt("key");

        Update();

        etLogin.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                etLogin.setHint("");
            else
                etLogin.setHint("Логин");
        });
        etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                etPassword.setHint("");
            else
                etPassword.setHint("Пароль");
        });
    }
    public void Update() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_USERS + " WHERE " + DBHelper.KEY_ID + " = '" + index + "'", null);

        int loginIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
        int passwordIndex = cursor.getColumnIndex(DBHelper.KEY_PASSWORD);

        if (cursor.moveToFirst()) {
            etLogin.setText(cursor.getString(loginIndex));
            etPassword.setText(cursor.getString(passwordIndex));
        }
        cursor.close();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exitBtn:
                startActivity(new Intent(this, users.class));
                finish();
                break;
            case R.id.saveBtn:
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();

                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_NAME, login);
                contentValues.put(DBHelper.KEY_PASSWORD, password);
                database.update(DBHelper.TABLE_USERS, contentValues,DBHelper.KEY_ID + " = '" + index + "'", null);
                startActivity(new Intent(this, users.class));
                finish();
                break;
            case R.id.deleteBtn:
                database.delete(DBHelper.TABLE_USERS, DBHelper.KEY_ID + " = " + index, null);
                startActivity(new Intent(this, users.class));
                finish();
                break;
        }
    }
}
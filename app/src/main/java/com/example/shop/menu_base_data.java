package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu_base_data extends AppCompatActivity implements View.OnClickListener {

    Button EditingCarBase, BookedCars, EditingUsers, Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_base_data);

        EditingCarBase = findViewById(R.id.EditingCarBase);
        EditingCarBase.setOnClickListener(this);
        BookedCars = findViewById(R.id.BookedCars);
        BookedCars.setOnClickListener(this);
        EditingUsers = findViewById(R.id.EditingUsers);
        EditingUsers.setOnClickListener(this);
        Exit = findViewById(R.id.Exit);
        Exit.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.EditingCarBase:
                startActivity(new Intent(this, base_data_car.class));
                finish();
                break;
            case R.id.BookedCars:
                startActivity(new Intent(this, booked_cars.class));
                finish();
                break;
            case R.id.EditingUsers:
                startActivity(new Intent(this, users.class));
                finish();
                break;
            case R.id.Exit:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }
}
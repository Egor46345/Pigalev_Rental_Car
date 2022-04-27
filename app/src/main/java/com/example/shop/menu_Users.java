package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu_Users extends AppCompatActivity implements View.OnClickListener {

    Button PersonalArea, BookingCars, BookedCars, Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_users);

        PersonalArea = findViewById(R.id.PersonalArea);
        PersonalArea.setOnClickListener(this);
        BookingCars= findViewById(R.id.BookingCars);
        BookingCars.setOnClickListener(this);
        BookedCars= findViewById(R.id.BookedCars);
        BookedCars.setOnClickListener(this);
        Exit= findViewById(R.id.Exit);
        Exit.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.PersonalArea:
                startActivity(new Intent(this, personal_area.class));
                finish();
                break;
            case R.id.BookingCars:
                startActivity(new Intent(this, booking_cars.class));
                finish();
                break;
            case R.id.BookedCars:
                startActivity(new Intent(this, personal_booked.class));
                finish();
                break;
            case R.id.Exit:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }
}
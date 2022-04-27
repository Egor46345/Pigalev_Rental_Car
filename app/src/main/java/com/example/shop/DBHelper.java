package com.example.shop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactsDb";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID1 = "_id1";
    public static final String KEY_BRAND = "brand";
    public static final String KEY_MODEL = "model";
    public static final String KEY_DATE = "date";
    public static final String KEY_PRICE = "price";

    public static final String TABLE_BOOKED = "bookedCars";

    public static final String KEY_ID2 = "_id2";
    public static final String KEY_IDCAR = "idCar";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_TIME = "time";
    public static final String KEY_DATEBOOKED = "dateBooked";
    public static final String KEY_IDUSERS = "idUser";

    public static final String TABLE_USERS = "userTable";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID1
                + " integer primary key AUTOINCREMENT," + KEY_BRAND + " text," + KEY_MODEL + " text," + KEY_DATE + " text," + KEY_PRICE + " text" + ")");

        db.execSQL("create table " + TABLE_BOOKED + "(" + KEY_ID2
                + " integer primary key  AUTOINCREMENT," + KEY_SURNAME + " text," + KEY_TIME + " text," + KEY_DATEBOOKED + " text," + KEY_IDCAR
                + " integer," + KEY_IDUSERS
                + " integer," + " FOREIGN KEY("+ KEY_IDCAR +") REFERENCES "+ TABLE_CONTACTS +"("+ KEY_ID1 +")," +
                " FOREIGN KEY("+ KEY_IDUSERS +") REFERENCES "+ TABLE_USERS +"("+ KEY_ID +"))");

        db.execSQL("create table " + TABLE_USERS + "(" + KEY_ID
                + " integer primary key AUTOINCREMENT," + KEY_NAME + " text," + KEY_PASSWORD + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion > newVersion){
            db.execSQL("drop table if exists " + TABLE_CONTACTS);
            onCreate(db);

            db.execSQL("drop table if exists " + TABLE_BOOKED);
            onCreate(db);

            db.execSQL("drop table if exists " + TABLE_USERS);
            onCreate(db);
        }
    }
}
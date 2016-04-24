package com.example.andrewsamir.movieapp.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrew Samir on 3/8/2016.
 */
public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;


    private static final String DATABASE_NAME = "DBase";
    private static final String TABLE_NAME = "Favourite";
    private static final String ID = "ID";
    private static final String TITLE = "Tile";
    private static final String RELEASE_DATE = "ReleaseDate";
    private static final String IMAGE_PATH = "ImagePath";
    private static final String OVERVIEW = "Overview";
    private static final String RATE = "Rate";

    private static final String CREATE_TABLE_EFTKAD = "CREATE TABLE "
            + TABLE_NAME + "( " + ID + " INTEGER PRIMARY KEY, " + TITLE + " TEXT , "
            + RELEASE_DATE + " TEXT , " + IMAGE_PATH + " TEXT , " + OVERVIEW
            + " TEXT ," + RATE + " TEXT )";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_EFTKAD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP IF EXISTS " + TABLE_NAME);
    }

    public boolean ADD(int id, String title, String release_date, String image_path, String overview, String rate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(TITLE, title);
        values.put(IMAGE_PATH, image_path);
        values.put(OVERVIEW, overview);
        values.put(RELEASE_DATE, release_date);
        values.put(RATE, rate);

        // insert row
        long user_row = db.insert(TABLE_NAME, null, values);
        if (user_row == -1)
            return false;
        else
            return true;

    }

    public void deletemovie(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = " + id, null);
    }

    public Cursor getMoviess() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

}

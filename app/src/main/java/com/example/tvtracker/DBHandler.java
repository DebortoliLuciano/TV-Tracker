package com.example.tvtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hikingapplication.Javabean.Location;
import com.example.tvtracker.JavaBeans.show;

import java.util.ArrayList;

/**
 * @author Saad Amjad
 * @date 2020/03/15
 */

public class DBHandler extends SQLiteOpenHelper {

    /*
     * Keep track of the database version
     */

    public static final int DATABASE_VERSION = 1;

    /*
     * Name the database
     */

    public static final String DATABASE_NAME = "tvShows";


    /*
     * Names of tables
     */

    public static final String TABLE_MAIN = "main";
    public static final String TABLE_SHOWS = "show";
    public static final String TABLE_NETWORK = "network";
    public static final String TABLE_GENRE = "genre";

    /*
     * Main table
     */

    public static final String COLUMN_MAINID = "id";
    public static final String COLUMN_SHOW = "show";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_NETWORK = "network";


    /*
     * show table
     */
    public static final String COLUMN_SHOWID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_IMDBID = "imdb_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DAY = "weekly_release_day";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_SUMMARY = "summary";


    /*
     * network table
     */
    public static final String COLUMN_NETWORKID = "id";
    public static final String COLUMN_NETWORKNAME = "network";

    /*
     * genre table
     */
    public static final String COLUMN_GENREID = "id";
    public static final String COLUMN_GENRENAME = "genre";


    /*
     * create statements
     */

    //updated with foreign key references
    public static final String CREATE_MAIN_TABLE = "CREATE TABLE " +
            TABLE_MAIN + "(" + COLUMN_MAINID + " INTEGER PRIMARY KEY,"
            + COLUMN_SHOW + " INTEGER REFERENCES " + TABLE_SHOWS + "(" + COLUMN_SHOWID + "),"
            + COLUMN_GENRE + " INTEGER REFERENCES " + TABLE_GENRE + "(" + COLUMN_GENREID + "),"
            + COLUMN_STATUS + " TEXT, "
            + COLUMN_NETWORK + " INTEGER REFERENCES " + TABLE_NETWORK +
            "(" + COLUMN_NETWORKID +"))" ;

    public static final String CREATE_SHOW_TABLE = "CREATE TABLE " +
            TABLE_SHOWS + "(" + COLUMN_SHOWID + " INTEGER PRIMARY KEY,"
            + COLUMN_TITLE + " TEXT, " + COLUMN_IMDBID + " INTEGER,"
            + COLUMN_TIME + " TEXT, " + COLUMN_DAY + " TEXT, " + COLUMN_IMAGE
            + " TEXT, " + COLUMN_SUMMARY + " TEXT)" ;

    public static final String CREATE_NETWORK_TABLE = "CREATE TABLE " +
            TABLE_NETWORK + "(" + COLUMN_NETWORKID + " INTEGER PRIMARY KEY,"
            + COLUMN_NETWORKNAME + " TEXT)" ;

    public static final String CREATE_GENRE_TABLE = "CREATE TABLE " +
            TABLE_GENRE + "(" + COLUMN_GENREID + " INTEGER PRIMARY KEY,"
            + COLUMN_GENRENAME + " TEXT)" ;


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MAIN_TABLE);
        db.execSQL(CREATE_SHOW_TABLE);
        db.execSQL(CREATE_NETWORK_TABLE);
        db.execSQL(CREATE_GENRE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*
           CREATE STATEMENTS
     */

    //create show
    public void addShow(show show){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, show.getTitle());
        values.put(COLUMN_IMDBID, show.getImdbID());
        values.put(COLUMN_TIME, show.getTime());
        values.put(COLUMN_DAY, show.getDay());
        values.put(COLUMN_IMAGE, show.getCover());
        values.put(COLUMN_SUMMARY, show.getSummary());
        db.insert(TABLE_SHOWS, null, values);
        db.close();
    }

    /*
           READ STATEMENTS
     */

    //fix zis bad boi
    public Location getLocation(int id){
        SQLiteDatabase db  = this.getReadableDatabase();
        Location location = null;
        Cursor cursor = db.query(TABLE_LOCATION, new String[]{COLUMN_ID,
        COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_GEO,
        COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_TEMP,
        COLUMN_LAST_UPDATED}, COLUMN_ID + "= ?",
        new String[]{String.valueOf(id)}, null, null, null);
        if(cursor.moveToFirst()){
            location = new Location(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4),
                    cursor.getDouble(5),
                    cursor.getDouble(6),
                    cursor.getLong(7));
        }
        db.close();
        return location;
    }

    public ArrayList<Location> getAllLocations(){
       SQLiteDatabase db  = this.getReadableDatabase();
       Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOCATION ,
                null);
       ArrayList<Location> locations = new ArrayList<>();
       while(cursor.moveToNext()) {
           locations.add(new Location(
                   cursor.getInt(0),
                   cursor.getString(1),
                   cursor.getString(2),
                   cursor.getString(3),
                   cursor.getDouble(4),
                   cursor.getDouble(5),
                   cursor.getDouble(6),
                   cursor.getLong(7)));
       }
       db.close();
       return locations;
    }

    /*
        Update Statements
    */

    //show update
    public int updateShow(show show){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, show.getTitle());
        values.put(COLUMN_IMDBID, show.getImdbID());
        values.put(COLUMN_TIME, show.getTime());
        values.put(COLUMN_DAY, show.getDay());
        values.put(COLUMN_IMAGE, show.getCover());
        values.put(COLUMN_SUMMARY, show.getSummary());
        return db.update(TABLE_SHOWS, values, COLUMN_SHOWID + "=?",
                new String[]{String.valueOf(show.getId())});
    }

    /*
        Delete Statements
     */

    //delete show
    public void deleteShow(int location){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOWS, COLUMN_SHOWID + " = ?",
                new String[]{String.valueOf(location)});
        db.close();
    }

}

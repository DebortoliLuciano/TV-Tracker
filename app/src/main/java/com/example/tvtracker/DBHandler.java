package com.example.tvtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tvtracker.JavaBeans.Genre;
import com.example.tvtracker.JavaBeans.MainRow;
import com.example.tvtracker.JavaBeans.Network;
import com.example.tvtracker.JavaBeans.Show;

import java.util.ArrayList;

/**
 * @author Saad Amjad
 * @date 2020/03/15
 * @author2 Luciano DeBortoli
 * @dateEdited 2020/03/19
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
     * Show table
     */
    public static final String COLUMN_SHOWID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_IMDBID = "imdb_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DAY = "weekly_release_day";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_WATCHED = "watched";


    /*
     * Network table
     */
    public static final String COLUMN_NETWORKID = "id";
    public static final String COLUMN_NETWORKNAME = "network";

    /*
     * Genre table
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
            + " TEXT, " + COLUMN_SUMMARY + " TEXT, " + COLUMN_WATCHED + " TEXT)" ;

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

    //create Show record
    public void addShow(Show show){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, show.getTitle());
        values.put(COLUMN_IMDBID, show.getImdbID());
        values.put(COLUMN_TIME, show.getTime());
        values.put(COLUMN_DAY, show.getDay());
        values.put(COLUMN_IMAGE, show.getCover());
        values.put(COLUMN_SUMMARY, show.getSummary());
        values.put(COLUMN_WATCHED, show.getWatched());
        db.insert(TABLE_SHOWS, null, values);
        db.close();
    }

    //create Genre record
    public void addGenre(Genre genre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GENRENAME, genre.getGenreName());
        db.insert(TABLE_GENRE, null, values);
        db.close();
    }

    //create Network record
    public void addNetwork(Network network){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NETWORKNAME, network.getNetworkName());
        db.insert(TABLE_NETWORK, null, values);
        db.close();
    }

    //create main table record
    public void addMainRow(MainRow mainRow){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SHOW, mainRow.getShow());
        values.put(COLUMN_GENRE, mainRow.getGenre());
        values.put(COLUMN_STATUS, mainRow.getStatus());
        values.put(COLUMN_NETWORK, mainRow.getNetwork());
        db.insert(TABLE_MAIN, null, values);
        db.close();
    }

    /*
           READ STATEMENTS
     */

    //read one show
    public Show getShow(int id){
        SQLiteDatabase db  = this.getReadableDatabase();
        Show show = null;
        Cursor cursor = db.query(TABLE_SHOWS, new String[]{COLUMN_SHOWID,
        COLUMN_TITLE, COLUMN_TIME, COLUMN_DAY, COLUMN_IMAGE, COLUMN_SUMMARY, COLUMN_WATCHED}, COLUMN_SHOWID + "= ?",
        new String[]{String.valueOf(id)}, null, null, null);
        if(cursor.moveToFirst()){
            show = new Show(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7));
        }
        db.close();
        return show;
    }

    //read shows by watched
    public ArrayList<Show> getWatchedShows(String watched){
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SHOWS, new String[]{COLUMN_SHOWID,
                        COLUMN_TITLE, COLUMN_TIME, COLUMN_DAY, COLUMN_IMAGE, COLUMN_SUMMARY, COLUMN_WATCHED}, COLUMN_WATCHED + "= ?",
                new String[]{String.valueOf(watched)}, null, null, null);
        ArrayList<Show> shows = new ArrayList<>();
        while(cursor.moveToNext()) {
            shows.add(new Show(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7)));
        }
        db.close();
        return shows;
    }

    //read all shows
    public ArrayList<Show> getAllShows(){
       SQLiteDatabase db  = this.getReadableDatabase();
       Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SHOWS ,
                null);
       ArrayList<Show> shows = new ArrayList<>();
       while(cursor.moveToNext()) {
           shows.add(new Show(
                   cursor.getInt(0),
                   cursor.getString(1),
                   cursor.getString(2),
                   cursor.getString(3),
                   cursor.getString(4),
                   cursor.getString(5),
                   cursor.getString(6),
                   cursor.getString(7)));
       }
       db.close();
       return shows;
    }

    //read one genre
    public Genre getGenre(int id){
        SQLiteDatabase db  = this.getReadableDatabase();
        Genre genre = null;
        Cursor cursor = db.query(TABLE_GENRE, new String[]{COLUMN_GENREID,
                        COLUMN_GENRENAME}, COLUMN_GENREID + "= ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor.moveToFirst()){
            genre = new Genre(
                    cursor.getInt(0),
                    cursor.getString(1));
        }
        db.close();
        return genre;
    }

    //read all genres
    public ArrayList<Genre> getAllGenre(){
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GENRE ,
                null);
        ArrayList<Genre> genre = new ArrayList<>();
        while(cursor.moveToNext()) {
            genre.add(new Genre(
                    cursor.getInt(0),
                    cursor.getString(1)));
        }
        db.close();
        return genre;
    }

    //read one network
    public Network getNetwork(int id){
        SQLiteDatabase db  = this.getReadableDatabase();
        Network network = null;
        Cursor cursor = db.query(TABLE_NETWORK, new String[]{COLUMN_NETWORKID,
                        COLUMN_NETWORKNAME}, COLUMN_NETWORKID + "= ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor.moveToFirst()){
            network = new Network(
                    cursor.getInt(0),
                    cursor.getString(1));
        }
        db.close();
        return network;
    }

    //read all networks
    public ArrayList<Network> getAllNetwork(){
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NETWORK ,
                null);
        ArrayList<Network> networks = new ArrayList<>();
        while(cursor.moveToNext()) {
            networks.add(new Network(
                    cursor.getInt(0),
                    cursor.getString(1)));
        }
        db.close();
        return networks;
    }

    //read one row from main table
    public MainRow getMainRow(int id){
        SQLiteDatabase db  = this.getReadableDatabase();
        MainRow mainRow = null;
        Cursor cursor = db.query(TABLE_MAIN, new String[]{COLUMN_MAINID,
                        COLUMN_SHOW, COLUMN_GENRE, COLUMN_STATUS, COLUMN_NETWORK}, COLUMN_MAINID + "= ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor.moveToFirst()){
            mainRow = new MainRow(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4));
        }
        db.close();
        return mainRow;
    }

    //read one row from main table
    public ArrayList<MainRow> getMainRows(){
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SHOWS ,
                null);
        ArrayList<MainRow> mainRows = new ArrayList<>();
        while(cursor.moveToNext()) {
            mainRows.add(new MainRow(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4)));
        }
        db.close();
        return mainRows;
    }

    /*
        Update Statements
    */

    //Show update
    public int updateShow(Show show){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, show.getTitle());
        values.put(COLUMN_IMDBID, show.getImdbID());
        values.put(COLUMN_TIME, show.getTime());
        values.put(COLUMN_DAY, show.getDay());
        values.put(COLUMN_IMAGE, show.getCover());
        values.put(COLUMN_SUMMARY, show.getSummary());
        values.put(COLUMN_WATCHED, show.getWatched());
        return db.update(TABLE_SHOWS, values, COLUMN_SHOWID + "=?",
                new String[]{String.valueOf(show.getId())});
    }

    //genre update
    public int updateGenre(Genre genre){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GENRENAME, genre.getGenreName());
        return db.update(TABLE_GENRE, values, COLUMN_GENREID + "=?",
                new String[]{String.valueOf(genre.getId())});
    }

    //network update
    public int updateNetwork(Network network){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NETWORKNAME, network.getNetworkName());
        return db.update(TABLE_NETWORK, values, COLUMN_NETWORKID + "=?",
                new String[]{String.valueOf(network.getId())});
    }

    //main table update
    public int updateMainRow(MainRow mainRow){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SHOW, mainRow.getShow());
        values.put(COLUMN_GENRE, mainRow.getGenre());
        values.put(COLUMN_STATUS, mainRow.getStatus());
        values.put(COLUMN_NETWORK, mainRow.getNetwork());
        return db.update(TABLE_MAIN, values, COLUMN_MAINID + "=?",
                new String[]{String.valueOf(mainRow.getId())});
    }

    /*
        Delete Statements
     */

    //delete Show (in both main and Show table)
    public void deleteShow(int show){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOWS, COLUMN_SHOWID + " = ?",
                new String[]{String.valueOf(show)});
        db.delete(TABLE_MAIN, COLUMN_SHOW + " = ?",
                new String[]{String.valueOf(show)});
        db.close();
    }

    //delete main row
    public void deleteMain(int mainRow){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MAIN, COLUMN_SHOW + " = ?",
                new String[]{String.valueOf(mainRow)});
        db.close();
    }

    //other tables don't need deletes for now, may change later
}

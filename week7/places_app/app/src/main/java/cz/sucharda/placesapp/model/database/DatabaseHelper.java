package cz.sucharda.placesapp.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cz.sucharda.placesapp.model.Place;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "places.db";
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        String CREATE_TABLE_GAME = "CREATE TABLE " + Place.TABLE + '('
                + Place.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Place.KEY_NAME + " TEXT, "
                + Place.KEY_CITY + " TEXT, "
                + Place.KEY_LATITUDE + " REAL, "
                + Place.KEY_LONGITUDE + " REAL )";

        db.execSQL(CREATE_TABLE_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Place.TABLE);
        onCreate(db);
    }
}

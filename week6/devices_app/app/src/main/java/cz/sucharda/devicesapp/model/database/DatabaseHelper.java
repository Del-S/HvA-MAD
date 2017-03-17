package cz.sucharda.devicesapp.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cz.sucharda.devicesapp.model.Device;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "devices.db";
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        String CREATE_TABLE_GAME = "CREATE TABLE " + Device.TABLE + '('
                + Device.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Device.KEY_NAME + " TEXT, "
                + Device.KEY_VALUES + " TEXT )";

        db.execSQL(CREATE_TABLE_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Device.TABLE);
        onCreate(db);
    }
}

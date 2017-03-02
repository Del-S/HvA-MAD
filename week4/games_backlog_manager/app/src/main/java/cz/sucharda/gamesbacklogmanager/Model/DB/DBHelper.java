package cz.sucharda.gamesbacklogmanager.Model.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.sucharda.gamesbacklogmanager.Model.Game;
import cz.sucharda.gamesbacklogmanager.R;

class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "game.db";
    private final Context context;
    private static final String TIME_FORMAT = "dd/MM/yyyy";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private static String getSimpleCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        Date today = new Date();

        return format.format(today);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        String CREATE_TABLE_GAME = "CREATE TABLE " + Game.TABLE + '('
                + Game.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Game.KEY_TITLE + " TEXT, "
                + Game.KEY_PLATFORM + " TEXT, "
                + Game.KEY_DATE + " TEXT, "
                + Game.KEY_STATUS + " TEXT, "
                + Game.KEY_NOTES + " TEXT )";

        db.execSQL(CREATE_TABLE_GAME);
        String curDate = getSimpleCurrentDate();
        String[] titles = context.getResources().getStringArray(R.array.game_titles);

        for (int i = 1; i < titles.length; i++) {
            ContentValues values = new ContentValues();
            values.put(Game.KEY_TITLE, titles[i]);
            values.put(Game.KEY_PLATFORM, "PC");
            values.put(Game.KEY_DATE, curDate);
            values.put(Game.KEY_STATUS, "Stalled");
            values.put(Game.KEY_NOTES, "I should stop playing");
            // Inserting Row
            db.insert(Game.TABLE, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Game.TABLE);
        onCreate(db);
    }
}
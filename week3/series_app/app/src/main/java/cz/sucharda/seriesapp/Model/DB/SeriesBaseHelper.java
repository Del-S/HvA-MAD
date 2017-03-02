package cz.sucharda.seriesapp.Model.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 *  Database connection - creates or upgrades database
 */

public class SeriesBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "series.db";
    private static final int VERSION = 1;

    public SeriesBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SeriesDbSchema.SeriesTable.NAME + "(" +
            SeriesDbSchema.SeriesTable.Cols.ID + " integer primary key autoincrement, " +
            SeriesDbSchema.SeriesTable.Cols.NAME + ", " +
            SeriesDbSchema.SeriesTable.Cols.DESCRITPION + ", " +
            SeriesDbSchema.SeriesTable.Cols.DATE_START + ", " +
            SeriesDbSchema.SeriesTable.Cols.DATE_FINISH + ", " +
            SeriesDbSchema.SeriesTable.Cols.FINISHED +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SeriesDbSchema.SeriesTable.NAME);
        onCreate(db);
    }
}

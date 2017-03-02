package cz.sucharda.homework.Model.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 *  Database connection - creates or upgrades database
 */

public class HomeworkBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "homework.db";
    private static final int VERSION = 1;

    public HomeworkBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + HomeworkDbSchema.HomeworkTable.NAME + "(" +
            HomeworkDbSchema.HomeworkTable.Cols.ID + " integer primary key autoincrement, " +
            HomeworkDbSchema.HomeworkTable.Cols.NAME + ", " +
            HomeworkDbSchema.HomeworkTable.Cols.DATE + ", " +
            HomeworkDbSchema.HomeworkTable.Cols.FINISHED + ", " +
            HomeworkDbSchema.HomeworkTable.Cols.SUBJECT +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HomeworkDbSchema.HomeworkTable.NAME);
        onCreate(db);
    }
}

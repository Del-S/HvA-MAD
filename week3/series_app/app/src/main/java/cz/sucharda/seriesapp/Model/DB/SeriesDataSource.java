package cz.sucharda.seriesapp.Model.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

import java.util.Date;

import cz.sucharda.seriesapp.Model.Series;

/*
 *  Database reading and writing (Insert/Update/Select)
 */

public class SeriesDataSource {
    private SQLiteDatabase mDatabase;

    public SeriesDataSource(Context context) {
        mDatabase = new SeriesBaseHelper(context).getWritableDatabase();
    }

    private static ContentValues getContentValues(cz.sucharda.seriesapp.Model.Series series) {
        ContentValues values = new ContentValues();
        values.put(SeriesDbSchema.SeriesTable.Cols.NAME, series.getName());
        values.put(SeriesDbSchema.SeriesTable.Cols.DESCRITPION, series.getDescription());
        if(series.getDateStart() != null) {
            values.put(SeriesDbSchema.SeriesTable.Cols.DATE_START, DateFormat.format(
                    SeriesDbSchema.SeriesTable.DATE_FORMAT, series.getDateStart()).toString());
        }
        Date dateFinish = series.getDateFinish();
        String dateFinishString = "";
        if(dateFinish != null) {
            dateFinishString = DateFormat.format(
                    SeriesDbSchema.SeriesTable.DATE_FORMAT, series.getDateFinish()).toString();
        }
        values.put(SeriesDbSchema.SeriesTable.Cols.DATE_FINISH, dateFinishString);
        values.put(SeriesDbSchema.SeriesTable.Cols.FINISHED, series.isFinished());
        return values;
    }

    /* Query series function to enable different query types (all, by id, ...) */
    private SeriesCursorWrapper querySeries(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SeriesDbSchema.SeriesTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new SeriesCursorWrapper(cursor);
    }

    /*
     * Select/Insert/Update/Delete functions
     */

    public void addSeries(Series series) {
        ContentValues values = getContentValues(series);
        mDatabase.insert(SeriesDbSchema.SeriesTable.NAME, null, values);
    }

    public void updateSeries(Series series) {
        String idString = Long.toString(series.getId());
        ContentValues values = getContentValues(series);
        mDatabase.update(SeriesDbSchema.SeriesTable.NAME, values,
                SeriesDbSchema.SeriesTable.Cols.ID + " = ?",
                new String[]{idString});
    }

    public Cursor getAllSeries() {
        return querySeries(null, null);
    }

    public Series getSeriesById(long id) {
        SeriesCursorWrapper cursor = querySeries(SeriesDbSchema.SeriesTable.Cols.ID + " = ?",
                new String[]{Long.toString(id)});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();

            Series s = cursor.getSeries();
            s.getDateFinish();
            return s;
        } finally {
            cursor.close();
        }
    }

    public void deleteSeries(long id) {
        String idString = Long.toString(id);
        mDatabase.delete(SeriesDbSchema.SeriesTable.NAME,
                SeriesDbSchema.SeriesTable.Cols.ID + " = ?",
                new String[]{idString});
    }

    public void deleteAllSeries() {
        mDatabase.delete(SeriesDbSchema.SeriesTable.NAME, null, null);
    }
}

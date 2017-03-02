package cz.sucharda.seriesapp.Model.DB;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.text.TextUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

/*
 * Database reading wrapper through Cursor (Select)
 */

public class SeriesCursorWrapper extends CursorWrapper {
    public SeriesCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public cz.sucharda.seriesapp.Model.Series getSeries() {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(SeriesDbSchema.SeriesTable.DATE_FORMAT);
        ParsePosition pos = new ParsePosition(0);
        ParsePosition pos1 = new ParsePosition(1);

        long id = getLong(getColumnIndex(SeriesDbSchema.SeriesTable.Cols.ID));
        String name = getString(getColumnIndex(SeriesDbSchema.SeriesTable.Cols.NAME));
        String description = getString(getColumnIndex(SeriesDbSchema.SeriesTable.Cols.DESCRITPION));
        String dateStart = getString(getColumnIndex(SeriesDbSchema.SeriesTable.Cols.DATE_START));
        String dateFinish = getString(getColumnIndex(SeriesDbSchema.SeriesTable.Cols.DATE_FINISH));
        int isFinished = getInt(getColumnIndex(SeriesDbSchema.SeriesTable.Cols.FINISHED));

        cz.sucharda.seriesapp.Model.Series series = new cz.sucharda.seriesapp.Model.Series();
        series.setId(id);
        series.setName(name);
        series.setDescription(description);
        if(!TextUtils.isEmpty(dateStart)) {
            series.setDateStart( simpledateformat.parse(dateStart, pos) );
        }

        if(!TextUtils.isEmpty(dateFinish)) {
            series.setDateFinish( simpledateformat.parse(dateFinish, pos1) );
        }
        series.setFinished(isFinished != 0);
        return series;
    }
}

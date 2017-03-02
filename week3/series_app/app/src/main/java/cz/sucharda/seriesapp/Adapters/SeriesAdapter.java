package cz.sucharda.seriesapp.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.sucharda.seriesapp.Model.DB.SeriesCursorWrapper;
import cz.sucharda.seriesapp.Model.DB.SeriesDbSchema;
import cz.sucharda.seriesapp.Model.Series;
import cz.sucharda.seriesapp.R;

public class SeriesAdapter extends CursorAdapter{

    public SeriesAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_series, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        SeriesCursorWrapper cursorWrapper = (SeriesCursorWrapper) cursor;
        Series series = cursorWrapper.getSeries();

        TextView name = (TextView) view.findViewById(R.id.lis_name);
        name.setText(series.getName());

        TextView subject = (TextView) view.findViewById(R.id.lis_description);
        subject.setText(series.getDescription());

        TextView date = (TextView) view.findViewById(R.id.lis_finished);
        Date mDate = series.getDateFinish();
        if (mDate != null) {
            date.setText( DateFormat.format( SeriesDbSchema.SeriesTable.DATE_FORMAT, mDate ) );
        }

        CheckBox finishedCheckBox = (CheckBox) view.findViewById(R.id.lis_check_box);
        finishedCheckBox.setChecked(series.isFinished());
    }
}

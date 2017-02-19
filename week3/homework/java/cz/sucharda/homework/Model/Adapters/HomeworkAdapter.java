package cz.sucharda.homework.Model.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import cz.sucharda.homework.Model.DB.HomeworkCursorWrapper;
import cz.sucharda.homework.Model.Homework;
import cz.sucharda.homework.R;

public class HomeworkAdapter extends CursorAdapter {

    public HomeworkAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_homework, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        HomeworkCursorWrapper cursorWrapper = (HomeworkCursorWrapper) cursor;
        Homework homework = cursorWrapper.getHomework();

        TextView name = (TextView) view.findViewById(R.id.list_item_name);
        name.setText(homework.getTitle());

        TextView subject = (TextView) view.findViewById(R.id.list_item_subject);
        subject.setText(homework.getSubject());

        TextView date = (TextView) view.findViewById(R.id.list_item_date);
        date.setText(homework.getDate());

        CheckBox finishedCheckBox = (CheckBox) view.findViewById(R.id.list_item_check_box);
        finishedCheckBox.setChecked(homework.isFinished());
    }
}

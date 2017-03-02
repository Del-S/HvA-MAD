package cz.sucharda.studentportal.Model.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import cz.sucharda.studentportal.Model.ListItem;
import cz.sucharda.studentportal.R;

public class ListAdapter extends ArrayAdapter<ListItem> {
    private LayoutInflater inflater;

    public ListAdapter(Context context, int resource, List<ListItem> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if(row == null) {
            row = inflater.inflate(R.layout.row_layout, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.populateRow(getItem(position));
        return row;
    }
}

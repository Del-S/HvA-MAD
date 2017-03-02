package cz.sucharda.basketoffruit.Model.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cz.sucharda.basketoffruit.Model.ListItem;
import cz.sucharda.basketoffruit.Model.ViewHolder;
import cz.sucharda.basketoffruit.R;

public class ItemAdapter extends ArrayAdapter<ListItem> {
    private LayoutInflater inflater;

    public ItemAdapter(Context context, int resource, List<ListItem> objects) {
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

package cz.sucharda.basketoffruit.Model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cz.sucharda.basketoffruit.R;

public class ViewHolder {
    private ImageView image;
    private TextView title, description;

    public ViewHolder(View view) {
        image = (ImageView) view.findViewById(R.id.rl_item_image);
        title = (TextView) view.findViewById(R.id.rl_title);
        description = (TextView) view.findViewById(R.id.rl_description);
    }

    public void populateRow(ListItem item) {
        image.setImageResource(item.getImageResource());
        title.setText(item.getTitle());
        description.setText(item.getDescription());
    }
}

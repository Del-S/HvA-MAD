package cz.sucharda.basketoffruit.Model;

import java.io.Serializable;

public class ListItem implements Serializable {

    private String title;
    private String description;
    private int imageResource;

    public ListItem(String title, String description, int imageResource) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }

}

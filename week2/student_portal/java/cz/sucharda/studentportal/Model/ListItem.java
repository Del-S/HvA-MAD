package cz.sucharda.studentportal.Model;

import java.io.Serializable;

public class ListItem implements Serializable {
    private String url;
    private String title;

    public ListItem(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}

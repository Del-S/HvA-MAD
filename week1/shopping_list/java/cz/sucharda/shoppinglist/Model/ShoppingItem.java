package cz.sucharda.shoppinglist.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShoppingItem {

    private String name;
    private Date created;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public ShoppingItem(String name) {
        this.name = name;
        this.created = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return this.name + " (" + sdf.format(this.created) + ")";
    }
}

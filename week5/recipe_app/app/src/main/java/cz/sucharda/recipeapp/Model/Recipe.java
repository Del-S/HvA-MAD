package cz.sucharda.recipeapp.Model;

import java.util.UUID;

public class Recipe {

    private UUID id;
    private String name, description;

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = UUID.randomUUID();
    }

    public Recipe() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

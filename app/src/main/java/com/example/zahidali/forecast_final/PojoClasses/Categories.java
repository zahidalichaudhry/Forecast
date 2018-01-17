package com.example.zahidali.forecast_final.PojoClasses;

/**
 * Created by Itpvt on 13-Jan-18.
 */

public class Categories {
    private String category_id;
    private String parent_id;
    private String name;
    private String is_active;
    private String position;
    private String level;
    private int child;

    public Categories(String category_id, String parent_id, String name, String is_active, String position, String level,int child) {
        this.category_id = category_id;
        this.parent_id = parent_id;
        this.name = name;
        this.is_active = is_active;
        this.position = position;
        this.level = level;
        this.child=child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getChild() {
        return child;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public String getName() {
        return name;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getPosition() {
        return position;
    }

    public String getLevel() {
        return level;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

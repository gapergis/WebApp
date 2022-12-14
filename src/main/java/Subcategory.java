/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 * @author Greg
 */
public class Subcategory {

    private int id;
    private boolean selected;
    ArrayList< Object > all_services = new ArrayList < Object > ();
    private int order;
    private String title;
    private String slug;
    private String url = null;
    private String description;
    private int category;


    // Getter Methods

    public int getId() {
        return id;
    }

    public boolean getSelected() {
        return selected;
    }

    public int getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public int getCategory() {
        return category;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 * @author Greg
 */
public class Organization {

    private int id;
    private boolean selected;
    private String logo;
    ArrayList< Object > services = new ArrayList < Object > ();
    private int services_count;
    private String title;
    private String slug;
    private String url;
    private String description;
    private String special_slug;
    private int ministry;
    ArrayList < Object > subcategories = new ArrayList < Object > ();
    ArrayList < Object > second_subcategories = new ArrayList < Object > ();


    // Getter Methods

    public int getId() {
        return id;
    }

    public boolean getSelected() {
        return selected;
    }

    public String getLogo() {
        return logo;
    }

    public int getServices_count() {
        return services_count;
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

    public String getSpecial_slug() {
        return special_slug;
    }

    public int getMinistry() {
        return ministry;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setServices_count(int services_count) {
        this.services_count = services_count;
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

    public void setSpecial_slug(String special_slug) {
        this.special_slug = special_slug;
    }

    public void setMinistry(int ministry) {
        this.ministry = ministry;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", selected=" + selected +
                ", logo='" + logo + '\'' +
                ", services=" + services +
                ", services_count=" + services_count +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", special_slug='" + special_slug + '\'' +
                ", ministry=" + ministry +
                ", subcategories=" + subcategories +
                ", second_subcategories=" + second_subcategories +
                '}';
    }
}

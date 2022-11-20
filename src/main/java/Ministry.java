import java.util.ArrayList;

public class Ministry {
    private float id;
    private boolean selected;
    ArrayList< Object > organizations = new ArrayList < Object > ();
    private String title;
    private String slug;
    private String url = null;
    private String description;
    private boolean is_authority;


    // Getter Methods

    public float getId() {
        return id;
    }

    public boolean getSelected() {
        return selected;
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

    public boolean getIs_authority() {
        return is_authority;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    public void setIs_authority(boolean is_authority) {
        this.is_authority = is_authority;
    }
}

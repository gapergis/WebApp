/**
 * @author Greg
 */
public class Useful {

    private int id;
    private boolean selected;
    private String slug;
    private String url;
    private String description;
    private String title;
    private int service;

    private int code;

    private String status;

//    public Useful(float id, boolean selected, String slug, String url, String description, String title, float service, int code, String status) {
//        this.id = id;
//        this.selected = selected;
//        this.slug = slug;
//        this.url = url;
//        this.description = description;
//        this.title = title;
//        this.service = service;
//        this.code = code;
//        this.status = status;
//    }

    // Getter Methods

    public int getId() {
        return id;
    }

    public boolean getSelected() {
        return selected;
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

    public String getTitle() {
        return title;
    }

    public int getService() {
        return service;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setService(int service) {
        this.service = service;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }


    @Override
    public String toString() {
        return "Useful{" +
                "id=" + id +
                ", selected=" + selected +
                ", slug='" + slug + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", service=" + service +
                '}';
    }
}

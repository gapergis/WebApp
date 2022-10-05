/**
 * @author Greg
 */
public class Organization {

    private int id;
    private String name;
    private String slug;

    @Override
    public String toString() {
        return "app.Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }

    public Organization(int id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

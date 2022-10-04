/**
 * @author Greg
 */
public class Organization {

    public int id;
    public String name;
    public String slug;

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

    public String getNamebyid(int id) {
        String name2 = null;
        if (id == this.id) {
            name2 = this.name;
        }
        return name2;
    }


}

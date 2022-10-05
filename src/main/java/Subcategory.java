/**
 * @author Greg
 */
public class Subcategory {

    private int id;
    private String name;
    private int category;
    private String slug;

    public Subcategory(int id, String name, int category, String slug) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.slug = slug;

    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "app.Subcategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", slug='" + slug + '\'' +
                '}';
    }
}

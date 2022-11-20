import java.util.ArrayList;

/**
 * @author Greg
 */
public class Service {

    private int id;
    private boolean selected;
    ArrayList< Object > service_actions = new ArrayList < Object > ();
    ArrayList < Object > useful_links = new ArrayList < Object > ();
    ArrayList <Object> service_region_useful_links = new ArrayList < Object > ();
    ArrayList < Object > regions = new ArrayList < Object > ();
    private String modified_timestamp;
    private String title;
    private String slug;
    private String url;
    private String description;
    private boolean is_g2c;
    private boolean is_g2b;
    private boolean is_g2g;
    private boolean active;
    private String support_url;
    private int sub_category;
    private int second_sub_category;
    private int group;
    private int organization;


    // Getter Methods

    public int getId() {
        return id;
    }

    public boolean getSelected() {
        return selected;
    }

    public String getModified_timestamp() {
        return modified_timestamp;
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

    public boolean getIs_g2c() {
        return is_g2c;
    }

    public boolean getIs_g2b() {
        return is_g2b;
    }

    public boolean getIs_g2g() {
        return is_g2g;
    }

    public boolean getActive() {
        return active;
    }

    public String getSupport_url() {
        return support_url;
    }

    public int getSub_category() {
        return sub_category;
    }

    public int getSecond_sub_category() {
        return second_sub_category;
    }

    public int getGroup() {
        return group;
    }

    public int getOrganization() {
        return organization;
    }



    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setModified_timestamp(String modified_timestamp) {
        this.modified_timestamp = modified_timestamp;
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

    public void setIs_g2c(boolean is_g2c) {
        this.is_g2c = is_g2c;
    }

    public void setIs_g2b(boolean is_g2b) {
        this.is_g2b = is_g2b;
    }

    public void setIs_g2g(boolean is_g2g) {
        this.is_g2g = is_g2g;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSupport_url(String support_url) {
        this.support_url = support_url;
    }

    public void setSub_category(int sub_category) {
        this.sub_category = sub_category;
    }

    public void setSecond_sub_category(int second_sub_category) {
        this.second_sub_category = second_sub_category;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setOrganization(int organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", selected=" + selected +
                ", service_actions=" + service_actions +
                ", useful_links=" + useful_links +
                ", service_region_useful_links=" + service_region_useful_links +
                ", regions=" + regions +
                ", modified_timestamp='" + modified_timestamp + '\'' +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", is_g2c=" + is_g2c +
                ", is_g2b=" + is_g2b +
                ", is_g2g=" + is_g2g +
                ", active=" + active +
                ", support_url='" + support_url + '\'' +
                ", sub_category=" + sub_category +
                ", second_sub_category='" + second_sub_category + '\'' +
                ", group=" + group +
                ", organization=" + organization +
                '}';
    }
}

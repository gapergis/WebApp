/**
 * @author Greg
 */
public class Useful {

    public int id;
    public String name;
    public String url;
    public int service;
    public int code;
    public String status;
    public String contact;
    public String slug;
    public String foreas;
    public final Boolean perif;

    public Useful(int id, String name, String url, int code, String status, int service,String foreas, String contact, String slug, Boolean perif) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.code = code;
        this.status = status;
        this.service = service;
        this.foreas = foreas;
        this.contact = contact;
        this.slug = slug;
        this.perif= perif;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getForeas() {
        return foreas;
    }

    public void setForeas(String foreas) {
        this.foreas = foreas;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


    @Override
    public String toString() {
        return "app.Useful{" + "id=" + id + ", name=" + name + ", url=" + url + ", foreas=" + foreas + ", perif?=" + perif + '}';
    }
}

import java.util.List;

/**
 * @author Greg
 */
public class Service {

    public int id;
    public String name;
    public final String slug;
    public String url;
    public String contact;
    public final String description;
    public String orgTitle;
    public String urlSupport;
    public int code;
    public String status;
    public final boolean active;
    public final String category;
    public final String subCat;
    public final String modTime;
    public String urlEisodou;


    public Service(int id, String name, String url, String urlEisodou,String description, String contact, String slug, String orgTitle, String urlSupport, int code, String status, boolean active, String category, String subCat, String modTime) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.urlEisodou = urlEisodou;
        this.description = description;
        this.contact = contact;
        this.slug = slug;
        this.orgTitle = orgTitle;
        this.urlSupport = urlSupport;
        this.code = code;
        this.status = status;
        this.active = active;
        this.category = category;
        this.subCat = subCat;
        this.modTime = modTime;
    }

    public String getUrlEisodou() {
        return urlEisodou;
    }

    public void setUrlEisodou(String urlEisodou) {
        this.urlEisodou = urlEisodou;
    }


    public String getOrgTitle() {
        return orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
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

    public String getUrlSupport() {
        return urlSupport;
    }

    public void setUrlSupport(String urlSupport) {
        this.urlSupport = urlSupport;
    }

    @Override
    public String toString() {
        return "app.Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + urlEisodou + '\'' +
                ", orgTitle='" + orgTitle + '\'' +
                ", urlSupport='" + urlSupport + '\'' +
                '}';
    }

}

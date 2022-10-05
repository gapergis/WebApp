/**
 * @author Greg
 */
public class Service {

    private int id;
    private String name;
    private final String slug;
    private String url;
    private String contact;
    private final String description;
    private String orgTitle;
    private String urlSupport;
    private int code;
    private String status;
    private final boolean active;
    private final String category;
    private final String subCat;
    private final String modTime;
    private String urlEisodou;


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

    public String getSlug() {
        return slug;
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

    public String getDescription() {
        return description;
    }

    public String getOrgTitle() {
        return orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getUrlSupport() {
        return urlSupport;
    }

    public void setUrlSupport(String urlSupport) {
        this.urlSupport = urlSupport;
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

    public boolean isActive() {
        return active;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCat() {
        return subCat;
    }

    public String getModTime() {
        return modTime;
    }

    public String getUrlEisodou() {
        return urlEisodou;
    }

    public void setUrlEisodou(String urlEisodou) {
        this.urlEisodou = urlEisodou;
    }

    public Service(int id, String name, String url, String urlEisodou, String description, String contact, String slug, String orgTitle, String urlSupport, int code, String status, boolean active, String category, String subCat, String modTime) {
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



}

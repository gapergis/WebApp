public class ServiceAction {
    private int id;
    private String title;
    private String url;
    private String orgTitle;
    private int code;

    public ServiceAction(int id, String title, String url, String orgTitle, int code) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.orgTitle = orgTitle;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrgTitle() {
        return orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

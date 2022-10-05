import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Greg
 */
public class Controller {

    public static boolean isUrlValid(String url) {
        try {
            URL obj = new URL(url);
            obj.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }


    public static String getAPIResponse(String getData) { //ανάλογα με το epilogi θα κατέβουν τα αντοίστοιχα δεδομένα
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client;
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        client = builder.build();
        Request request = new Request.Builder().url(getData).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Service> getAPIServiceData(ArrayList<Organization> org, ArrayList<Category> cat, ArrayList<Subcategory> sub, ArrayList<Useful> usefuls) {//μέθοδος για κατέβασμα των δεδομένων για κάθε χώρα (Coviddata)
        ArrayList<Service> services = new ArrayList<>();
        ArrayList<Useful> usef = new ArrayList<>();
        String data = null;
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/services/");
        }
        try {
            JsonArray outer = (JsonArray) JsonParser.parseString(data);
            for (Object object : outer) {
                JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
                if (objServ.get("active").getAsBoolean()) {
                    Service service;
                    if (!serviceExists(services, objServ.get("id").getAsInt())) {
                        String title = objServ.get("title").getAsString();
                        String catslug = getCatSlug(sub, cat, objServ.get("sub_category").getAsInt());
                        String subCatslug = getSubSlug(sub, objServ.get("sub_category").getAsInt());
                        String urlSupport = "";
                        if (!objServ.get("support_url").isJsonNull()) {
                            urlSupport = objServ.get("support_url").getAsString();
                        }
                        int foreas = 0;
                        if (!objServ.get("organization").isJsonNull()) {
                            foreas = objServ.get("organization").getAsInt();
                        }
                        String url = "Https://www.gov.gr/ipiresies/" + catslug + "/" + subCatslug + "/" + objServ.get("slug").getAsString();
                        String orga;
                        if (objServ.getAsJsonArray("regions").size() != 0) {
                            int i = 1;
                            for (Object reg : objServ.getAsJsonArray("regions")) {
                                JsonObject orgServ = (JsonObject) JsonParser.parseString(reg.toString());
                                orga = orgServ.get("region_title").getAsString();
                                service = new Service(objServ.get("id").getAsInt(), title, url, orgServ.get("url").getAsString(), objServ.get("description").getAsString(), "No Contact Info", objServ.get("slug").getAsString(), orga, urlSupport, 0, "", objServ.get("active").getAsBoolean(), getCatName(sub, cat, objServ.get("sub_category").getAsInt()), getSubName(sub, objServ.get("sub_category").getAsInt()), objServ.get("modified_timestamp").getAsString());
                                services.add(service);
                                for (Object regsup : objServ.getAsJsonArray("service_region_useful_links")) {
                                    JsonObject regsupport = (JsonObject) JsonParser.parseString(regsup.toString());
                                    if (orgServ.get("region_slug").getAsString().equals(regsupport.get("region_slug").getAsString())) {
                                        Useful useful = new Useful(i++, regsupport.get("title").getAsString(), regsupport.get("url").getAsString(), 0, "", objServ.get("id").getAsInt(), orgServ.get("region_title").getAsString(), "No Contact Info", "", true);
                                        usef.add(useful);
                                    }
                                }
                            }
                        } else if (objServ.getAsJsonArray("service_actions").size() != 0) {
                            JsonArray serviceActions = objServ.getAsJsonArray("service_actions");
                            for (Object inObject : serviceActions) {
                                JsonObject inObj = (JsonObject) JsonParser.parseString(inObject.toString());
                                service = new Service(objServ.get("id").getAsInt(), title, url, inObj.get("url").getAsString(), objServ.get("description").getAsString(), "No Contact Info", objServ.get("slug").getAsString(), getOrgName(org, foreas), urlSupport, 0, "", objServ.get("active").getAsBoolean(), getCatName(sub, cat, objServ.get("sub_category").getAsInt()), getSubName(sub, objServ.get("sub_category").getAsInt()), objServ.get("modified_timestamp").getAsString());
                                services.add(service);
                            }
                            if (!urlSupport.equals("")) {
                                Useful useful = new Useful(0, "Επικοινωνία", urlSupport, 0, "", objServ.get("id").getAsInt(), getOrgName(org, foreas), "No Contact Info", "epikoinonia", false);
                                usef.add(useful);
                            }
                        } else {
                            service = new Service(objServ.get("id").getAsInt(), title, url, "", objServ.get("description").getAsString(), "No Contact Info", objServ.get("slug").getAsString(), getOrgName(org, foreas), urlSupport, 0, "", objServ.get("active").getAsBoolean(), getCatName(sub, cat, objServ.get("sub_category").getAsInt()), getSubName(sub, objServ.get("sub_category").getAsInt()), objServ.get("modified_timestamp").getAsString());
                            services.add(service);
                        }
                    }
                }
            }
            usefuls.addAll(usef);
            System.out.println(services.size());
        } catch (Exception e) {
            System.out.println(e);
        }
        return services;
    }

    public static ArrayList<Useful> getAPIUsefulData(ArrayList<Service> ser) {//μέθοδος για κατέβασμα των δεδομένων για κάθε χώρα (Coviddata)
        ArrayList<Useful> usefuls = new ArrayList<>();
        String data = null;
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/service-useful-links/");
        }
        try {
            JsonArray outer = (JsonArray) JsonParser.parseString(data);
            for (Object object : outer) {
                Useful useful;
                JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
                String title = objServ.get("title").getAsString();
                int service = 0;
                if (!objServ.get("service").isJsonNull()) {
                    service = objServ.get("service").getAsInt();
                }
                if (serviceActive(ser, service)) {
                    String url = "";
                    if (!objServ.get("url").isJsonNull()) {
                        url = objServ.get("url").getAsString();
                    }
                    useful = new Useful(objServ.get("id").getAsInt(), title, url, 0, "", service, getServFor(ser, service), "No Contact Info", objServ.get("slug").getAsString(), false);
                    usefuls.add(useful);
                }
            }
            System.out.println(usefuls.size());
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        }
        return usefuls;
    }

    public static ArrayList<Organization> getOrgData() {
        String data = null;
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/organizations/");
        }
        JsonArray outer = (JsonArray) JsonParser.parseString(data);
        System.out.println(outer.size());
        ArrayList<Organization> orgData = new ArrayList<>();
        for (Object object : outer) {
            JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
            String title = getAPIMinistryName(objServ.get("title").getAsString());
            Organization org = new Organization(objServ.get("id").getAsInt(), title, objServ.get("slug").getAsString());
            orgData.add(org);
        }
        return orgData;
    }

    public static String getAPIMinistryName(String title) {//μέθοδος για κατέβασμα των δεδομένων για κάθε χώρα (Coviddata)
        String data = null;
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/ministries/");
        }
        try {
            JsonArray outer = (JsonArray) JsonParser.parseString(data);
            for (Object object : outer) {
                JsonObject objMin = (JsonObject) JsonParser.parseString(object.toString());
                if (objMin.get("title").getAsString().replace("Υπουργείο ", "").equals(title)) {
                    return objMin.get("title").getAsString();
                }
            }
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        }
        return title;
    }

    public static ArrayList<Category> getCatData() {
        String data = null;
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/categories/");
        }
        JsonArray outer = (JsonArray) JsonParser.parseString(data);
        System.out.println(outer.size());
        ArrayList<Category> catData = new ArrayList<>();
        for (Object object : outer) {
            JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
            String title = objServ.get("title").getAsString();
            Category cat = new Category(objServ.get("id").getAsInt(), title, objServ.get("slug").getAsString());
            catData.add(cat);
        }
        return catData;
    }

    public static ArrayList<Subcategory> getGegData() {
        String data = null;
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/subcategories/");
        }
        JsonArray outer = (JsonArray) JsonParser.parseString(data);
        System.out.println(outer.size());
        ArrayList<Subcategory> gegData = new ArrayList<>();
        for (Object object : outer) {
            JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
            String title = objServ.get("title").getAsString();
            Subcategory sub = new Subcategory(objServ.get("id").getAsInt(), title, objServ.get("category").getAsInt(), objServ.get("slug").getAsString());
            gegData.add(sub);
        }
        return gegData;
    }

    public static boolean serviceExists(ArrayList<Service> services, int id) {
        for (Service service : services) {
            if (service.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static boolean serviceActive(ArrayList<Service> services, int id) {
        for (Service service : services) {
            if (service.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static String getServFor(ArrayList<Service> services, int id) {
        for (Service service : services) {
            if (service.getId() == id) {
                return service.getOrgTitle();
            }
        }
        return "";
    }

    public String getServName(ArrayList<Service> services, int id) {
        for (Service service : services) {
            if (service.getId() == id) {
                return service.getName();
            }
        }
        return "";
    }

    public static String getOrgName(ArrayList<Organization> org, int id) {
        for (Organization org2 : org) {
            if (id == org2.getId()) {
                return org2.getName();
            }
        }
        return null;
    }

    public static String getCatName(ArrayList<Subcategory> sub, ArrayList<Category> cat, int id) {
        int catid = 0;
        for (Subcategory sub2 : sub) {
            if (id == sub2.getId()) {
                catid = sub2.getCategory();
                break;
            }
        }
        for (Category cat2 : cat) {
            if (catid == cat2.getId()) {
                return cat2.getName();
            }
        }
        return null;
    }

    public static String getSubName(ArrayList<Subcategory> sub, int id) {
        for (Subcategory sub2 : sub) {
            if (id == sub2.getId()) {
                return sub2.getName();
            }
        }
        return null;
    }

    public String getOrgSlug(ArrayList<Organization> org, int id) {
        for (Organization org2 : org) {
            if (id == org2.getId()) {
                return org2.getSlug();
            }
        }
        return null;
    }

    public static String getCatSlug(ArrayList<Subcategory> sub, ArrayList<Category> cat, int id) {
        int catid = 0;
        for (Subcategory sub2 : sub) {
            if (id == sub2.getId()) {
                catid = sub2.getCategory();
                break;
            }
        }
        for (Category cat2 : cat) {
            if (catid == cat2.getId()) {
                return cat2.getSlug();
            }
        }
        return null;
    }

    public static String getSubSlug(ArrayList<Subcategory> sub, int id) {
        for (Subcategory sub2 : sub) {
            if (id == sub2.getId()) {
                return sub2.getSlug();
            }
        }
        return null;
    }

    public File ServiceWriter(ArrayList<Service> services) throws IOException {

        ArrayList<Integer> printed = new ArrayList<>();
        File temp = new File(Controller.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = temp.getParent();
        File fileName = new File(path + "/serviceExport.csv");
        System.out.println(path);
        FileOutputStream file = new FileOutputStream(fileName);
        try (BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(file, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Id")
                    .append('^')
                    .append("Κατηγόρια")
                    .append('^')
                    .append("Γεγ. Ζωής")
                    .append('^')
                    .append("Υπηρεσία")
                    .append('^')
                    .append("Φορέας")
                    .append('^')
                    .append("Ημ. Τροποποίησης")
                    .append('^')
                    .append("Link Εισόδου")
                    .append('^')
                    .append("Url επικοινωνίας")
                    .append('\n');
            for (Service service : services) {
                if (!printed.contains(service.getId())) {
                    sb.append(service.getId())
                            .append('^')
                            .append(service.getCategory())
                            .append('^')
                            .append(service.getSubCat())
                            .append('^').append("=HYPERLINK(\"").append(service.getUrl()).append("\",\"").append(service.getName()).append("\")")
                            .append('^')
                            .append(service.getOrgTitle())
                            .append('^')
                            .append(getModDate(service.getModTime()))
                            .append('^')
                            .append(service.getUrlEisodou())
                            .append('\n');
                    printed.add(service.getId());
                }
            }
            wr.write(sb.toString());
            wr.flush();
        } catch (IOException | ParseException ex) {
            System.out.println(ex);
        }
        return fileName;
    }

    public void UsefulWriter(ArrayList<Service> services, List<Useful> usefuls) throws IOException {
        File temp = new File(Controller.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = temp.getParent();
        File fileName = new File(path + "/UsefulExport.csv");
        System.out.println(path);
        FileOutputStream file = new FileOutputStream(fileName);
        try (BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(file, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Id")
                    .append('^')
                    .append("Title")
                    .append('^')
                    .append("Link")
                    .append('^')
                    .append("app.Service")
                    .append('^')
                    .append("Foreas")
                    .append('^')
                    .append("Code")
                    .append('^')
                    .append("Status")
                    .append('\n');
            for (Useful useful : usefuls) {
                if (useful.getStatus().equals("red")) {
                    sb.append('\n')
                            .append(useful.getId())
                            .append('^')
                            .append(useful.getName())
                            .append('^')
                            .append(useful.getUrl())
                            .append('^')
                            .append(getServName(services, useful.getService()))
                            .append('^')
                            .append(getServFor(services, useful.getService()))
                            .append('^')
                            .append(useful.getCode())
                            .append('^')
                            .append(useful.getStatus());
                }
            }
            wr.write(sb.toString());
            wr.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public String onlyDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public String getModDate(String date) throws ParseException {
        Date modDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(modDate);
    }
}

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

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
        OkHttpClient client = new OkHttpClient();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        client = builder.build();
        Request request = new Request.Builder().url(getData).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Service> getAPIServiceData() {//μέθοδος για κατέβασμα των δεδομένων για κάθε χώρα (Coviddata)
        System.out.print("Getting Services... ");
        ArrayList<Service> services = new ArrayList<>();
        String data = null;
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/services/");
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            JsonNode actualObj = mapper.readTree(data);
            TypeReference<ArrayList<Service>> typeRef = new TypeReference<>() {
            };
            services = mapper.readValue(actualObj.traverse(), typeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(services.size());
        return services;
    }

    public static ArrayList<Useful> getAPIUsefulData() {//μέθοδος για κατέβασμα των δεδομένων για κάθε χώρα (Coviddata)\
        System.out.print("Getting Useful Links... ");
        ArrayList<Useful> usefuls = new ArrayList<>();
        String data = null;
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/service-useful-links/");
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            JsonNode actualObj = mapper.readTree(data);
            TypeReference<ArrayList<Useful>> typeRef = new TypeReference<>() {
            };
            usefuls = mapper.readValue(actualObj.traverse(), typeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(usefuls.size());
        return usefuls;
    }

    public static ArrayList<Organization> getOrgData() {
        System.out.print("Getting Organizations... ");
        String data = null;
        ArrayList<Organization> orgData = new ArrayList<>();
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/organizations/");
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            JsonNode actualObj = mapper.readTree(data);
            TypeReference<ArrayList<Organization>> typeRef = new TypeReference<>() {
            };
            orgData = mapper.readValue(actualObj.traverse(), typeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(orgData.size());
        return orgData;
    }

    public static ArrayList<Ministry> getMinData() {
        System.out.print("Getting Ministries... ");
        String data = null;
        ArrayList<Ministry> minData = new ArrayList<>();
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/ministries/");
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            JsonNode actualObj = mapper.readTree(data);
            TypeReference<ArrayList<Ministry>> typeRef = new TypeReference<>() {
            };
            minData = mapper.readValue(actualObj.traverse(), typeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(minData.size());
        return minData;
    }

    public String getMinistryName(Data data, String title) {
        for (Ministry ministry : data.mins) {
            if (ministry.getTitle().replace("Υπουργείο ", "").equals(title)) {
                return ministry.getTitle();
            }
        }
        return title;
    }

    public static ArrayList<Category> getCatData()  {
        System.out.print("Getting Categories... ");
        String data = null;
        ArrayList<Category> catData = new ArrayList<>();
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/categories/");
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            JsonNode actualObj = mapper.readTree(data);
            TypeReference<ArrayList<Category>> typeRef = new TypeReference<>() {};
            catData = mapper.readValue(actualObj.traverse(), typeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(catData.size());
        return catData;
    }

    public static ArrayList<Subcategory> getGegData() {
        System.out.print("Getting Life Events... ");
        String data = null;
        ArrayList<Subcategory> gegData = new ArrayList<>();
        while (data == null) {
            data = getAPIResponse("https://www.gov.gr/api/v1/subcategories/");
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            JsonNode actualObj = mapper.readTree(data);
            TypeReference<ArrayList<Subcategory>> typeRef = new TypeReference<>() {};
            gegData = mapper.readValue(actualObj.traverse(), typeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(gegData.size());
        return gegData;
    }

    public String getOrgName(Data data, int id) {
        if (id==0){
            return "Τοπική Αυτοδιοίκηση - Περιφέρειες";
        }
        for (Organization org2 : data.orgs) {
            if (id == org2.getId()) {
                String orgName = getMinistryName(data, org2.getTitle());
                return orgName;
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

    public String getCatName(ArrayList<Category> cat, ArrayList<Subcategory> sub, int subId) {
        for (Subcategory sub2 : sub) {
            if (subId == sub2.getId()) {
                int catId = sub2.getCategory();
                for (Category cat2 : cat) {
                    if (catId == cat2.getId()) {
                        return cat2.getTitle();
                    }
                }
            }
        }
        return null;
    }
    public String getCatSlug(ArrayList<Category> cat, ArrayList<Subcategory> sub, int subId) {
        for (Subcategory sub2 : sub) {
            if (subId == sub2.getId()) {
                int catId = sub2.getCategory();
                for (Category cat2 : cat) {
                    if (catId == cat2.getId()) {
                        return cat2.getSlug();
                    }
                }
            }
        }
        return null;
    }

    public String getSubSlug(ArrayList<Subcategory> sub, int subId) {
        for (Subcategory sub2 : sub) {
            if (subId == sub2.getId()) {
                return sub2.getSlug();
            }
        }
        return null;
    }

    public String getSubName(ArrayList<Subcategory> sub, int id) {
        for (Subcategory sub2 : sub) {
            if (id == sub2.getId()) {
                return sub2.getTitle();
            }
        }
        return null;
    }

    public String getServName(Data data, int id) {
        for (Service service : data.services) {
            if (service.getId() == id) {
                return service.getTitle();
            }
        }
        return "";
    }

    public String getURL(ArrayList<Service> services, int id){
        for (Service service: services){
            if (service.getId()==id) {
                for (Object ser : service.service_actions) {
                    String jsonInString = new Gson().toJson(ser);
                    JSONObject obj = new JSONObject(jsonInString);
                    if (obj.get("type").toString().equals("BUTTON")){
                        return obj.get("url").toString();
                    }
                }
            }
        }
        return null;
    }

    public String createBread(Data data, int id){
        for (Service service: data.services){
            if (service.getId()==id) {
                String cat = getCatSlug(data.cats, data.subs, service.getSub_category());
                String sub = getSubSlug(data.subs, service.getSub_category());
                String serslug = service.getSlug();
                return "https://www.gov.gr/ipiresies/"+ cat +"/"+ sub +"/"+ serslug;
            }
        }
        return null;
    }

    public File ServiceWriter(Data data) throws IOException {
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
                    .append("Κατηγορία")
                    .append('^')
                    .append("Γεγ. Ζωής")
                    .append('^')
                    .append("Υπηρεσία")
                    .append('^')
                    .append("Φορέας")
                    .append('^')
                    .append("Ημ. Τροποποίησης")
                    .append('\n');
            for (Service service : data.services) {
                if(!printed.contains(service.getId()) && service.getActive()) {
                    sb.append(service.getId())
                            .append('^')
                            .append(getCatName(data.cats, data.subs, service.getSub_category()))
                            .append('^')
                            .append(getSubName(data.subs,service.getSub_category()))
                            .append('^')
                            .append("=HYPERLINK(\"" + createBread(data,service.getId()) + "\",\"" + service.getTitle() + "\")")
                            .append('^')
                            .append(getOrgName(data,service.getOrganization()))
                            .append('^')
                            .append(getModDate(service.getModified_timestamp()))
                            .append('\n');
                    printed.add(service.getId());
                }
            }
            wr.write(sb.toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return fileName;
    }

//    public void UsefulWriter(ArrayList<Service> services, List<Useful> usefuls) throws IOException {
//        ArrayList<Integer> printed=new ArrayList<>();
//        File temp = new File(GovApiData.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//        String path = temp.getParent();
//        File fileName = new File(path + "/exports/export_" + onlyDate() + ".csv");
//        System.out.println(path);
//        FileOutputStream file = new FileOutputStream(fileName);
//        try (BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(file, StandardCharsets.UTF_8))) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("Id")
//                    .append('^')
//                    .append("Title")
//                    .append('^')
//                    .append("Link")
//                    .append('^')
//                    .append("Service")
//                    .append('^')
//                    .append("Foreas")
//                    .append('^')
//                    .append("Code")
//                    .append('^')
//                    .append("Status")
//                    .append('\n');
//            for (Useful useful : usefuls) {
//                if (useful.getStatus().equals("red")) {
//                    sb.append('\n')
//                    .append(useful.getId())
//                    .append('^')
//                    .append(useful.getName())
//                    .append('^')
//                    .append(useful.getUrl())
//                    .append('^')
//                    .append(getServName(services, useful.getService()))
//                    .append('^')
//                    .append(getServFor(services, useful.getService()))
//                    .append('^')
//                    .append(useful.getCode())
//                    .append('^')
//                    .append(useful.getStatus());
//                }
//            }
//            wr.write(sb.toString());
//        } catch (IOException ex) {
//            System.out.println(ex);
//        }
//    }

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
